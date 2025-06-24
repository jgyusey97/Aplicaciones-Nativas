package com.bg.bancoguayaquilcertificates.repository

import com.bg.bancoguayaquilcertificates.messages.AppMessages
import com.bg.bancoguayaquilcertificates.models.*
import com.bg.bancoguayaquilmultichannel.models.ServiceError
import com.bg.bancoguayaquilmultichannel.services.MultiChannelService
import com.bg.bancoguayaquilsession.BancoGuayaquilSession
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.lang.reflect.Type

class CertificatesRepository {
    private val service = MultiChannelService.instance
    private val session = BancoGuayaquilSession.instance.getSession()
    private val gson = Gson()

    // Custom serializer that uses Gson internally
    private inline fun <reified T> createGsonSerializer(): KSerializer<T> {
        return object : KSerializer<T> {
            override val descriptor: SerialDescriptor =
                buildClassSerialDescriptor(T::class.simpleName ?: "Unknown")

            override fun serialize(encoder: Encoder, value: T) {
                val jsonString = gson.toJson(value)
                encoder.encodeString(jsonString)
            }

            override fun deserialize(decoder: Decoder): T {
                val jsonString = decoder.decodeString()
                return gson.fromJson(jsonString, T::class.java)
            }
        }
    }

    inline fun <reified T> gsonType(): Type {
        return object : TypeToken<T>() {}.type
    }

    suspend fun getCertificateTypes(): List<CertificateType> = withContext(Dispatchers.IO) {
        val details = mapOf(
            "FuenteInformacion" to "BANCAELECTRONICABG",
            "FuenteRegistrador" to "WEBAPI",
            "Id_Contrato" to 0,
            "TramaREST" to mapOf(
                "Action" to "OBTENER_PRODUCTOS_NEOCOM",
                "Data" to emptyMap<String, Any>(),
                "Parameters" to mapOf(
                    "Canal" to "BV",
                    "Evento" to "NEOCOMCREF",
                    "codAplicacion" to "BV",
                    "tipoIdentificacion" to "C"
                )
            )
        )

        val response = service.sendRequest<CertificatesResponse>(
            transaction = "OBTENER_PRODUCTOS_NEOCOM",
            details = details,
            responseType = gsonType<CertificatesResponse>()
        )

        if (
            response.data == null ||
            response.data?.returnCode != "0000" ||
            response.data?.data?.certificates.isNullOrEmpty()
        ) {
            val code = response.data?.returnCode ?: "ERROR"
            val message =
                response.data?.message ?: AppMessages.General.messages["ErrorGeneral"] ?: ""

            throw ServiceError.ResponseError(code, message)
        }

        val certificates = response.data!!.data!!.certificates

        certificates
    }

    suspend fun getDetailCertificate(extCode: String): CertificateData =
        withContext(Dispatchers.IO) {
            if (extCode.isEmpty()) {
                val code = "NO_ID_PRODUCTO"
                val message = AppMessages.RefBancariaCatalogoProductos.messages[code] ?: ""
                throw ServiceError.ResponseError(code, message)
            }

            val user = session?.user
            val details = mapOf(
                "FuenteInformacion" to "BANCAELECTRONICABG",
                "FuenteRegistrador" to "WEBAPI",
                "Id_Contrato" to 0,
                "TramaREST" to mapOf(
                    "Action" to "CONSULTA_PROD_CARTA_REF",
                    "Data" to emptyMap<String, Any>(),
                    "Parameters" to mapOf(
                        "Canal" to "BV",
                        "codAplicacion" to "BV",
                        "identificacion" to (user?.identification ?: ""),
                        "tipoIdentificacion" to (user?.identificationType ?: ""),
                        "tipoProducto" to extCode
                    )
                )
            )

            val response = service.sendRequest<DetailCertificateResponse>(
                transaction = "CONSULTA_PROD_CARTA_REF",
                details = details,
                responseType = gsonType<DetailCertificateResponse>()
            )

            if (response.data == null || response.data?.returnCode != "0000") {
                val code = response.data?.returnCode ?: "ERROR"
                val message = AppMessages.RefBancariaCatalogoProductos.messages[code]
                    ?: AppMessages.RefBancariaCatalogoProductos.messages["ERROR"]
                    ?: ""

                throw ServiceError.ResponseError(code, message)
            }

            if (
                response.data?.data == null ||
                response.data!!.data.productDetails.isEmpty() ||
                response.data!!.data.productDetails[0].productCode.isEmpty()
            ) {
                val code = "SIN_PRODUCTOS"
                val message = AppMessages.RefBancariaCatalogoProductos.messages[code] ?: ""
                throw ServiceError.ResponseError(code, message)
            }

            val detailCertificate = response.data!!.data

            val transformedProductDetails = detailCertificate.productDetails.map { product ->
                val transformedProductType =
                    product.productType.substringAfter(":", product.productType)
                ProductDetail(
                    controlId = product.controlId,
                    productType = transformedProductType,
                    productCode = product.productCode,
                    state = product.state,
                    value = product.value,
                    sign = product.sign,
                    availableCash = product.issueDate,
                    availableCashSign = product.expirationDate,
                    issueDate = product.availableCash,
                    expirationDate = product.availableCashSign,
                    levelLetters = product.levelQuota,
                    levelNumber = product.levelDue,
                    levelUtil = product.levelUtil,
                    figuresUtil = product.levelLetters,
                    levelQuota = product.levelNumber,
                    figuresQuota = product.figuresQuota,
                    levelDue = product.figuresDue,
                    figuresDue = product.figuresUtil
                )
            }

            CertificateData(
                productDetails = transformedProductDetails,
                catalogDetailsByType = detailCertificate.catalogDetailsByType
            )
        }

    suspend fun requestReferenceLetter(request: GetCertificateRequest): Boolean =
        withContext(Dispatchers.IO) {
            val product = request.product
            val certRequest = request.request

            if (product == null || certRequest == null) {
                val code = "NO_INFO"
                val message = AppMessages.RefBancariaSolicitar.messages[code] ?: ""
                throw ServiceError.ResponseError(code, message)
            }

            val user = session?.user
            val account = session?.accounts?.firstOrNull()
            val details = mapOf(
                "FuenteInformacion" to "BANCAELECTRONICABG",
                "FuenteRegistrador" to "WEBAPI",
                "Id_Contrato" to 0,
                "TramaREST" to mapOf(
                    "Action" to "SOLICITUD_CARTA_REFERENCIA",
                    "Data" to emptyMap<String, Any>(),
                    "Parameters" to mapOf(
                        "codAplicacion" to "BV",
                        "CorreoPara" to (user?.email ?: ""),
                        "CorreoPlantilla" to "CartaRefBG",
                        "CorreoXmlPathVar" to "<mail><nombre>${user?.name ?: ""}</nombre></mail>",
                        "dirigidoA" to certRequest.recipient,
                        "idFormaPago" to (account?.accountType ?: ""),
                        "numCtaPago" to (account?.accountNumber ?: ""),
                        "idIdioma" to certRequest.language,
                        "incluirSaldoCta" to certRequest.balanceType,
                        "idCanal" to "358",
                        "idTipoID" to (user?.identificationType ?: ""),
                        "Identificacion" to (user?.identification ?: ""),
                        "codeHostCR" to certRequest.certificateType.extCode,
                        "costoServicio" to (certRequest.certificateCost ?: "0"),
                        "idProductoCR" to certRequest.certificateType.returnCode,
                        "Items" to listOf(
                            mapOf(
                                "cifrasCupo" to product.figuresQuota,
                                "cifrasutil" to product.figuresUtil,
                                "cifrasVcdo" to product.figuresDue,
                                "codigoPro" to product.productCode,
                                "efeDisO" to product.availableCash,
                                "fechaEmi" to product.issueDate,
                                "fechaVen" to product.expirationDate,
                                "idControl" to product.controlId,
                                "nivelCupo" to product.levelQuota,
                                "nivelVcdo" to product.levelDue,
                                "nivLetras" to product.levelLetters,
                                "nivnumero" to product.levelNumber,
                                "nivutil" to product.levelUtil,
                                "oestado" to product.state,
                                "signo" to product.sign,
                                "signoEfeDisO" to product.availableCashSign,
                                "tipoPro" to product.productType,
                                "valor" to product.value
                            )
                        )
                    )
                )
            )

            val response = service.sendRequest<GetCertificateResponse>(
                transaction = "SOLICITUD_CARTA_REFERENCIA",
                details = details,
                responseType = gsonType<GetCertificateResponse>()
            )

            if (response.data == null || response.data?.returnCode != "0000") {
                val code = response.data?.returnCode ?: "ERROR"
                val message = AppMessages.RefBancariaSolicitar.messages[code]
                    ?: AppMessages.RefBancariaSolicitar.messages["ERROR"]
                    ?: ""

                throw ServiceError.ResponseError(code, message)
            }

            true
        }
}

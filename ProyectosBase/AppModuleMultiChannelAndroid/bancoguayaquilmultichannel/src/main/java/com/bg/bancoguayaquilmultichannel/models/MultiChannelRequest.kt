package com.bg.bancoguayaquilmultichannel.models

import com.google.gson.annotations.SerializedName
import com.google.gson.*

/**
 * Un modelo de solicitud optimizado para llamadas a la API MultiChannel que maneja
 * adecuadamente campos dinámicos mientras mantiene la seguridad de tipos
 */
data class MultiChannelRequest(
    @SerializedName("Transaccion")
    val transaccion: Transaction
) {
    /**
     * Define la estructura de la transacción, que contiene un encabezado e información detallada
     */
    data class Transaction(
        @SerializedName("Cabecera")
        val cabecera: Header,
        @SerializedName("Detalle")
        private var detalle: MutableMap<String, JsonElement> = mutableMapOf()
    ) {
        /**
         * Estructura del encabezado con todos los campos requeridos para la API
         */
        data class Header(
            @SerializedName("Agencia")
            var agencia: String,
            @SerializedName("Aplicacion")
            var aplicacion: String,
            @SerializedName("AppVersion")
            var appVersion: String,
            @SerializedName("Canal")
            var canal: String,
            @SerializedName("Codigo")
            var codigo: String,
            @SerializedName("CodigoBanco")
            var codigoBanco: String,
            @SerializedName("CodigoCaja")
            var codigoCaja: String,
            @SerializedName("Dispositivo")
            var dispositivo: String,
            @SerializedName("IdEmpresa")
            var idEmpresa: String,
            @SerializedName("IdTransaccion")
            var idTransaccion: String,
            @SerializedName("Idioma")
            var idioma: String,
            @SerializedName("Operador")
            var operador: String,
            @SerializedName("Sucursal")
            var sucursal: String,
            @SerializedName("Usuario")
            var usuario: String,
            @SerializedName("fingerprint")
            var fingerprint: String,
            private var additionalProperties: MutableMap<String, JsonElement> = mutableMapOf()
        ) {
            /**
             * Actualiza el encabezado con propiedades adicionales desde un diccionario
             */
            fun update(dictionary: Map<String, Any>) {
                val gson = Gson()
                dictionary.forEach { (key, value) ->
                    when (key.lowercase()) {
                        "codigo" -> (value as? String)?.let { codigo = it }
                        "fingerprint" -> (value as? String)?.let { fingerprint = it }
                        "dispositivo" -> (value as? String)?.let { dispositivo = it }
                        "appversion" -> (value as? String)?.let { appVersion = it }
                        else -> additionalProperties[key] = gson.toJsonTree(value)
                    }
                }
            }

            /**
             * Establece una propiedad adicional que será incluida en la salida JSON
             */
            fun setAdditionalProperty(key: String, value: Any) {
                val gson = Gson()
                additionalProperties[key] = gson.toJsonTree(value)
            }
        }

        /**
         * Actualiza el diccionario de detalles de la transacción con nuevos valores
         */
        fun convertToJsonElement(value: Any?): JsonElement {
            val gson = Gson()
            return when (value) {
                null -> JsonNull.INSTANCE
                is String -> JsonPrimitive(value)
                is Number -> JsonPrimitive(value)
                is Boolean -> JsonPrimitive(value)
                is Map<*, *> -> {
                    val jsonObject = JsonObject()
                    value.entries.forEach { entry ->
                        jsonObject.add(entry.key.toString(), convertToJsonElement(entry.value))
                    }
                    jsonObject
                }
                is List<*> -> {
                    val jsonArray = JsonArray()
                    value.forEach { item ->
                        jsonArray.add(convertToJsonElement(item))
                    }
                    jsonArray
                }
                else -> gson.toJsonTree(value)
            }
        }

        fun convertMapToJson(data: Map<String, Any?>): Map<String, JsonElement> {
            return data.mapValues { (_, value) -> convertToJsonElement(value) }
        }

        fun updateDetails(dictionary: Map<String, Any>) {
            dictionary.forEach { (key, value) ->
                detalle[key] = convertToJsonElement(value)
            }
        }
    }

    companion object {
        /**
         * Crea una solicitud completa con valores estándar de encabezado
         */
        fun build(
            codigo: String,
            detalle: Map<String, Any> = emptyMap(),
            canal: String = "BMP",
            agencia: String = "1",
            aplicacion: String = "BMP",
            appVersion: String = "0",
            codigoBanco: String = "0017",
            codigoCaja: String = "001",
            dispositivo: String = "web",
            idEmpresa: String = "1231217",
            idTransaccion: String = "0",
            idioma: String = "ES",
            operador: String = "bg",
            sucursal: String = "01",
            usuario: String = "",
            fingerprint: String = ""
        ): MultiChannelRequest {
            return MultiChannelRequest(
                Transaction(
                    Transaction.Header(
                        agencia = agencia,
                        aplicacion = aplicacion,
                        appVersion = appVersion,
                        canal = canal,
                        codigo = codigo,
                        codigoBanco = codigoBanco,
                        codigoCaja = codigoCaja,
                        dispositivo = dispositivo,
                        idEmpresa = idEmpresa,
                        idTransaccion = idTransaccion,
                        idioma = idioma,
                        operador = operador,
                        sucursal = sucursal,
                        usuario = usuario,
                        fingerprint = fingerprint
                    )
                ).apply {
                    updateDetails(detalle)
                }
            )
        }
    }
}

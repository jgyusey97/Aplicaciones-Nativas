package com.bg.bancoguayaquilmultichannel.services

import io.ktor.http.HttpMethod
import com.google.gson.Gson
import com.bg.bancoguayaquilmultichannel.models.*
import com.bg.bancoguayaquilmultichannel.network.HttpClientWrapper
import com.bg.bancoguayaquilsession.BancoGuayaquilSession
import java.lang.reflect.Type

class MultiChannelService private constructor() {
    private val http = HttpClientWrapper.instance
    private val gson = Gson()
    private var userToken: UserToken? = null
    private var baseUrl: String = ""

    companion object {
        val instance = MultiChannelService()
    }

    suspend fun <T> sendRequest(
        transaction: String,
        details: Map<String, Any>? = null,
        header: Map<String, Any>? = null,
        baseUrl: String = instance.baseUrl,
        timeout: Int = 30,
        responseType: Type,
        channel: String = "BMP"
    ): MultiChannelResponse<T> {
        if (baseUrl.isEmpty()) {
            throw ServiceError.GenericError("URL_MULTICHANNEL is not set")
        }

        val defaultHeaders = mutableMapOf(
            "Content-Type" to "application/json; charset=UTF-8"
        )

        instance.userToken?.let { token ->
            defaultHeaders["AccessToken"] = token.accessToken
        }

        val requestBody = MultiChannelRequest.build(
            codigo = transaction,
            detalle = details ?: emptyMap(),
            canal = channel,
            appVersion = "0",
            usuario = BancoGuayaquilSession.instance.getSession()?.user?.identification ?: "",
            fingerprint = BancoGuayaquilSession.instance.getSession()?.user?.fingerprint ?: "",
        )

        header?.let { h ->
            requestBody.transaccion.cabecera.update(h)
        }

        return try {
            val body = gson.toJson(requestBody)

            val response = http.request<T>(
                url = baseUrl,
                _method = HttpMethod.Post,
                body = body,
                _headers = defaultHeaders,
                timeoutSeconds = timeout,
                responseType = responseType,
            )

            response.apply {
                if (code == "0" ||
                    code == "OK" ||
                    message == "PROCESO OK"
                ) {
                    code = "OK"
                }
            }
        } catch (e: ServiceError) {
            throw e
        } catch (e: BGNetworkError) {
            when (e) {
                is BGNetworkError.Timeout ->
                    throw ServiceError.ResponseError("TIMEOUT_ERROR", "")

                else -> throw ServiceError.NetworkError(e)
            }
        } catch (e: Exception) {
            throw ServiceError.GenericError(e.localizedMessage ?: e.message ?: "")
        }
    }

    fun setBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
    }

    fun setUserToken(userToken: UserToken) {
        this.userToken = userToken
    }
}

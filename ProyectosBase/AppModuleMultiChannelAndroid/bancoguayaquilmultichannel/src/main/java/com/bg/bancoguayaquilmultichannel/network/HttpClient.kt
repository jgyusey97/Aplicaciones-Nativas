package com.bg.bancoguayaquilmultichannel.network

import com.bg.bancoguayaquilmultichannel.models.BGNetworkError
import com.bg.bancoguayaquilmultichannel.models.MultiChannelResponse
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.timeout
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import java.io.IOException
import java.lang.reflect.Type

class HttpClientWrapper private constructor() {
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val client = HttpClient(CIO) {
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
        }
        install(ContentNegotiation) {
            gson {
                setLenient()
            }
        }
    }
    private val _timeout: Int = 30

    companion object {
        @JvmStatic
        val instance: HttpClientWrapper = HttpClientWrapper()
    }

    suspend fun <T> request(
        url: String,
        _method: HttpMethod = HttpMethod.Post,
        body: Any? = null,
        _headers: Map<String, String>? = null,
        timeoutSeconds: Int = _timeout,
        responseType: Type,
        @Suppress("UNUSED_PARAMETER") serviceType: String? = null
    ): MultiChannelResponse<T> {
        try {
            val reqUrl = try {
                Url(url)
            } catch (e: Exception) {
                throw BGNetworkError.InvalidURL
            }

            val response = client.request(reqUrl) {
                method = _method
                timeout {
                    requestTimeoutMillis = (timeoutSeconds).toLong() * 1000
                }

                _headers?.forEach { (key, value) -> header(key, value) }

                if (body != null) {
                    setBody(body)
                }
            }

            if (response.status.value !in 200..500) {
                throw BGNetworkError.ServerError(
                    statusCode = response.status.value,
                    message = "Unacceptable status code"
                )
            }

            val responseBodyText = response.bodyAsText()
            val mChannelResponseType = TypeToken.getParameterized(
                MultiChannelResponse::class.java,
                responseType
            ).type

            return gson.fromJson(responseBodyText, mChannelResponseType)
        } catch (e: BGNetworkError) {
            throw e
        } catch (e: com.google.gson.JsonSyntaxException) {
            throw BGNetworkError.DecodingError(e)
        } catch (_: HttpRequestTimeoutException) {
            throw BGNetworkError.Timeout
        } catch (_: IOException) {
            throw BGNetworkError.NoConnection
        } catch (e: Throwable) {
            throw BGNetworkError.UnknownError(e)
        }
    }
}

package com.bg.bancoguayaquilmultichannel.models

sealed class BGNetworkError : Exception() {
    object InvalidURL : BGNetworkError()
    object InvalidResponse : BGNetworkError()
    object Timeout : BGNetworkError()
    object NoConnection : BGNetworkError()
    data class ServerError(val statusCode: Int, override val message: String) : BGNetworkError()
    data class DecodingError(val error: Throwable) : BGNetworkError()
    data class ConnectionError(val error: Throwable) : BGNetworkError()
    data class UnknownError(val error: Throwable) : BGNetworkError()
}

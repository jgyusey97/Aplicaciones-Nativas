package com.bg.bancoguayaquilmultichannel.models

sealed class ServiceError : Exception() {
    data class NetworkError(val error: Throwable) : ServiceError()
    data class EncryptionFailed(val reason: String) : ServiceError()
    data class ResponseError(val code: String, override val message: String) : ServiceError()
    object SessionExpired : ServiceError()
    object UserWithoutContracts : ServiceError()
    data class CacheError(val reason: String) : ServiceError()
    data class GenericError(val reason: String) : ServiceError()
}

package com.eltescode.core_data.utils.exception

sealed class ServerException(message: String?) : Throwable(message) {
    class Internal(message: String?) : ServerException(message)
    class NotFound(message: String?) : ServerException(message)
    class BadRequest(message: String?) : ServerException(message)
    class Unauthorized(message: String?) : ServerException(message)
    class PaymentRequired(message: String?) : ServerException(message)
    class Forbidden(message: String?) : ServerException(message)
    class Unknown(message: String?) : ServerException(message)

}
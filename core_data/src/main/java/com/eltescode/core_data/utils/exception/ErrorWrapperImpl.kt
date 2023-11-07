package com.eltescode.core_data.utils.exception

import retrofit2.HttpException


class ErrorWrapperImpl : ErrorWrapper {
    override fun wrap(throwable: Throwable): Throwable {
        return when (throwable) {
            is HttpException -> {
                wrapServerException(throwable)
            }

            else -> throwable
        }
    }

    private fun wrapServerException(httpException: HttpException): Throwable {
        return with(httpException) {
            when (httpException.code()) {
                500 -> ServerException.Internal(message)
                400 -> ServerException.BadRequest(message)
                401 -> ServerException.Unauthorized(message)
                402 -> ServerException.PaymentRequired(message)
                403 -> ServerException.Forbidden(message)
                404 -> ServerException.NotFound(message)
                else -> ServerException.Unknown(message)
            }
        }
    }
}

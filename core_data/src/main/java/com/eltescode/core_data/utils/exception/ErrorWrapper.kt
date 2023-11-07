package com.eltescode.core_data.utils.exception

interface ErrorWrapper {
    fun wrap(throwable: Throwable): Throwable
}
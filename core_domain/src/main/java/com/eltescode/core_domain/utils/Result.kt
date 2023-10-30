package com.eltescode.core_domain.utils

sealed interface Result {
    object Success : Result
    data class Error(val message: String?) : Result
}
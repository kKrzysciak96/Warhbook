package com.eltescode.auth_domain.model

data class CustomUser(
    val photo: String? = null,
    val uid: String = "",
    val email: String? = null,
)

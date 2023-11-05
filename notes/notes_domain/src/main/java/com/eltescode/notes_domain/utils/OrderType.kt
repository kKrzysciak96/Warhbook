package com.eltescode.notes_domain.utils

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}

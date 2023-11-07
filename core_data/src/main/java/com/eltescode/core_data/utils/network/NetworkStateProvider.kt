package com.eltescode.core_data.utils.network

interface NetworkStateProvider {
    fun isNetworkAvailable(): Boolean
}
package com.techlambda.common.networkUtils

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class BaseNetworkResponse <T> (
    val statusCode: Int? = null,
    val message: String? = null,
    val data: T? = null
)
package com.samsung.monimo.API.model

data class CalculateRoiResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: Double,
)

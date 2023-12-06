package com.samsung.monimo.API.model

data class ProductListEarningResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: List<TopResult>,
)

data class TopResult(
    val name: String,
    val price: String,
    val roi: Double,
)

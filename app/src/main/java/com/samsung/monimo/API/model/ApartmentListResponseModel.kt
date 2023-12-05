package com.samsung.monimo.API.model

data class ApartmentListResponseModel(
    val isSuccess: Boolean,
    val code: Long,
    val message: String,
    val result: List<ApartmentListResult>,
)

data class ApartmentListResult(
    val apartmentId: Long,
    val longitude: Double,
    val latitude: Double,
    val address: String,
    val apartmentName: String,
    val price: String,
)

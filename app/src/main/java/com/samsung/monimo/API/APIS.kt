package com.samsung.monimo.API

import com.samsung.monimo.API.model.ApartmentListResponseModel
import com.samsung.monimo.API.model.CalculateRoiResponseModel
import com.samsung.monimo.API.model.ProductListEarningResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIS {
    // 설정 목표 리스트 (내 집)
    @GET("apartment/list")
    fun getApartmentList(): Call<ApartmentListResponseModel>

    // 필요 수익률 계산
    @GET("apartment/{apartment-Id}/{goal-Year}")
    fun calculateRoi(
        @Path("apartment-Id") apartmentId: String,
        @Path("goal-Year") globalYear: String,
    ): Call<CalculateRoiResponseModel>

    // 상품 추천 + 수익률
    @GET("product/list/{type}/{roi}")
    fun getProductEarningList(
        @Path("type") type: String,
        @Path("roi") roi: String,
    ): Call<ProductListEarningResponseModel>

    // 상품 추천 + 관심 분야
    @GET("product/list/{type}")
    fun getProductInterestList(
        @Path("type") type: String,
    ): Call<ProductListEarningResponseModel>
}

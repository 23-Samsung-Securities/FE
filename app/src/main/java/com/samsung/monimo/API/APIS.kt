package com.samsung.monimo.API

import com.samsung.monimo.API.model.ApartmentListResponseModel
import com.samsung.monimo.API.model.ProductListEarningResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIS {
    // 설정 목표 리스트 (내 집)
    @GET("apartment/list")
    fun getApartmentList(): Call<ApartmentListResponseModel>
}

package com.samsung.monimo.UI.setting.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samsung.monimo.API.ApiInstance
import com.samsung.monimo.API.model.ApartmentListResponseModel
import com.samsung.monimo.API.model.ApartmentListResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApartmentListViewModel : ViewModel() {
    private val APIS = ApiInstance.retrofitInstance().create(com.samsung.monimo.API.APIS::class.java)

    var apartmentInfoList = MutableLiveData<MutableList<ApartmentListResult>>()

    init {
        apartmentInfoList.value = mutableListOf<ApartmentListResult>()
    }

    fun getApartmentList(context: Context) {
        var tempList = mutableListOf<ApartmentListResult>()

        APIS.getApartmentList().enqueue(object :
            Callback<ApartmentListResponseModel> {
            override fun onResponse(
                call: Call<ApartmentListResponseModel>,
                response: Response<ApartmentListResponseModel>,
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: ApartmentListResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    for (i in 0 until result?.result!!.size) {
                        var apartmentId = result?.result!!.get(i).apartmentId
                        var longitude = result?.result!!.get(i).longitude
                        var latitude = result?.result!!.get(i).latitude
                        var address = result?.result!!.get(i).address
                        var apartmentName = result?.result!!.get(i).apartmentName
                        var price = result?.result!!.get(i).price

                        tempList.add(ApartmentListResult(apartmentId, longitude, latitude, address, apartmentName, price))
                    }

                    apartmentInfoList.value = tempList
                    Log.d("모니모", "${apartmentInfoList.value}")
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        Toast.makeText(context, "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ApartmentListResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }
}

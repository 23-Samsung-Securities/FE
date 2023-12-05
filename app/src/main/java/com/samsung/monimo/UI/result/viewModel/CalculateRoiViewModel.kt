package com.samsung.monimo.UI.result.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samsung.monimo.API.ApiInstance
import com.samsung.monimo.API.model.CalculateRoiResponseModel
import com.samsung.monimo.R
import com.samsung.monimo.UI.result.ResultFragment
import com.samsung.monimo.Utils.MyApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalculateRoiViewModel : ViewModel() {
    private val APIS = ApiInstance.retrofitInstance().create(com.samsung.monimo.API.APIS::class.java)

    var roi = MutableLiveData<Double>()
    var apartmentName = MutableLiveData<String>()
    var period = MutableLiveData<Int>()

    fun calculateRoi(context: Context, manager: FragmentManager) {
        APIS.calculateRoi(MyApplication.selectedApartmentId.toString(), MyApplication.selectedPeriod.toString()).enqueue(object :
            Callback<CalculateRoiResponseModel> {
            override fun onResponse(
                call: Call<CalculateRoiResponseModel>,
                response: Response<CalculateRoiResponseModel>,
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: CalculateRoiResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    roi.value = String.format("%.2f", result?.result).toDouble()
                    apartmentName.value = MyApplication.selectedApartmentName
                    period.value = MyApplication.selectedPeriod

                    // 결과 화면으로 전환
                    val fragment = ResultFragment()

                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.fragmentContainerView, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        Toast.makeText(context, "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<CalculateRoiResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }
}

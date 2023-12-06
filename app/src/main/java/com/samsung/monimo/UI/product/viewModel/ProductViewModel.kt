package com.samsung.monimo.UI.product.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samsung.monimo.API.ApiInstance
import com.samsung.monimo.API.model.ProductListEarningResponseModel
import com.samsung.monimo.API.model.TopResult
import com.samsung.monimo.Utils.MyApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    private val APIS = ApiInstance.retrofitInstance().create(com.samsung.monimo.API.APIS::class.java)

    var productList = MutableLiveData<MutableList<TopResult>>()
    var productInterestList = MutableLiveData<MutableList<TopResult>>()

    init {
        productList.value = mutableListOf<TopResult>()
        productInterestList.value = mutableListOf<TopResult>()
    }

    fun getEarningProductList(context: Context, type: String) {
        var tempList = mutableListOf<TopResult>()

        APIS.getProductEarningList(type, MyApplication.roi).enqueue(object :
            Callback<ProductListEarningResponseModel> {
            override fun onResponse(
                call: Call<ProductListEarningResponseModel>,
                response: Response<ProductListEarningResponseModel>,
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: ProductListEarningResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    for (i in 0 until result?.result!!.size) {
                        var name = result?.result!!.get(i).name
                        var roi = result?.result!!.get(i).roi
                        var price = result?.result!!.get(i).price

                        tempList.add(TopResult(name, price, roi))
                    }

                    productList.value = tempList
                    Log.d("모니모", "${productList.value}")
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        Toast.makeText(context, "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ProductListEarningResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }

    fun getInterestProductList(context: Context, type: String) {
        var tempList = mutableListOf<TopResult>()

        APIS.getProductInterestList(type).enqueue(object :
            Callback<ProductListEarningResponseModel> {
            override fun onResponse(
                call: Call<ProductListEarningResponseModel>,
                response: Response<ProductListEarningResponseModel>,
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: ProductListEarningResponseModel? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    for (i in 0 until result?.result!!.size) {
                        var name = result?.result!!.get(i).name
                        var roi = result?.result!!.get(i).roi
                        var price = result?.result!!.get(i).price

                        tempList.add(TopResult(name, price, roi))
                    }

                    productInterestList.value = tempList
                    Log.d("모니모", "${productInterestList.value}")
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("##", "onResponse 실패: " + response.code())
                    Log.d("##", "onResponse 실패: " + response.message())

                    if (response.code() == 400) {
                        Toast.makeText(context, "다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ProductListEarningResponseModel>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString())
            }
        })
    }
}

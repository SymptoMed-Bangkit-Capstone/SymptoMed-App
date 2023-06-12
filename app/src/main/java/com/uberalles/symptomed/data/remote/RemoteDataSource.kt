package com.uberalles.symptomed.data.remote

import android.util.Log
import com.uberalles.symptomed.data.remote.api.ApiConfig
import com.uberalles.symptomed.data.remote.response.DiseaseResponse
import com.uberalles.symptomed.data.remote.response.SymptomRequestBody
import com.uberalles.symptomed.utilities.JsonHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getSymptoms(query: String, callback: LoadDisease) {
        val requestBody = SymptomRequestBody(query)
        ApiConfig.getApiService().getSymptom(requestBody)
            .enqueue(object : Callback<DiseaseResponse> {
                override fun onResponse(
                    call: Call<DiseaseResponse>,
                    response: Response<DiseaseResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { callback.diseaseResult(it) }
                    } else {
                        Log.e("Gagal", response.message())
                    }
                }

                override fun onFailure(call: Call<DiseaseResponse>, t: Throwable) {
                    Log.e("Gagal", t.toString())
                }
            })
    }

    interface LoadDisease {
        fun diseaseResult(diseaseResponse: DiseaseResponse)
    }
}
package com.uberalles.symptomed.data.remote.api

import com.uberalles.symptomed.data.remote.response.DiseaseResponse
import com.uberalles.symptomed.data.remote.response.SymptomRequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("/")
    fun getSymptom(
        @Body requestBody: SymptomRequestBody
    ): Call<DiseaseResponse>
}
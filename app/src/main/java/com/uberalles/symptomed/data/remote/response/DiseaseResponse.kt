package com.uberalles.symptomed.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiseaseResponse(

    @field:SerializedName("Kelas")
    val kelas: String? = null,

    @field:SerializedName("Rekomendasi")
    val rekomendasi: String? = null,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("Probabilitas")
    val probabilitas: String? = null
) : Parcelable



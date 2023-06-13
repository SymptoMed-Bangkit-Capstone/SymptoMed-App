package com.uberalles.symptomed.data.remote.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiseaseEntity(
    val kelas: String? = null,
    val probabilitas: String? = null,
    val link: String? = null,
    val rekomendasi: String? = null
) : Parcelable

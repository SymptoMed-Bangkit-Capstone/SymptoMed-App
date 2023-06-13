package com.uberalles.symptomed.utilities

import androidx.lifecycle.LiveData
import com.uberalles.symptomed.data.remote.entity.DiseaseEntity

interface SymptoMedDataSource {
    fun getDisease(query: String): LiveData<DiseaseEntity>
}
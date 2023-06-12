package com.uberalles.symptomed.data

import androidx.lifecycle.LiveData

interface SymptoMedDataSource {
    fun getDisease(query: String): LiveData<DiseaseEntity>
}
package com.uberalles.symptomed.utilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uberalles.symptomed.data.remote.RemoteDataSource
import com.uberalles.symptomed.data.remote.entity.DiseaseEntity
import com.uberalles.symptomed.data.remote.response.DiseaseResponse

class SymptoMedRepository private constructor(private val remoteDataSource: RemoteDataSource) :
    SymptoMedDataSource {

    companion object {
        @Volatile
        private var instance: SymptoMedRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): SymptoMedRepository =
            instance ?: synchronized(this) {
                instance ?: SymptoMedRepository(remoteDataSource).apply { instance = this }
            }
    }

    override fun getDisease(query: String): LiveData<DiseaseEntity> {
        val diseaseResult = MutableLiveData<DiseaseEntity>()
        remoteDataSource.getSymptoms(
            query, object : RemoteDataSource.LoadDisease {
                override fun diseaseResult(diseaseResponse: DiseaseResponse) {
                    val responseResultDisease = DiseaseEntity(
                        diseaseResponse.kelas,
                        diseaseResponse.probabilitas,
                        diseaseResponse.link,
                        diseaseResponse.rekomendasi
                    )
                    diseaseResult.postValue(responseResultDisease)
                }

            }
        )
        return diseaseResult
    }

}
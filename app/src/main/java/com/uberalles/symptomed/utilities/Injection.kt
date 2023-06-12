package com.uberalles.symptomed.utilities

import android.content.Context
import com.uberalles.symptomed.data.SymptoMedRepository
import com.uberalles.symptomed.data.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): SymptoMedRepository{
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))

        return SymptoMedRepository.getInstance(remoteDataSource)
    }
}
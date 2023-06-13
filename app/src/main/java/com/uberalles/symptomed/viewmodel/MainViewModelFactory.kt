package com.uberalles.symptomed.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uberalles.symptomed.utilities.SymptoMedRepository
import com.uberalles.symptomed.utilities.Injection

class MainViewModelFactory private constructor(private val mSymptoMedRepository: SymptoMedRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: MainViewModelFactory? = null

        fun getInstance(context: Context): MainViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MainViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                return MainViewModel(mSymptoMedRepository) as T
            }
            else ->throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
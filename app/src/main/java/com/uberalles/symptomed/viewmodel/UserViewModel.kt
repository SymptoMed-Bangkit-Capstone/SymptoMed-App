package com.uberalles.symptomed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.uberalles.symptomed.firebase.FirebaseCallback
import com.uberalles.symptomed.firebase.Response
import com.uberalles.symptomed.firebase.UserRepository

class UserViewModel(private val repository: UserRepository = UserRepository()): ViewModel() {
    fun getResponseUsingCallback(callback: FirebaseCallback) {
        repository.getResponseFromRealtimeDatabaseUsingCallback(callback)
    }
    fun getResponseUsingLiveData() : LiveData<Response> {
        return repository.getResponseFromRealtimeDatabaseUsingLiveData()
    }
}
package com.uberalles.symptomed.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference(Firebase.auth.currentUser!!.uid),

) {
    fun getResponseFromRealtimeDatabaseUsingCallback(callback: FirebaseCallback) {
        rootRef.get().addOnCompleteListener { task ->
            val response = Response()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.userId = result.children.map { snapShot ->

                        snapShot.getValue(User::class.java)!!
                    }
                }
            } else {
                response.exception = task.exception
            }
            callback.onResponse(response)
        }
    }

    fun getResponseFromRealtimeDatabaseUsingLiveData() : MutableLiveData<Response> {
        val mutableLiveData = MutableLiveData<Response>()
        rootRef.get().addOnCompleteListener { task ->
            val response = Response()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.userId = result.children.map { snapShot ->
                        snapShot.getValue(User::class.java)!!
                    }
                }
            } else {
                response.exception = task.exception
            }
            mutableLiveData.value = response
        }
        return mutableLiveData
    }

}
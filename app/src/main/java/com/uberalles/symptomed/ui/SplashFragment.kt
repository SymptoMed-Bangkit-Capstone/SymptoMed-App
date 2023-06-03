package com.uberalles.symptomed.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        firebaseAuth = Firebase.auth

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkUserStatus()
    }

    private fun checkUserStatus() {
        val user = firebaseAuth.currentUser
        val reference = user?.let {
            FirebaseDatabase.getInstance("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/").reference.child(
                "userId"
            ).child(
                it.uid
            )
        }

        if (user != null) {
            Log.d("SplashFragment", "User ID: ${user.uid}")
        } else {
            Log.d("SplashFragment", "User ID: null")
        }

        val name = reference?.child("name")?.get()?.addOnSuccessListener {
            Log.i("Firebase", "Got value ${it.value}")
        }?.addOnFailureListener {
            Log.e("Firebase", "Error getting data", it)
        }

        val gender = reference?.child("gender")?.get()?.addOnSuccessListener {
            Log.i("Firebase", "Got value ${it.value}")
        }?.addOnFailureListener {
            Log.e("Firebase", "Error getting data", it)
        }

        val age = reference?.child("age")?.get()?.addOnSuccessListener {
            Log.i("Firebase", "Got value ${it.value}")
        }?.addOnFailureListener {
            Log.e("Firebase", "Error getting data", it)
        }

        if (user != null) {
            if (name == null) {
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_splashFragment_to_nameFragment)
                }, 2000)
                Log.d("SplashFragment", "Name is null")
                return;
            }
            if (gender == null) {
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_splashFragment_to_genderFragment)
                }, 3000)
                Log.d("SplashFragment", "Gender is null")
                return;
            }
            if (age == null) {
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_splashFragment_to_ageFragment)
                }, 3000)
                Log.d("SplashFragment", "Age is null")
                return;
            }
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_mainActivity)
            }, 3000)
            Log.d("SplashFragment", "$name - $gender - $age")
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
            }, 3000)
            Log.d("SplashFragment", "User data is empty")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
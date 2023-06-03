package com.uberalles.symptomed.ui

import android.os.Bundle
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

        checkStatus()
    }

    private fun checkStatus() {
        val user = Firebase.auth.currentUser
        val reference =
            FirebaseDatabase.getInstance("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child(
                user?.uid.toString()
            )

        Log.d("SplashFragment", "User is $user, reference is $reference")

        reference.child("name").get().addOnCompleteListener { nameTask ->
            val name = nameTask.result.value.toString()
            if (name.isEmpty()) {
                findNavController().navigate(R.id.action_splashFragment_to_nameFragment)
                Log.d("SplashFragment", "Name is empty")
                return@addOnCompleteListener
            }

            reference.child("gender").get().addOnCompleteListener { genderTask ->
                val gender = genderTask.result.value.toString()
                if (gender.isEmpty()) {
                    findNavController().navigate(R.id.action_splashFragment_to_genderFragment)
                    Log.d("SplashFragment", "Gender is empty")
                    return@addOnCompleteListener
                }

                reference.child("age").get().addOnCompleteListener { ageTask ->
                    val age = ageTask.result.value.toString()
                    if (age.isEmpty()) {
                        findNavController().navigate(R.id.action_splashFragment_to_ageFragment)
                        Log.d("SplashFragment", "Age is empty")
                        return@addOnCompleteListener
                    }

                    // All fields are present
                    Log.d("SplashFragment", "The field that are present \n$name, $gender, $age")
                    if (user != null) {
                        findNavController().navigate(R.id.action_splashFragment_to_mainActivity)
                        Log.d("SplashFragment", "User data is not null, goes to main activity")
                    } else {
                        Log.d("SplashFragment", "User is null, goes to sign in fragment")
                        findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
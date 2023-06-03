package com.uberalles.symptomed.auth

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        firebaseAuth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        formatError()
        signIn()
        signUp()
    }

    private fun formatError() {
        binding.apply {
            email.addTextChangedListener {
                if (it.toString().isEmpty()) {
                    email.error = "Email is required"
                } else if (!Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()) {
                    email.error = "Email is not valid"
                } else {
                    email.error = null
                }
            }
        }
    }

    private fun signIn() {
        binding.btnSignIn.setOnClickListener {

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {

                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = Firebase.auth.currentUser
                            val reference =
                                FirebaseDatabase.getInstance("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child(
                                    user?.uid.toString()
                                )

                            reference.child("name").get().addOnCompleteListener { nameTask ->
                                val name = nameTask.result?.value?.toString()
                                if (name.isNullOrEmpty()) {
                                    findNavController().navigate(R.id.action_signInFragment_to_nameFragment)
                                    Log.d("SignInFragment", "Name is empty")
                                    return@addOnCompleteListener
                                }

                                reference.child("gender").get()
                                    .addOnCompleteListener { genderTask ->
                                        val gender = genderTask.result?.value?.toString()
                                        if (gender.isNullOrEmpty()) {
                                            findNavController().navigate(R.id.action_signInFragment_to_genderFragment)
                                            Log.d("SignInFragment", "Gender is empty")
                                            return@addOnCompleteListener
                                        }

                                        reference.child("age").get()
                                            .addOnCompleteListener { ageTask ->
                                                val age = ageTask.result?.value?.toString()
                                                if (age.isNullOrEmpty()) {
                                                    findNavController().navigate(R.id.action_signInFragment_to_ageFragment)
                                                    Log.d("SignInFragment", "Age is empty")
                                                    return@addOnCompleteListener
                                                }

                                                // All fields are present
                                                Log.d(
                                                    "Firebase",
                                                    "Name: $name, gender: $gender, age: $age"
                                                )
                                                if (user != null) {
                                                    findNavController().navigate(R.id.action_signInFragment_to_mainActivity)
                                                    Log.d("SignInFragment", "User data is not null")
                                                } else {
                                                    findNavController().navigate(R.id.action_signInFragment_to_nameFragment)
                                                    Log.d("SignInFragment", "User data is null")
                                                }
                                                Log.d("SignInFragment", "$email $password")
                                            }
                                    }
                            }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Sign in failed. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

//                        if (task.isSuccessful) {
//                            val user = Firebase.auth.currentUser
//                            val reference =
//                                FirebaseDatabase.getInstance("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child(
//                                    user?.uid.toString()
//                                )
//
//                            val name = reference.child("name").get()
//                            val gender = reference.child("gender").get()
//                            val age = reference.child("age").get()
//
//                            Log.d("Firebase", "Name: $name, gender: $gender, age: $age")
//
//                            if (user != null) {
//
//                                Log.d("SignInFragment", "User data is not null")
//                            } else {
//                                findNavController().navigate(R.id.action_signInFragment_to_nameFragment)
//                                Log.d("SignInFragment", "User data is null")
//                            }
//                            Log.d("SignInFragment", "$email $password")
//                        } else {
//                            Toast.makeText(
//                                requireContext(),
//                                "Sign in failed. Please try again.",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
                    }
            }
        }
    }

    private fun signUp() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
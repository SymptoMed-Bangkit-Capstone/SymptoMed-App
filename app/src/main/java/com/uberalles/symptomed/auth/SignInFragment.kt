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
import com.google.firebase.database.ktx.database
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
            password.addTextChangedListener {
//                if (it.toString().length < 8) {
//                    password.error = "Password must be at least 8 characters"
//                } else {
//                    password.error = null
//                }
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
                firebaseAuth = FirebaseAuth.getInstance()

                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

//                            val sharedPref = activity?.getSharedPreferences("user", 0)
//                            val editor = sharedPref?.edit()
//                            editor?.putString("email", email)
//                            editor?.putString("password", password)
//                            editor?.apply()

                            val database =
                                Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/")
                            val userId = database.reference.child("userId")
                                .child(firebaseAuth.currentUser!!.uid)
                            userId.setValue(firebaseAuth.currentUser!!.uid)

                            findNavController().navigate(R.id.action_signInFragment_to_nameFragment)

                            Log.d("SignInFragment", "$email $password")
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Sign in failed. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
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
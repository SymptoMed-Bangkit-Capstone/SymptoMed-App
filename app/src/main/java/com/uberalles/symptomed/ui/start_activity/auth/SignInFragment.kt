package com.uberalles.symptomed.ui.start_activity.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.databinding.FragmentSignInBinding
import com.uberalles.symptomed.ui.start_activity.intro.AgeFragment
import com.uberalles.symptomed.ui.start_activity.intro.GenderFragment
import com.uberalles.symptomed.ui.start_activity.intro.NameFragment
import com.uberalles.symptomed.ui.start_activity.StartActivity
import com.uberalles.symptomed.ui.main_activity.MainActivity

class SignInFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        firebaseAuth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                activity?.finish()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

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
                            val emailDb = reference.child("email")
                            emailDb.setValue(email)

                            reference.child("name").get().addOnCompleteListener { nameTask ->
                                val name = nameTask.result?.value?.toString()
                                if (name.isNullOrEmpty()) {
                                    (activity as StartActivity).navigationFragment(NameFragment())
                                    Log.d("SignInFragment", "Name is empty")
                                    return@addOnCompleteListener
                                }

                                reference.child("gender").get()
                                    .addOnCompleteListener { genderTask ->
                                        val gender = genderTask.result?.value?.toString()
                                        if (gender.isNullOrEmpty()) {
                                            (activity as StartActivity).navigationFragment(
                                                GenderFragment()
                                            )
                                            Log.d("SignInFragment", "Gender is empty")
                                            return@addOnCompleteListener
                                        }

                                        reference.child("age").get()
                                            .addOnCompleteListener { ageTask ->
                                                val age = ageTask.result?.value?.toString()
                                                if (age.isNullOrEmpty()) {
                                                    (activity as StartActivity).navigationFragment(
                                                        AgeFragment()
                                                    )
                                                    Log.d("SignInFragment", "Age is empty")
                                                    return@addOnCompleteListener
                                                }

                                                Log.d(
                                                    "Firebase",
                                                    "Name: $name, gender: $gender, age: $age"
                                                )
                                                if (user != null) {
                                                    val intent = Intent(
                                                        requireContext(),
                                                        MainActivity::class.java
                                                    )
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                                    startActivity(intent)
                                                    activity?.finish()
                                                } else {
                                                    (activity as StartActivity).navigationFragment(
                                                        NameFragment()
                                                    )
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
                    }
            }
        }
    }

    private fun signUp() {
        binding.tvSignUp.setOnClickListener {
            (activity as StartActivity).navigationFragment(SignUpFragment())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
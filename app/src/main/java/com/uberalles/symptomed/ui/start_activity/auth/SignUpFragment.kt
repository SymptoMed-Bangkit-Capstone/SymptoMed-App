package com.uberalles.symptomed.ui.start_activity.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.uberalles.symptomed.databinding.FragmentSignUpBinding
import com.uberalles.symptomed.ui.start_activity.StartActivity

class SignUpFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        formatError()
        signIn()
        signUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as StartActivity).backToSignInFragment()
            }
        })
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
                if (it.toString().length < 6) {
                    password.error = "Password must be at least 6 characters"
                } else {
                    password.error = null
                }
            }
        }
    }

    private fun signUp() {
        binding.btnSignUp.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                firebaseAuth = FirebaseAuth.getInstance()

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Signed Up Successfully", Toast.LENGTH_SHORT)
                                .show()
                            (activity as StartActivity).navigationFragment(SignInFragment())
                        } else {
                            Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }


        }
    }

    private fun signIn() {
        binding.tvSignIn.setOnClickListener {
            (activity as StartActivity).navigationFragment(SignInFragment())
        }
    }

}
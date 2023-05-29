package com.uberalles.symptomed.ui

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkUserStatus()
    }

    private fun checkUserStatus() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_nameFragment)
            }, 3000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
            }, 3000)
        }

//        val sharedPreferences = requireActivity().getSharedPreferences("user", MODE_PRIVATE)
//        val email = sharedPreferences.getString("email", "")
//        val password = sharedPreferences.getString("password", "")

//        if (email != "" && password != "") {
//            Handler(Looper.getMainLooper()).postDelayed({
//                findNavController().navigate(R.id.action_splashFragment_to_nameFragment)
//                Log.d("SplashFragment", "$email $password")
//            }, 3000)
//        } else {
//            Handler(Looper.getMainLooper()).postDelayed({
//                findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
//                Log.d("SplashFragment", "$email $password")
//            }, 3000)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
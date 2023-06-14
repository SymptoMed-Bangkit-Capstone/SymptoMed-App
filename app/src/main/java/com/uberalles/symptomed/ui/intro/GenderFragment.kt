package com.uberalles.symptomed.ui.intro

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentGenderBinding

class GenderFragment : Fragment() {
//    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentGenderBinding? = null
    private val binding get() = _binding!!
//    private lateinit var bundle: Bundle

    companion object {
        const val EXTRA_GENDER = "extra_gender"
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenderBinding.inflate(layoutInflater, container, false)
//        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bundle = Bundle()
        firebaseAuth = FirebaseAuth.getInstance()

        genderResult()
        nextButton()
    }

    private fun nextButton() {
        binding.button.setOnClickListener {
            val gender = binding.radioGender.checkedRadioButtonId
            val database = Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val genderDatabase = database.reference.child(firebaseAuth.currentUser!!.uid).child("gender")
            if (gender == binding.maleRadio.id) {
//                bundle.putString(EXTRA_GENDER, binding.maleRadio.text.toString())
//                bundle.putString(EXTRA_NAME, arguments?.getString(EXTRA_NAME))
                //save to shared preferences
//                val editor = sharedPreferences.edit()
//                editor.putString("gender", binding.maleRadio.text.toString())
//                editor.apply()
//                Toast.makeText(
//                    requireContext(),
//                    sharedPreferences.getString("gender", ""),
//                    Toast.LENGTH_SHORT
//                ).show()
                //save to firebase
                genderDatabase.setValue(binding.maleRadio.text.toString())
                (activity as StartActivity).navigationFragment(AgeFragment())
            } else {
//                bundle.putString(EXTRA_GENDER, binding.femaleRadio.text.toString())
//                bundle.putString(EXTRA_NAME, arguments?.getString(EXTRA_NAME))
                //save to shared preferences
//                val editor = sharedPreferences.edit()
//                editor.putString("gender", binding.femaleRadio.text.toString())
//                editor.apply()
//                Toast.makeText(
//                    requireContext(),
//                    sharedPreferences.getString("gender", ""),
//                    Toast.LENGTH_SHORT
//                ).show()
                //save to firebase
                genderDatabase.setValue(binding.femaleRadio.text.toString())
                (activity as StartActivity).navigationFragment(AgeFragment())
            }
        }
    }

    private fun genderResult() {
        binding.apply {
            radioGender.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    binding.maleRadio.id -> {
                        visible()
                    }

                    binding.femaleRadio.id -> {
                        visible()
                    }
                }
            }
        }
    }

    private fun visible() {
        binding.button.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(1000).setListener(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
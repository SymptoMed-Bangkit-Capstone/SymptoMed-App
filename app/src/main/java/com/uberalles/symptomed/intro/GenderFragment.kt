package com.uberalles.symptomed.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentGenderBinding

class GenderFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentGenderBinding? = null
    private val binding get() = _binding!!
    private lateinit var bundle: Bundle

    companion object {
        const val EXTRA_GENDER = "extra_gender"
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentGenderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundle = Bundle()
        firebaseAuth = FirebaseAuth.getInstance()

        genderResult()
        nextButton()
    }

    private fun nextButton() {
        binding.button.setOnClickListener {
            val gender = binding.radioGender.checkedRadioButtonId
            val database = Firebase.database("https://symptomed-bf727-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val genderDatabase = database.reference.child("userId").child(firebaseAuth.currentUser!!.uid).child("gender")
            if (gender == binding.maleRadio.id) {
                bundle.putString(EXTRA_GENDER, binding.maleRadio.text.toString())
                bundle.putString(EXTRA_NAME, arguments?.getString(EXTRA_NAME))
                genderDatabase.setValue(binding.maleRadio.text.toString())
                findNavController().navigate(R.id.action_genderFragment_to_ageFragment, bundle)
            } else {
                bundle.putString(EXTRA_GENDER, binding.femaleRadio.text.toString())
                bundle.putString(EXTRA_NAME, arguments?.getString(EXTRA_NAME))
                genderDatabase.setValue(binding.femaleRadio.text.toString())
                findNavController().navigate(R.id.action_genderFragment_to_ageFragment, bundle)
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


}
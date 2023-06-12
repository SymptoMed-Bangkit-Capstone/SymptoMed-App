package com.uberalles.symptomed.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentOnlineSymptomBinding

class OnlineSymptomFragment : Fragment() {
    private var _binding : FragmentOnlineSymptomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOnlineSymptomBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).hideNavBottom(true)
        backToHome()
        predictSymptom()
    }

    private fun predictSymptom() {
        binding.btnPrediksi.setOnClickListener {
            val symptom = binding.etGejala.text.toString()
            Toast.makeText(requireContext(), symptom, Toast.LENGTH_SHORT).show()
        }
    }

    private fun backToHome() {
        binding.btnBack.setOnClickListener {
            (activity as MainActivity).backToHome()
            (activity as MainActivity).hideNavBottom(false)
        }
    }

}
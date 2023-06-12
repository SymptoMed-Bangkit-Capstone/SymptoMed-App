package com.uberalles.symptomed.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uberalles.symptomed.databinding.FragmentDiseaseBinding

class DiseaseFragment : Fragment() {

    private var _binding: FragmentDiseaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiseaseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val diagnosa = arguments?.getString("diagnosa")
        val probabilitas = arguments?.getString("probabilitas")
        val saran = arguments?.getString("saran")
        val wiki = arguments?.getString("wiki")

        binding.tvPenyakit.text = diagnosa
        binding.tvProbabilitas.text = probabilitas
        binding.tvSaran.text = saran

        Log.d("Diagnosa", diagnosa.toString())
        Log.d("Probabilitas", probabilitas.toString())
        Log.d("Saran", saran.toString())
        Log.d("Wiki", wiki.toString())

        backToOnlineSymptomFragment()
    }

    private fun backToOnlineSymptomFragment() {
        binding.btnBack.setOnClickListener {
            (activity as MainActivity).onlineFragment()
        }
    }
}
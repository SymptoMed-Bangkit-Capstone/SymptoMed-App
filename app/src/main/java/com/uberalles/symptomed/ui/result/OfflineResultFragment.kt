package com.uberalles.symptomed.ui.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uberalles.symptomed.databinding.FragmentOfflineResultBinding
import com.uberalles.symptomed.ui.main.MainActivity


class OfflineResultFragment : Fragment() {

    private var _binding: FragmentOfflineResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOfflineResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getResult()
        backToOfflineSymptomFragment()
    }

    private fun backToOfflineSymptomFragment() {
        binding.btnBack.setOnClickListener {
            (activity as MainActivity).offlineFragment()
        }
    }

    private fun getResult() {
        val diagnosa = arguments?.getString("diagnosa")
        val probabilitas = arguments?.getString("probabilitas")
        val saran = arguments?.getString("saran")
        val wiki = arguments?.getString("wiki")

        binding.tvPenyakit.text = diagnosa
        binding.tvSaran.text = saran

        if (diagnosa == "Karakter terlalu sedikit"){
            binding.tvWikipedia.visibility = View.INVISIBLE
            binding.tvProbabilitas.text = "Tidak diketahui"
        } else {
            binding.tvWikipedia.visibility = View.VISIBLE
            binding.tvProbabilitas.text = probabilitas
        }

        binding.tvWikipedia.setOnClickListener {
            val urlIntent = Intent(Intent.ACTION_VIEW)
            urlIntent.data = Uri.parse(wiki)
            startActivity(urlIntent)
        }

        Log.d("Diagnosa", diagnosa.toString())
        Log.d("Probabilitas", probabilitas.toString())
        Log.d("Saran", saran.toString())
        Log.d("Wiki", wiki.toString())
    }

}
package com.uberalles.symptomed.ui.main_activity.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.uberalles.symptomed.databinding.FragmentOfflineResultBinding
import com.uberalles.symptomed.ui.main_activity.MainActivity
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as MainActivity).offlineFragment()
            }
        })
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

        val paramsCardViewProbabilitas = binding.cardViewProbabilitas.layoutParams as LayoutParams
        val paramsCardViewRekomendasi = binding.cardViewRekomendasi.layoutParams as LayoutParams

        if (diagnosa == "Karakter terlalu sedikit" || diagnosa == "Tidak Ada Kecocokan") {
            paramsCardViewProbabilitas.height = 0
            paramsCardViewProbabilitas.topMargin = 0
            paramsCardViewRekomendasi.topToBottom = binding.cardViewDiagnosa.id
            binding.cardViewSpace.visibility = View.INVISIBLE
            binding.cardViewProbabilitas.visibility = View.GONE
            binding.tvWikipedia.visibility = View.GONE
            binding.cardViewDisclaimer.visibility = View.GONE
        } else {
            binding.cardViewSpace.visibility = View.INVISIBLE
            binding.tvWikipedia.visibility = View.VISIBLE
            binding.tvProbabilitas.text = probabilitas
        }

        binding.cardViewProbabilitas.layoutParams = paramsCardViewProbabilitas
        binding.cardViewRekomendasi.layoutParams = paramsCardViewRekomendasi

        binding.tvWikipedia.setOnClickListener {
            val urlIntent = Intent(Intent.ACTION_VIEW)
            urlIntent.data = Uri.parse(wiki)
            startActivity(urlIntent)
        }
    }

}
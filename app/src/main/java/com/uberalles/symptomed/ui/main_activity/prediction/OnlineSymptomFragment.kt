package com.uberalles.symptomed.ui.main_activity.prediction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uberalles.symptomed.R
import com.uberalles.symptomed.data.remote.api.ApiConfig
import com.uberalles.symptomed.databinding.FragmentOnlineSymptomBinding
import com.uberalles.symptomed.ui.main_activity.MainActivity
import com.uberalles.symptomed.ui.main_activity.result.OnlineResultFragment
import com.uberalles.symptomed.viewmodel.MainViewModel
import com.uberalles.symptomed.viewmodel.MainViewModelFactory

class OnlineSymptomFragment : Fragment() {
    private var _binding: FragmentOnlineSymptomBinding? = null
    private val binding get() = _binding!!
    private lateinit var apiConfig: ApiConfig
    private lateinit var viewModel: MainViewModel
    private lateinit var bundle: Bundle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOnlineSymptomBinding.inflate(layoutInflater, container, false)
        apiConfig = ApiConfig()

        val factory = MainViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        bundle = Bundle()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as MainActivity).backToHome()
                (activity as MainActivity).hideNavBottom(false)
            }
        })
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
            if (symptom.isEmpty()) {
                Toast.makeText(activity, "Gejala tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.getDisease(symptom).observe(viewLifecycleOwner) { disease ->
                    binding.progressBar.visibility = View.VISIBLE
                    if (disease != null) {
                        binding.progressBar.visibility = View.INVISIBLE
                        bundle.putString(DIAGNOSA, disease.kelas)
                        bundle.putString(PROBABILITAS, disease.probabilitas)
                        bundle.putString(SARAN, disease.rekomendasi)
                        bundle.putString(WIKI, disease.link)

                        val onlineResultFragment = OnlineResultFragment()
                        onlineResultFragment.arguments = bundle

                        val fragment = requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container_main, onlineResultFragment)
                            .addToBackStack(null)
                        fragment.commit()

                    } else {
                        Toast.makeText(activity, "Gagal mendapatkan hasil", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun backToHome() {
        binding.btnBack.setOnClickListener {
            (activity as MainActivity).backToHome()
            (activity as MainActivity).hideNavBottom(false)
        }
    }

    companion object {
        const val DIAGNOSA = "diagnosa"
        const val PROBABILITAS = "probabilitas"
        const val SARAN = "saran"
        const val WIKI = "wiki"
    }

}
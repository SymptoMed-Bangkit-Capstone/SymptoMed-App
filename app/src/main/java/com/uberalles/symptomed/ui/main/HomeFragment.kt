package com.uberalles.symptomed.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        offlineSymptom()
        onlineSymptom()
    }

    private fun onlineSymptom() {
        binding.ivOnlinePrediction.setOnClickListener {
            fragmentNav(OnlineSymptomFragment())
        }
    }

    private fun offlineSymptom() {
        binding.ivOfflinePrediction.setOnClickListener {
            fragmentNav(OfflineSymptomFragment())
        }
    }

    private fun fragmentNav(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction().replace(R.id.fragment_container_main, fragment)
        fragmentTransaction.commit()
    }

}
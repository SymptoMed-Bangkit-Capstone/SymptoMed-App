package com.uberalles.symptomed.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentSettingBinding
import com.uberalles.symptomed.ui.intro.NameFragment

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogout.setOnClickListener {
                (activity as MainActivity).logout()
            }
            btnEditProfile.setOnClickListener {
                val fragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction =
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_main, EditProfileFragment())
                fragmentTransaction.commit()
            }
        }
    }
}
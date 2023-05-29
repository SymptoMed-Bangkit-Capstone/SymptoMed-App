package com.uberalles.symptomed.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentLogOutBinding

class LogOutFragment : Fragment() {

    private var _binding: FragmentLogOutBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): LogOutFragment {
            return LogOutFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentLogOutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {
            findNavController().navigate(R.id.action_logOutFragment_to_signInFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.uberalles.symptomed.ui.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.uberalles.symptomed.R
import com.uberalles.symptomed.databinding.FragmentNameBinding

class NameFragment : Fragment() {
    private var _binding: FragmentNameBinding? = null
    private val binding get() = _binding!!
    private lateinit var bundle: Bundle

    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundle = Bundle()

        nameResult()
        nextButton()
    }


    private fun nameResult() {
        binding.apply {
            tvName.addTextChangedListener {
                if (it.toString().isEmpty()) {
                    binding.button.apply {
                        visibility = View.INVISIBLE
                    }
                } else {
                    binding.button.apply {
                        alpha = 0f
                        visibility = View.VISIBLE
                        animate().alpha(1f).setDuration(1000).setListener(null)
                    }
                }
            }
        }
    }

    private fun nextButton() {
        binding.apply {
            button.setOnClickListener {
                bundle.putString(EXTRA_NAME, binding.tvName.text.toString())

                findNavController().navigate(R.id.action_nameFragment_to_genderFragment, bundle)
            }
        }
    }
}

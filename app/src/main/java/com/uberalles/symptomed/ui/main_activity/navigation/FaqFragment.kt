package com.uberalles.symptomed.ui.main_activity.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uberalles.symptomed.adapter.FaqAdapter
import com.uberalles.symptomed.databinding.FragmentFaqBinding
import com.uberalles.symptomed.viewmodel.MainViewModel
import com.uberalles.symptomed.viewmodel.MainViewModelFactory

class FaqFragment : Fragment() {
    private var _binding: FragmentFaqBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FaqAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFaqBinding.inflate(inflater, container, false)
        val factory = MainViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        val faq = viewModel.getFaqList()

        adapter = FaqAdapter()
        adapter.setFaq(faq)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFaq.layoutManager = LinearLayoutManager(context)
        binding.rvFaq.setHasFixedSize(true)
        binding.rvFaq.adapter = adapter
    }

}
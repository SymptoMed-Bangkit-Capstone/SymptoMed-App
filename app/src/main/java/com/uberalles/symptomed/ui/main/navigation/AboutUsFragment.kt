package com.uberalles.symptomed.ui.main.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uberalles.symptomed.adapter.AboutUsAdapter
import com.uberalles.symptomed.databinding.FragmentAboutUsBinding
import com.uberalles.symptomed.viewmodel.MainViewModel
import com.uberalles.symptomed.viewmodel.MainViewModelFactory

class AboutUsFragment : Fragment() {

    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AboutUsAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        val factory = MainViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(
            this, factory
        )[MainViewModel::class.java]
        val members = viewModel.getTeamMembers()

        adapter = AboutUsAdapter()
        adapter.setMember(members)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTeamMember.layoutManager = LinearLayoutManager(context)
        binding.rvTeamMember.setHasFixedSize(true)
        binding.rvTeamMember.adapter = adapter
    }

}
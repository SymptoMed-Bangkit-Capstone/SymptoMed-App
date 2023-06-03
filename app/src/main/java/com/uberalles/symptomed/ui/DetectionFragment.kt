package com.uberalles.symptomed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.uberalles.symptomed.adapter.SymptomAdapter
import com.uberalles.symptomed.data.Symptom
import com.uberalles.symptomed.data.SymptomNames
import com.uberalles.symptomed.databinding.FragmentDetectionBinding

class DetectionFragment : Fragment() {
    private var _binding: FragmentDetectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var symptomAdapter: SymptomAdapter
    private lateinit var symptomArrayList: ArrayList<Symptom>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentDetectionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = binding.symptomsRecyclerView
        recyclerView.layoutManager = layoutManager
        symptomArrayList = arrayListOf<Symptom>()
        symptomAdapter = SymptomAdapter(symptomArrayList)
        recyclerView.adapter = symptomAdapter

        symptomList()
    }

    private fun symptomList() {
        symptomArrayList.addAll(SymptomNames.symptomList.map { Symptom(it) })
        symptomAdapter.notifyDataSetChanged()
    }

}
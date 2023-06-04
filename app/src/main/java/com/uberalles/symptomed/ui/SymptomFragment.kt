package com.uberalles.symptomed.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uberalles.symptomed.adapter.SymptomAdapter
import com.uberalles.symptomed.data.Symptom
import com.uberalles.symptomed.data.SymptomNames
import com.uberalles.symptomed.databinding.FragmentSymptomBinding

class SymptomFragment : Fragment() {
    private var _binding: FragmentSymptomBinding? = null
    private val binding get() = _binding!!
    private lateinit var symptomAdapter: SymptomAdapter
    private lateinit var symptomArrayList: ArrayList<Symptom>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentSymptomBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = binding.symptomsRecyclerView
        recyclerView.layoutManager = layoutManager
        symptomArrayList = arrayListOf()
        symptomAdapter = SymptomAdapter(symptomArrayList)
        recyclerView.adapter = symptomAdapter

        symptomList()
        symptomSelected()
        searchView()
    }

    private fun symptomSelected() {
    }

    private fun searchView() {
        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = "Search Symptoms"
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchSymptom(newText)
                return false
            }
        })
    }

    private fun searchSymptom(query: String) {
        val symptoms = SymptomNames.symptomList.filter { it.contains(query, true) }
        val filteredSymptoms = arrayListOf<Symptom>()
        symptoms.forEach { filteredSymptoms.add(Symptom(it)) }
        symptomAdapter = SymptomAdapter(filteredSymptoms)
        recyclerView.adapter = symptomAdapter
    }

    private fun symptomList() {
        symptomArrayList.addAll(SymptomNames.symptomList.map { Symptom(it) })
        symptomAdapter.notifyDataSetChanged()
    }

}
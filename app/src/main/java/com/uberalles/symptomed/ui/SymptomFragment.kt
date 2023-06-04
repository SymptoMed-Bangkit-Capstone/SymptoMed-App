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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.uberalles.symptomed.adapter.SelectedSymptomAdapter
import com.uberalles.symptomed.adapter.SymptomAdapter
import com.uberalles.symptomed.data.SelectedSymptom
import com.uberalles.symptomed.data.SelectedSymptomNames
import com.uberalles.symptomed.data.Symptom
import com.uberalles.symptomed.data.SymptomNames
import com.uberalles.symptomed.databinding.FragmentSymptomBinding

class SymptomFragment : Fragment() {

    private var _binding: FragmentSymptomBinding? = null
    private val binding get() = _binding!!
    private lateinit var symptomAdapter: SymptomAdapter
    private lateinit var selectedSymptomAdapter: SelectedSymptomAdapter

    private lateinit var symptomArrayList: ArrayList<Symptom>
    private lateinit var symptomSelectedArrayList: ArrayList<SelectedSymptom>

    private lateinit var recyclerViewSymptom: RecyclerView
    private lateinit var recyclerViewSelected: RecyclerView

    private lateinit var selectedSymptomList: SelectedSymptomNames

    private val onItemAdd: (Symptom) -> Unit = { symptom ->
        val selectedSymptom = SelectedSymptom(symptom.name)
        symptomArrayList.remove(symptom)
        symptomSelectedArrayList.add(selectedSymptom)
        symptomAdapter.notifyDataSetChanged()
    }

    private val onItemRemove: (SelectedSymptom) -> Unit = { selectedSymptom ->
        val symptom = Symptom(selectedSymptom.name)
        symptomSelectedArrayList.remove(selectedSymptom)
        symptomArrayList.add(symptom)
        symptomAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentSymptomBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager1 = LinearLayoutManager(context)
        val layoutManager2 = LinearLayoutManager(context)

        recyclerViewSymptom = binding.symptomsRecyclerView
        recyclerViewSymptom.layoutManager = layoutManager1
        symptomArrayList = arrayListOf()
        symptomAdapter = SymptomAdapter(symptomArrayList, onItemAdd)
        recyclerViewSymptom.adapter = symptomAdapter

        selectedSymptomList = SelectedSymptomNames

        recyclerViewSelected = binding.checkedSymptomsRecyclerView
        recyclerViewSelected.layoutManager = layoutManager2
        symptomSelectedArrayList = arrayListOf()
        selectedSymptomAdapter = SelectedSymptomAdapter(symptomSelectedArrayList, onItemRemove)
        recyclerViewSelected.adapter = selectedSymptomAdapter


        symptomList()
        symptomSelected()
        searchView()
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
        symptomAdapter = SymptomAdapter(filteredSymptoms, onItemAdd)
        recyclerViewSymptom.adapter = symptomAdapter
    }

    private fun symptomList() {
        symptomArrayList.addAll(SymptomNames.symptomList.map { Symptom(it) })
        symptomAdapter = SymptomAdapter(symptomArrayList, onItemAdd)
        recyclerViewSymptom.adapter = symptomAdapter
    }

    private fun symptomSelected() {
        symptomSelectedArrayList.addAll(selectedSymptomList.selectedSymptomList.map { SelectedSymptom(it) })
        selectedSymptomAdapter = SelectedSymptomAdapter(symptomSelectedArrayList, onItemRemove)
        recyclerViewSelected.adapter = selectedSymptomAdapter
    }

}

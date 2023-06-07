package com.uberalles.symptomed.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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
import com.uberalles.symptomed.viewmodel.MainViewModel

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

    private lateinit var viewModel: MainViewModel

    private val onItemAdd: (Symptom) -> Unit = { symptom ->
        val selectedSymptom = SelectedSymptom(symptom.name)
        symptomArrayList.remove(symptom)
        symptomSelectedArrayList.add(selectedSymptom)
        //Notify the observer
        viewModel.getSymptomMutableLiveData()?.value = symptomArrayList
        viewModel.getSymptomSelectedMutableLiveData()?.value = symptomSelectedArrayList
    }

    private val onItemDelete: (SelectedSymptom) -> Unit = { selectedSymptom ->
        val symptom = Symptom(selectedSymptom.name)
        symptomSelectedArrayList.remove(selectedSymptom)
        symptomArrayList.add(symptom)
        //Sort by name ascending
        symptomArrayList.sortBy { it.name }
        //Notify the observer
        viewModel.getSymptomMutableLiveData()?.value = symptomArrayList
        viewModel.getSymptomSelectedMutableLiveData()?.value = symptomSelectedArrayList
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSymptomBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager1 = LinearLayoutManager(context)
        val layoutManager2 = StaggeredGridLayoutManager(3, GridLayoutManager.VERTICAL)

        recyclerViewSymptom = binding.symptomsRecyclerView
        recyclerViewSymptom.layoutManager = layoutManager1
        symptomArrayList = arrayListOf()
        symptomAdapter = SymptomAdapter(symptomArrayList, onItemAdd)
        recyclerViewSymptom.adapter = symptomAdapter

        selectedSymptomList = SelectedSymptomNames

        recyclerViewSelected = binding.checkedSymptomsRecyclerView
        recyclerViewSelected.layoutManager = layoutManager2
        symptomSelectedArrayList = arrayListOf()
        selectedSymptomAdapter = SelectedSymptomAdapter(symptomSelectedArrayList, onItemDelete)
        recyclerViewSelected.adapter = selectedSymptomAdapter

        //initial mainviewmodel instance
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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

            override fun onQueryTextChange(query: String): Boolean {
                searchSymptom(query)
                return false
            }
        })
    }

    private fun searchSymptom(query: String) {
        viewModel.getSymptomMutableLiveData()?.observe(viewLifecycleOwner) {
            val symptoms = it.filter { it.name.contains(query, true) }
            val filteredSymptoms = arrayListOf<Symptom>()
            symptoms.forEach { filteredSymptoms.add(Symptom(it.name)) }
            symptomAdapter = SymptomAdapter(filteredSymptoms, onItemAdd)
            recyclerViewSymptom.adapter = symptomAdapter
        }
    }

    private fun symptomList() {
        symptomArrayList.addAll(SymptomNames.symptomList.map { Symptom(it) })

        viewModel.getSymptomMutableLiveData()?.observe(viewLifecycleOwner) {
            recyclerViewSymptom.adapter = symptomAdapter
        }
    }

    private fun symptomSelected() {

        symptomSelectedArrayList.addAll(SelectedSymptomNames.selectedSymptomList.map {
            SelectedSymptom(
                it
            )
        })

        viewModel.getSymptomSelectedMutableLiveData()?.observe(viewLifecycleOwner) {
            recyclerViewSelected.adapter = selectedSymptomAdapter
        }
    }

}

package com.uberalles.symptomed.ui.main

import DataRekomendasi
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uberalles.symptomed.adapter.SelectedSymptomAdapter
import com.uberalles.symptomed.adapter.SymptomAdapter
import com.uberalles.symptomed.data.symptom.SelectedSymptom
import com.uberalles.symptomed.data.symptom.SelectedSymptomNames
import com.uberalles.symptomed.data.symptom.Symptom
import com.uberalles.symptomed.data.symptom.SymptomNames
import com.uberalles.symptomed.databinding.FragmentOfflineSymptomBinding
import com.uberalles.symptomed.ml.Ds
import com.uberalles.symptomed.viewmodel.MainViewModel
import com.uberalles.symptomed.viewmodel.MainViewModelFactory
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class OfflineSymptomFragment : Fragment() {

    private var _binding: FragmentOfflineSymptomBinding? = null
    private val binding get() = _binding!!
    private lateinit var symptomAdapter: SymptomAdapter
    private lateinit var selectedSymptomAdapter: SelectedSymptomAdapter

    private lateinit var symptomArrayList: ArrayList<Symptom>
    private lateinit var selectedSymptomArrayList: ArrayList<SelectedSymptom>

    private lateinit var recyclerViewSymptom: RecyclerView
    private lateinit var recyclerViewSelected: RecyclerView

    private lateinit var viewModel: MainViewModel

    private val onItemAdd: (Symptom) -> Unit = { symptom ->
        val selectedSymptom = SelectedSymptom(symptom.name, true)
        symptomArrayList.remove(symptom)
        selectedSymptomArrayList.add(selectedSymptom)
        //Notify the observer
        viewModel.getSymptomMutableLiveData()?.value = symptomArrayList
        viewModel.getSymptomSelectedMutableLiveData()?.value = selectedSymptomArrayList
        Log.d("SymptomFragment", "onItemAdd: ${symptom.name}, ${symptom.status}")
    }

    private val onItemDelete: (SelectedSymptom) -> Unit = { selectedSymptom ->
        val symptom = Symptom(selectedSymptom.name, false)
        Log.d("SymptomFragment", "onItemAdd: ${selectedSymptom.name}, ${selectedSymptom.status}")
        selectedSymptomArrayList.remove(selectedSymptom)
        symptomArrayList.add(symptom)
        //Sort by name ascending
        symptomArrayList.sortBy { it.name }
        //Notify the observer
        viewModel.getSymptomMutableLiveData()?.value = symptomArrayList
        viewModel.getSymptomSelectedMutableLiveData()?.value = selectedSymptomArrayList
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfflineSymptomBinding.inflate(layoutInflater, container, false)
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

        recyclerViewSelected = binding.checkedSymptomsRecyclerView
        recyclerViewSelected.layoutManager = layoutManager2
        selectedSymptomArrayList = arrayListOf()
        selectedSymptomAdapter = SelectedSymptomAdapter(selectedSymptomArrayList, onItemDelete)
        recyclerViewSelected.adapter = selectedSymptomAdapter

        val factory = MainViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        (activity as MainActivity).hideNavBottom(true)

        predict()
        backToHome()
        symptomList()
        symptomSelected()
        searchView()
    }

    private fun predict() {
        binding.btnPrediksi.setOnClickListener{
            val selectedSymptom = selectedSymptomArrayList.map { it.name }
            SelectedSymptomNames.selectedSymptomList = selectedSymptom
            val getSymptomArray = SelectedSymptomNames.getSelectedSymptomList()
            println(SelectedSymptomNames.selectedSymptomList)
            println(getSymptomArray.contentToString())

            val model = Ds.newInstance(requireContext())

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 132), DataType.FLOAT32)
            inputFeature0.loadArray(getSymptomArray.map { it.toFloat() }.toFloatArray())

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val prediction = outputFeature0.floatArray
            model.close()

            var maxIndex = 0
            var maxValue = Float.MIN_VALUE

            for (i in prediction.indices) {
                if (prediction[i] > maxValue) {
                    maxValue = prediction[i]
                    maxIndex = i
                }
            }

            println(prediction[maxIndex])
            println("Indeks Prediksi: $maxIndex")

            val rekomendasi = DataRekomendasi.rekomendasiList.find { it.Index == maxIndex }

            if (rekomendasi != null) {
                println("Symptom: ${rekomendasi.Symptom}")
                println("Detail: ${rekomendasi.Detail}")
                println("Saran: ${rekomendasi.Saran}")
            } else {
                println("Indeks referensi tidak ditemukan.")
            }

        }
    }

    private fun backToHome() {
        binding.btnBack.setOnClickListener {
            (activity as MainActivity).backToHome()
            (activity as MainActivity).hideNavBottom(false)
        }
    }

    private fun searchView() {
        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = "Cari gejala"
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
        viewModel.getSymptomMutableLiveData()?.observe(viewLifecycleOwner) { it ->
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
        selectedSymptomArrayList.addAll(SelectedSymptomNames.selectedSymptomList.map {
            SelectedSymptom(
                it
            )

        })
        viewModel.getSymptomSelectedMutableLiveData()?.observe(viewLifecycleOwner) {
            recyclerViewSelected.adapter = selectedSymptomAdapter
        }
    }

}

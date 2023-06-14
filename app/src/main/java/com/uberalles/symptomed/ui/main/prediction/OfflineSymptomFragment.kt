package com.uberalles.symptomed.ui.main.prediction

//import com.uberalles.symptomed.data.local.DataRekomendasi
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.uberalles.symptomed.R
import com.uberalles.symptomed.adapter.SelectedSymptomAdapter
import com.uberalles.symptomed.adapter.SymptomAdapter
import com.uberalles.symptomed.data.local.DiseaseResult
import com.uberalles.symptomed.data.local.symptom.SelectedSymptom
import com.uberalles.symptomed.data.local.symptom.SelectedSymptomNames
import com.uberalles.symptomed.data.local.symptom.Symptom
import com.uberalles.symptomed.data.local.symptom.SymptomNames
import com.uberalles.symptomed.databinding.FragmentOfflineSymptomBinding
import com.uberalles.symptomed.ml.Model
import com.uberalles.symptomed.ui.main.MainActivity
import com.uberalles.symptomed.ui.result.OfflineResultFragment
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
    private lateinit var bundle: Bundle

    private val onItemAdd: (Symptom) -> Unit = { symptom ->
        val selectedSymptom = SelectedSymptom(symptom.name)
        symptomArrayList.remove(symptom)
        selectedSymptomArrayList.add(selectedSymptom)
        viewModel.getSymptomMutableLiveData()?.value = symptomArrayList
        viewModel.getSymptomSelectedMutableLiveData()?.value = selectedSymptomArrayList
    }

    private val onItemDelete: (SelectedSymptom) -> Unit = { selectedSymptom ->
        val symptom = Symptom(selectedSymptom.name)
        selectedSymptomArrayList.remove(selectedSymptom)
        symptomArrayList.add(symptom)

        symptomArrayList.sortBy { it.name }

        viewModel.getSymptomMutableLiveData()?.value = symptomArrayList
        viewModel.getSymptomSelectedMutableLiveData()?.value = selectedSymptomArrayList
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOfflineSymptomBinding.inflate(layoutInflater, container, false)
        bundle = Bundle()
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

    @SuppressLint("DiscouragedApi")
    private fun predict() {
        binding.btnPrediksi.setOnClickListener {
            if (selectedSymptomArrayList.isEmpty()) {
                Toast.makeText(context, "Pilih gejala terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else {
                val selectedSymptom = selectedSymptomArrayList.map { it.name }
                SelectedSymptomNames.selectedSymptomList = selectedSymptom

                val symptom = symptomArrayList.map { it.name }
                SymptomNames.symptomList = symptom

                val getSymptomArray = SelectedSymptomNames.getSelectedSymptomList()

                val jsonData = requireContext().resources.openRawResource(
                    requireContext().resources.getIdentifier(
                        "disease",
                        "raw",
                        requireContext().packageName
                    )
                ).bufferedReader().use { it.readText() }.trimIndent()

                val model = Model.newInstance(requireContext())

                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, 132), DataType.FLOAT32)
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

                val gson = Gson()
                val recommendationList =
                    gson.fromJson(jsonData, Array<DiseaseResult>::class.java).toList()

                val recommendation = recommendationList.find { it.Index == maxIndex }

                Log.d("TAG", "predict: ${recommendation?.Symptom}")

                val probabilitas = String.format("%.2f%%", (prediction[maxIndex] * 100))
                val diagnosa = recommendation?.Symptom
                val saran = recommendation?.Saran
                val wiki = recommendation?.Detail

                bundle.putString(DIAGNOSA, diagnosa)
                bundle.putString(PROBABILITAS, probabilitas)
                bundle.putString(SARAN, saran)
                bundle.putString(WIKI, wiki)

                val offlineResultFragment = OfflineResultFragment()
                offlineResultFragment.arguments = bundle

                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_main, offlineResultFragment)
                    .addToBackStack(null)
                fragment.commit()
            }
            refreshList()
        }
    }

    private fun refreshList() {
        symptomArrayList.addAll(selectedSymptomArrayList.map { Symptom(it.name) })
        symptomArrayList.sortBy { it.name }
        selectedSymptomArrayList.clear()
        SelectedSymptomNames.selectedSymptomList = emptyList()
        val symptom = symptomArrayList.map { it.name }
        SymptomNames.symptomList = symptom
    }

    private fun backToHome() {
        binding.btnBack.setOnClickListener {
            (activity as MainActivity).backToHome()
            (activity as MainActivity).hideNavBottom(false)
            refreshList()
        }
    }

    private fun searchView() {
        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = "Cari Gejala"
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

    companion object {
        const val DIAGNOSA = "diagnosa"
        const val PROBABILITAS = "probabilitas"
        const val SARAN = "saran"
        const val WIKI = "wiki"
    }

}

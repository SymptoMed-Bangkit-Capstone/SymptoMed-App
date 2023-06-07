package com.uberalles.symptomed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uberalles.symptomed.data.SelectedSymptom
import com.uberalles.symptomed.data.Symptom

class MainViewModel : ViewModel() {
    private var symptomLiveData: MutableLiveData<ArrayList<Symptom>>? = null
    private var SymptomSelectedLiveData: MutableLiveData<ArrayList<SelectedSymptom>>? = null

    var symptomArrayList: ArrayList<Symptom>? = null
    var symptomSelectedArrayList: ArrayList<SelectedSymptom>? = null

    init {
        symptomLiveData = MutableLiveData()
        SymptomSelectedLiveData = MutableLiveData()

    }

    private fun moveSymptomToSelected() {
        // Check if symptomArrayList and symptomSelectedArrayList are not null
        if (symptomArrayList != null && symptomSelectedArrayList != null) {
            // Create a copy of the symptomArrayList
            val copiedSymptomArrayList = ArrayList(symptomArrayList)

            // Move symptoms from copiedSymptomArrayList to symptomSelectedArrayList
            for (symptom in copiedSymptomArrayList) {
                val selectedSymptom = SelectedSymptom(symptom.name)
                symptomSelectedArrayList!!.add(selectedSymptom)
            }

            // Clear the symptomArrayList
            symptomArrayList!!.clear()

            // Update the symptomLiveData and SymptomSelectedLiveData with the new lists
            symptomLiveData?.value = symptomArrayList
            SymptomSelectedLiveData?.value = symptomSelectedArrayList
        }
    }


    fun getSymptomMutableLiveData(): MutableLiveData<ArrayList<Symptom>>? {
        return symptomLiveData
    }

    fun getSymptomSelectedMutableLiveData(): MutableLiveData<ArrayList<SelectedSymptom>>? {
        return SymptomSelectedLiveData
    }



}
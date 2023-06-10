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

//    private fun moveSymptomToSelected() {
//        if (symptomArrayList != null && symptomSelectedArrayList != null) {
//
//            val copiedSymptomArrayList = symptomArrayList?.let { ArrayList(it) }
//
//            if (copiedSymptomArrayList != null) {
//                for (symptom in copiedSymptomArrayList) {
//                    val selectedSymptom = SelectedSymptom(symptom.name)
//                    symptomSelectedArrayList!!.add(selectedSymptom)
//                }
//            }
//
//            symptomArrayList!!.clear()
//
//            symptomLiveData?.value = symptomArrayList
//            SymptomSelectedLiveData?.value = symptomSelectedArrayList
//        }
//    }


    fun getSymptomMutableLiveData(): MutableLiveData<ArrayList<Symptom>>? {
        return symptomLiveData
    }

    fun getSymptomSelectedMutableLiveData(): MutableLiveData<ArrayList<SelectedSymptom>>? {
        return SymptomSelectedLiveData
    }



}
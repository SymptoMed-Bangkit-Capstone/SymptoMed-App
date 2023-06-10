package com.uberalles.symptomed.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uberalles.symptomed.data.SelectedSymptom
import com.uberalles.symptomed.data.Symptom

class MainViewModel : ViewModel() {
    private var symptomLiveData: MutableLiveData<ArrayList<Symptom>>? = null
    private var symptomSelectedLiveData: MutableLiveData<ArrayList<SelectedSymptom>>? = null

    var symptomArrayList: ArrayList<Symptom>? = null
    var symptomSelectedArrayList: ArrayList<SelectedSymptom>? = null

    init {
        symptomLiveData = MutableLiveData()
        symptomSelectedLiveData = MutableLiveData()

    }


    fun getSymptomMutableLiveData(): MutableLiveData<ArrayList<Symptom>>? {
        return symptomLiveData
    }

    fun getSymptomSelectedMutableLiveData(): MutableLiveData<ArrayList<SelectedSymptom>>? {
        return symptomSelectedLiveData
    }



}
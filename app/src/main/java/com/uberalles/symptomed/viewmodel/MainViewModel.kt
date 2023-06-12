package com.uberalles.symptomed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uberalles.symptomed.data.DiseaseEntity
import com.uberalles.symptomed.data.SymptoMedRepository
import com.uberalles.symptomed.data.symptom.SelectedSymptom
import com.uberalles.symptomed.data.symptom.Symptom

class MainViewModel (private val mSymptoMedRepository: SymptoMedRepository) : ViewModel() {
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

    fun getDisease(query: String): LiveData<DiseaseEntity> =mSymptoMedRepository.getDisease(query)



}
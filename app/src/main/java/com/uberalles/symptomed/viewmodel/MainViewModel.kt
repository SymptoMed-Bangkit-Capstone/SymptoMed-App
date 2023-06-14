package com.uberalles.symptomed.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uberalles.symptomed.data.local.entity.TeamEntity
import com.uberalles.symptomed.data.remote.entity.DiseaseEntity
import com.uberalles.symptomed.utilities.SymptoMedRepository
import com.uberalles.symptomed.data.local.symptom.SelectedSymptom
import com.uberalles.symptomed.data.local.symptom.Symptom
import com.uberalles.symptomed.utilities.TeamData

class MainViewModel (private val mSymptoMedRepository: SymptoMedRepository) : ViewModel() {
    private var symptomLiveData: MutableLiveData<ArrayList<Symptom>>? = null
    private var symptomSelectedLiveData: MutableLiveData<ArrayList<SelectedSymptom>>? = null

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

    fun getDisease(query: String): LiveData<DiseaseEntity> = mSymptoMedRepository.getDisease(query)

    fun getTeamMembers():List<TeamEntity> = TeamData.generateDummyMembers()

}
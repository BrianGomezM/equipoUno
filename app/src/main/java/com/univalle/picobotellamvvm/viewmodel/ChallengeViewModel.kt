package com.univalle.picobotellamvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.univalle.picobotellamvvm.model.Challenge
import com.univalle.picobotellamvvm.repository.ChallengeRepository
import kotlinx.coroutines.launch

class ChallengeViewModel(application: Application): AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val challengeRepository = ChallengeRepository(context)

    private val _listChallenge = MutableLiveData<MutableList<Challenge>>()

    private val _randomChallenge = MutableLiveData<Challenge>()
    val randomChallenge: LiveData<Challenge> = _randomChallenge
    val listChallenge: LiveData<MutableList<Challenge>> get() = _listChallenge

    fun saveChallenge(challenge: Challenge) {
        viewModelScope.launch {
            challengeRepository.saveChallenge(challenge)
        }
    }

    fun getListChallenge(){
        viewModelScope.launch {
            _listChallenge.value = challengeRepository.getListChallenge()
        }
    }

    fun getRandomChallenge(){
        viewModelScope.launch {
            _randomChallenge.value = challengeRepository.getRandomChallenge()
        }
    }
}
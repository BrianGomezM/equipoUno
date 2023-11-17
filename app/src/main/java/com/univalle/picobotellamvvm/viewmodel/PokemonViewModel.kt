package com.univalle.picobotellamvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univalle.picobotellamvvm.model.PokemonList
import com.univalle.picobotellamvvm.repository.ChallengeRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class PokemonViewModel(application: Application): AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val challengeRepository = ChallengeRepository(context)
    private val _pokemonResponse = MutableLiveData<Response<PokemonList>?>()
    val pokemonResponse: LiveData<Response<PokemonList>?> = _pokemonResponse

    fun getPokemones(){
        viewModelScope.launch {
            _pokemonResponse.value = challengeRepository.getPokemones()

        }
    }

}


class SoundViewModel : ViewModel() {
    var soundPlaying: Boolean = true
}

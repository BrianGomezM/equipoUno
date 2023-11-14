package com.univalle.picobotellamvvm.repository
import android.content.Context
import android.util.Log
import com.univalle.picobotellamvvm.model.PokemonList
import com.univalle.picobotellamvvm.webservice.ApiService
import com.univalle.picobotellamvvm.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ChallengeRepository(val context: Context)  {
    private var apiService: ApiService= ApiUtils.getApiService()

    suspend fun getPokemones(): Response<PokemonList>?{
        return withContext(Dispatchers.IO){
            try{
                val response = apiService.getListPokemon()
                Log.d("ChallengeRepository", "Getpokmones response: $response")
                response
            } catch (e: Exception){
                e.printStackTrace()
                null
            }
        }
    }
}
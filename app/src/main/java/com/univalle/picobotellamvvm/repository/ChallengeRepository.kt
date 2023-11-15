package com.univalle.picobotellamvvm.repository
import android.content.Context
import android.util.Log
import com.univalle.picobotellamvvm.model.PokemonList
import com.univalle.picobotellamvvm.webservice.ApiService
import com.univalle.picobotellamvvm.webservice.ApiUtils
import com.univalle.picobotellamvvm.data.ChallengeDB
import com.univalle.picobotellamvvm.data.ChallengeDao
import com.univalle.picobotellamvvm.model.Challenge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ChallengeRepository(val context: Context)  {
    private var apiService: ApiService= ApiUtils.getApiService()
    private var challengeDao: ChallengeDao = ChallengeDB.getDatabase(context).challengeDao()

    suspend fun getPokemones(): Response<PokemonList>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getListPokemon()
                Log.d("ChallengeRepository", "Getpokmones response: $response")
                response
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun saveChallenge(challenge:Challenge){
        withContext(Dispatchers.IO){
            challengeDao.saveInventory(challenge)
        }
    }

    suspend fun getListChallenge():MutableList<Challenge>{
        return withContext(Dispatchers.IO){
            challengeDao.getListChallenge()
        }
    }

    suspend fun getRandomChallenge(): Challenge? {
        return withContext(Dispatchers.IO){
            challengeDao.getRandomChallenge()
        }
    }

    suspend fun deleteChallenge(challenge: Challenge) {
        challengeDao.deleteChallenge(challenge)
    }
}
package com.univalle.picobotellamvvm.repository

import android.content.Context
import com.univalle.picobotellamvvm.data.ChallengeDB
import com.univalle.picobotellamvvm.data.ChallengeDao
import com.univalle.picobotellamvvm.model.Challenge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChallengeRepository (val context: Context) {
    private var challengeDao: ChallengeDao = ChallengeDB.getDatabase(context).challengeDao()

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
}
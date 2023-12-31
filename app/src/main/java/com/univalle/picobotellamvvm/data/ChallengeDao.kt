package com.univalle.picobotellamvvm.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.univalle.picobotellamvvm.model.Challenge

@Dao
interface ChallengeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInventory(challenge: Challenge)

    @Query("SELECT * FROM Challenge ORDER BY id DESC")
    suspend fun getListChallenge(): MutableList<Challenge>

    @Delete
    suspend fun deleteChallenge(challenge: Challenge)

    @Update
    suspend fun updateChallenge(challenge: Challenge)

    @Query("SELECT * FROM Challenge ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomChallenge(): Challenge?
}
package com.example.spin_bottle.repository

import android.content.Context
import com.example.spin_bottle.data.ChallengeDB
import com.example.spin_bottle.data.ChallengeDao


import com.example.spin_bottle.model.Challenge
// import com.example.spin_bottle.model.Product
import com.example.spin_bottle.webservice.ApiService
import com.example.spin_bottle.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChallengeRepository(val context: Context) {
    private var challengeDao:ChallengeDao = ChallengeDB.getDatabase(context).challengeDao()
    private var apiService: ApiService = ApiUtils.getApiService()

    suspend fun saveChallenge(challenge:Challenge){
        withContext(Dispatchers.IO){
            challengeDao.saveChallenge(challenge)
        }
    }

    suspend fun getChallengesList():MutableList<Challenge>{
        return withContext(Dispatchers.IO){
            challengeDao.getChallengesList()
        }
    }

    suspend fun deleteChallenge(challenge: Challenge){
        withContext(Dispatchers.IO){
            challengeDao.deleteChallenge(challenge)
        }
    }

    suspend fun updateChallenge(challenge: Challenge){
        withContext(Dispatchers.IO){
            challengeDao.updateChallenge(challenge)
        }
    }

/*    suspend fun getProducts(): MutableList<Product> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProducts()
                response
            } catch (e: Exception) {

                e.printStackTrace()
                mutableListOf()
            }
        }
    }*/



}
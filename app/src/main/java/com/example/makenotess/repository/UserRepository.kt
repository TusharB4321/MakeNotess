package com.example.makenotess.repository

import android.util.Log
import com.example.makenotess.api.UserAPI
import com.example.makenotess.models.UserRequest
import com.example.makenotess.utils.Constants.TAG
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi:UserAPI) {

    suspend fun registerUser(userRequest: UserRequest){
        val response=userApi.signup(userRequest)
        Log.d(TAG,response.body().toString())
    }

    suspend fun loginUser(userRequest: UserRequest){
        val response=userApi.signin(userRequest)
        Log.d(TAG,response.body().toString())
    }
}
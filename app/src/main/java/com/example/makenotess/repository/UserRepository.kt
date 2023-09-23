package com.example.makenotess.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.makenotess.api.UserAPI
import com.example.makenotess.models.UserRequest
import com.example.makenotess.models.UserResponse
import com.example.makenotess.utils.Constants.TAG
import com.example.makenotess.utils.NetworkResult
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi:UserAPI) {

    private val _userResponseLiveData=
        MutableLiveData<NetworkResult<UserResponse>>()//ye private hota hai
    //Jo bhi changes karenge hum isme mutable me karenge
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>> //publically accessible hota hai
        get()=_userResponseLiveData
    suspend fun registerUser(userRequest: UserRequest){

        _userResponseLiveData.postValue(NetworkResult.Loading())

        val response=userApi.signup(userRequest)

        handleResponse(response)
    }

    suspend fun loginUser(userRequest: UserRequest){
        val response=userApi.signin(userRequest)
        _userResponseLiveData.postValue(NetworkResult.Loading())
        handleResponse(response)
    }
    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {

            val errorBody = JSONObject(
                response.errorBody()!!.charStream().readText()
            ) //Exact konsa error ye line show krti hai
            _userResponseLiveData.postValue(NetworkResult.Error(errorBody.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Error occured "))
        }
    }


}
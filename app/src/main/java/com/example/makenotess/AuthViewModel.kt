package com.example.makenotess

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.makenotess.models.UserRequest
import com.example.makenotess.models.UserResponse
import com.example.makenotess.repository.UserRepository
import com.example.makenotess.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    val userResponseLiveData:LiveData<NetworkResult<UserResponse>>
    get() = repository.userResponseLiveData
    fun registerUser(userRequest: UserRequest){

         viewModelScope.launch {
             repository.registerUser(userRequest)
         }
    }
     fun loginUser(userRequest: UserRequest){
         viewModelScope.launch {
             repository.loginUser(userRequest)
         }
    }
}
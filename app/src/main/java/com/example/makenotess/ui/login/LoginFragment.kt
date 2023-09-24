package com.example.makenotess.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.makenotess.AuthViewModel
import com.example.makenotess.R
import com.example.makenotess.databinding.FragmentLoginBinding
import com.example.makenotess.models.UserRequest
import com.example.makenotess.utils.Helper
import com.example.makenotess.utils.NetworkResult
import com.example.makenotess.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignUp.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.btnLogin.setOnClickListener {

            val validationResult=validateUserInput()
            if (validationResult.first){
                authViewModel.loginUser(getUser())
            }else{
                binding.txtError.text=validationResult.second
            }
        }

        bindObserver()


    }


    private fun getUser():UserRequest{
        val email=binding.txtEmail.text.toString()
        val password=binding.txtPassword.text.toString()

        return UserRequest(email,password,"")
    }
    private fun validateUserInput():Pair<Boolean,String>{
       val user=getUser()
        return Helper.validateCredential(user.username,user.email,user.password,true)
    }

    private fun bindObserver(){

        authViewModel.userResponseLiveData.observe(viewLifecycleOwner){
            binding.spinKit.isVisible=false
            when(it){
                is NetworkResult.Success->{
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is NetworkResult.Error->{
                  binding.txtError.text=it.message
                }
                is NetworkResult.Loading->{
                   binding.spinKit.isVisible=true
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package com.example.makenotess.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.makenotess.AuthViewModel
import com.example.makenotess.R
import com.example.makenotess.databinding.FragmentRegisterBinding
import com.example.makenotess.models.UserRequest
import com.example.makenotess.utils.NetworkResult
import com.example.makenotess.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>() //behind the scene ye ViewModelProvider ko hi
                                                             //call kr raha hai


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.btnSignUp.setOnClickListener {
            authViewModel.registerUser(UserRequest("tbodalwar@gmail.com", "432002", "Tushar"))
        }

        authViewModel.userResponseLiveData.observe(viewLifecycleOwner) {

            binding.spinKit.isVisible=false
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading -> {
                    binding.spinKit.isVisible = true
                }

            }

        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
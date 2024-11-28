package com.example.spin_bottle.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.spin_bottle_app.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.spin_bottle.model.AuthStatus
import com.example.spin_bottle.viewmodel.AuthViewModel
import com.example.spin_bottle_app.R

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private lateinit var binding: LoginFragmentBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)

        enableButtons()
        registerUser()

        loginUser()


        return binding.root
    }

    private fun enableButtons() {
        val emailEt = binding.emailInput
        val passwordEt = binding.passwordInput

        var emailCheck = false
        var passwordCheck = false

        // Función que se encarga de habilitar o deshabilitar los botones
        fun updateButtonState() {
            if (emailCheck && passwordCheck) {
                binding.loginButton.isEnabled = true
                binding.registerText.isEnabled = true
            } else {
                binding.loginButton.isEnabled = false
                binding.registerText.isEnabled = false
            }
        }

        emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isEmpty()){
                    binding.emailInputLayout.error = "Este campo no puede estar vacio"
                    emailCheck = false
                } else {
                    binding.emailInputLayout.error = null
                    emailCheck = true
                }
                updateButtonState()
            }
        })

        passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length < 6) {
                    binding.passwordInputLayout.error = "Minimo 6 digitos"
                    passwordCheck = false
                } else {
                    binding.passwordInputLayout.error = null
                    passwordCheck = true
                }
                updateButtonState()
            }
        })
    }

    private fun registerUser(){
        val registerButton = binding.registerText
        registerButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            authViewModel.registerUser(email, password)
            registerStatus()
        }
    }

    private fun registerStatus(){
        this.authViewModel.registerStatus.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                is AuthStatus.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is AuthStatus.Error -> {
                    Toast.makeText(context, status.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun loginUser(){
        val loginButton = binding.loginButton
        loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            authViewModel.loginUser(email, password)
            loginStatus()
        }
    }

    private fun loginStatus(){
        this.authViewModel.loginStatus.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                is AuthStatus.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is AuthStatus.Error -> {
                    Toast.makeText(context, status.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
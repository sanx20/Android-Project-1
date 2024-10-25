package com.group18.androidproject1.ui.screens.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.group18.androidproject1.R
import com.group18.androidproject1.ui.screens.menu.MenuActivity
import com.group18.androidproject1.ui.viewmodels.auth.AuthResult
import com.group18.androidproject1.ui.viewmodels.auth.AuthViewModel



class RegisterFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var registerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val emailInput: EditText = view.findViewById(R.id.register_email_input)
        val passwordInput: EditText = view.findViewById(R.id.register_password_input)
        registerButton = view.findViewById(R.id.register_button)
        val loginText: TextView = view.findViewById(R.id.login_text)
        progressBar = view.findViewById(R.id.progress_bar)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        registerButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.register(email, password)
            } else {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        authViewModel.registerStatus.observe(viewLifecycleOwner) { result ->
            when (result) {
                is AuthResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    registerButton.isEnabled = false
                }
                is AuthResult.Success -> {
                    progressBar.visibility = View.GONE
                    registerButton.isEnabled = true
                    Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(activity, MenuActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                is AuthResult.Error -> {
                    progressBar.visibility = View.GONE
                    registerButton.isEnabled = true
                    Toast.makeText(context, "Registration failed: ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loginText.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return view
    }
}

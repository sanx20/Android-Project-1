package com.group18.androidproject1.ui.screens.login

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


class LoginFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val emailEditText: EditText = view.findViewById(R.id.register_email_input)
        val passwordEditText: EditText = view.findViewById(R.id.register_password_input)
        loginButton = view.findViewById(R.id.login_button)
        val registerText: TextView = view.findViewById(R.id.register_text)
        progressBar = view.findViewById(R.id.progress_bar)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.login(email, password)
            } else {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        authViewModel.loginStatus.observe(viewLifecycleOwner) { result ->
            when (result) {
                is AuthResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    loginButton.isEnabled = false
                }
                is AuthResult.Success -> {
                    progressBar.visibility = View.GONE
                    loginButton.isEnabled = true
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()

                    val intent = Intent(activity, MenuActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                is AuthResult.Error -> {
                    progressBar.visibility = View.GONE
                    loginButton.isEnabled = true
                    Toast.makeText(context, "Login failed: ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return view
    }
}
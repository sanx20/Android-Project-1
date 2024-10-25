package com.group18.androidproject1.ui.screens.splash

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.group18.androidproject1.R
import com.group18.androidproject1.ui.screens.menu.MenuActivity

class SplashFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        auth = FirebaseAuth.getInstance()
        checkUserStatus()
        return view
    }

    private fun checkUserStatus() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(requireActivity(), MenuActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }
}

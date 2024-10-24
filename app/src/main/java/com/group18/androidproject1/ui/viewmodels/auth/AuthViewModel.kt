package com.group18.androidproject1.ui.viewmodels.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginStatus = MutableLiveData<AuthResult>()
    val loginStatus: LiveData<AuthResult> get() = _loginStatus

    private val _registerStatus = MutableLiveData<AuthResult>()
    val registerStatus: LiveData<AuthResult> get() = _registerStatus

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loginStatus.postValue(AuthResult.Loading)
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                _loginStatus.postValue(AuthResult.Success)
            } catch (e: Exception) {
                _loginStatus.postValue(AuthResult.Error(e.message ?: "Login failed"))
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _registerStatus.postValue(AuthResult.Loading)
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                _registerStatus.postValue(AuthResult.Success)
            } catch (e: Exception) {
                _registerStatus.postValue(AuthResult.Error(e.message ?: "Registration failed"))
            }
        }
    }
}

sealed class AuthResult {
    data object Loading : AuthResult()
    data object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}
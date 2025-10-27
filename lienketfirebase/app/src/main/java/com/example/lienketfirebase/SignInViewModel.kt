package com.example.lienketfirebase

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class SignInViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState = _signInState.asStateFlow()

    fun signInWithGoogle(idToken: String) {
        _signInState.value = SignInState.Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _signInState.value = SignInState.Success("Sign-in successful!")
                } else {
                    _signInState.value = SignInState.Error(task.exception?.message ?: "An unknown error occurred.")
                }
            }
    }

    fun resetState() {
        _signInState.value = SignInState.Idle
    }
}

package com.example.lienketfirebase

sealed class SignInState {
    object Idle : SignInState()
    object Loading : SignInState()
    data class Success(val message: String) : SignInState()
    data class Error(val errorMessage: String) : SignInState()
}

package com.example.nasibakarjoss18_application.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasibakarjoss18_application.Repository.AuthRepository

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _registState = MutableLiveData<Result<String>>()

    val registState : LiveData<Result<String>> = _registState

    fun registrasiUser (
        username : String,
        email : String,
        password : String,
    ) {
        repository.registrasi(username, email, password) {
            success, message ->
            if (success) {
                _registState.value = Result.success("Akun berhasil terdaftar")
            }else {
                _registState.value = Result.failure(Exception(message))
            }
        }
    }


}
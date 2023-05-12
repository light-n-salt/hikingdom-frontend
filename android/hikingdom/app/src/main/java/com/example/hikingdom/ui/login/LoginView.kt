package com.example.hikingdom.ui.login

import com.example.hikingdom.data.remote.auth.Auth

interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(auth: Auth)
    fun onLoginFailure(code: Int, message: String)
}
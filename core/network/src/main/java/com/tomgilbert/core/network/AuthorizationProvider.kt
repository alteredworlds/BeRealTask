package com.tomgilbert.core.network

interface AuthorizationProvider {

    val credentials: String

    fun setUserPassword(user: String, pass: String)
}
package com.tomgilbert.core.network.retrofit

import com.tomgilbert.core.network.AuthorizationProvider
import okhttp3.Credentials
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitAuthorizationProvider @Inject constructor() : AuthorizationProvider {
    override var credentials: String = ""

    override fun setUserPassword(user: String, pass: String) {
        credentials = Credentials.basic(user, pass)
    }
}
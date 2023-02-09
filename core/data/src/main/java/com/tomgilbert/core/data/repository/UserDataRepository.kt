package com.tomgilbert.core.data.repository

import com.tomgilbert.core.model.UserData
import kotlinx.coroutines.flow.StateFlow

interface UserDataRepository {

    val userData: StateFlow<UserData?>

    suspend fun getUserData(username: String, password: String): UserData
}
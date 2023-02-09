package com.tomgilbert.core.data.repository

import com.tomgilbert.core.data.model.asExternalModel
import com.tomgilbert.core.model.UserData
import com.tomgilbert.core.network.BeRealTaskNetworkDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NetworkUserDataRepository @Inject constructor(
    private val network: BeRealTaskNetworkDataSource
) : UserDataRepository {

    private val _userData = MutableStateFlow<UserData?>(null)

    override val userData: StateFlow<UserData?> = _userData

    override suspend fun getUserData(username: String, password: String): UserData {
        return network.getUserData(username, password).asExternalModel().also {
            _userData.value = it
        }
    }
}
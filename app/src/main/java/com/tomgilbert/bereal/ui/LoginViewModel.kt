package com.tomgilbert.bereal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomgilbert.core.data.repository.UserDataRepository
import com.tomgilbert.core.model.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginScreenUiState>(LoginScreenUiState.ReadyForInput)
    val uiState: StateFlow<LoginScreenUiState> = _uiState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginScreenUiState.Loading
            _uiState.value = try {
                delay(1000L)
                LoginScreenUiState.Success(userDataRepository.getUserData(username, password))
            } catch (e: Throwable) {
                LoginScreenUiState.Error(e)
            }
        }
    }
}

sealed interface LoginScreenUiState {
    object ReadyForInput : LoginScreenUiState
    object Loading : LoginScreenUiState
    data class Success(val userData: UserData) : LoginScreenUiState
    data class Error(val exception: Throwable? = null) : LoginScreenUiState
}
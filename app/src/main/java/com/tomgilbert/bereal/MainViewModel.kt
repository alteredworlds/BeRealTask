package com.tomgilbert.bereal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomgilbert.core.data.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    userDataRepository: UserDataRepository
) : ViewModel() {

    val isLoggedIn: StateFlow<Boolean> = userDataRepository.userData
        .mapLatest { it != null }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)
}
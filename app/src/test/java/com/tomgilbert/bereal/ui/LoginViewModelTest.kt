package com.tomgilbert.bereal.ui

import com.tomgilbert.core.data.repository.UserDataRepository
import com.tomgilbert.core.model.FolderItem
import com.tomgilbert.core.model.UserData
import com.tomgilbert.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel

    @Test
    fun initialStateIsReadyForInputAndNoRepositoryCallsMade() = runTest {
        val userDataRepository = object : UserDataRepository {
            override val userData: StateFlow<UserData?>
                get() = TODO("Not yet implemented")

            override suspend fun getUserData(username: String, password: String): UserData {
                TODO("Not yet implemented")
            }

        }
        viewModel = LoginViewModel(userDataRepository)
        assertEquals(LoginScreenUiState.ReadyForInput, viewModel.uiState.value)
    }

    @Test
    fun loginCallsUserDataRepositoryAndCorrectStateIsReturned() = runTest {
        val userData = UserData(
            "fred",
            "flintstone",
            FolderItem("1", null, "home", Instant.now().toString())
        )
        val userDataRepository = object : UserDataRepository {
            override val userData: StateFlow<UserData?>
                get() = TODO("Not yet implemented")

            override suspend fun getUserData(username: String, password: String): UserData {
                delay(100L)
                return userData
            }
        }
        viewModel = LoginViewModel(userDataRepository)
        assertEquals(LoginScreenUiState.ReadyForInput, viewModel.uiState.value)
        viewModel.login("fredf", "12345")
        assertEquals(LoginScreenUiState.Loading, viewModel.uiState.value)
        advanceUntilIdle()
        assertEquals(LoginScreenUiState.Success(userData), viewModel.uiState.value)
    }
}
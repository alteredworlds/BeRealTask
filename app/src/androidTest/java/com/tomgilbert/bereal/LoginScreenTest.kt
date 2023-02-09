package com.tomgilbert.bereal

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.tomgilbert.bereal.ui.LoginScreen
import com.tomgilbert.bereal.ui.LoginScreenUiState
import com.tomgilbert.bereal.ui.theme.BeRealTaskTheme
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val ready = mutableStateOf(LoginScreenUiState.ReadyForInput)

    @Test
    fun loginButtonDisplayedForInitialState() {
        composeTestRule.setContent {
            BeRealTaskTheme {
                LoginScreen(
                    uiState = ready,
                    onLoginClick = {_,_ -> },
                    onLoggedIn = {}
                )
            }
        }
        composeTestRule
            .onNodeWithText(appContext.getString(R.string.login))
            .assertIsDisplayed()
    }
}
package com.tomgilbert.bereal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomgilbert.bereal.R

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    LoginScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle(),
        onLoginClick = viewModel::login,
        onLoggedIn = onLoginSuccess
    )
}

@Composable
fun LoginScreen(
    uiState: State<LoginScreenUiState>,
    onLoginClick: (String, String) -> Unit,
    onLoggedIn: (String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    var showErrorSnackBar by remember { mutableStateOf(false) }
    if (showErrorSnackBar) {
        val message = stringResource(id = R.string.login_failed)
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
            )
            showErrorSnackBar = false
        }
    }
    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Box() {

        }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.please_login))
            UserInputSection(onLoginClick)
            when (val value = uiState.value) {
                LoginScreenUiState.ReadyForInput -> {}
                is LoginScreenUiState.Error -> {
                    LaunchedEffect(value) { showErrorSnackBar = true }
                }
                LoginScreenUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    )
                }
                is LoginScreenUiState.Success -> LaunchedEffect(Unit) {
                    onLoggedIn(value.userData.home.id)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserInputSection(
    onLoginClick: (String, String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(id = R.string.username)) }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password)) }
        )
        Button(onClick = {
            keyboardController?.hide()
            onLoginClick(username, password)
        }) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}
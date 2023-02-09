package com.tomgilbert.bereal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.tomgilbert.bereal.ui.BeRealTaskApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val isLoggedIn: Boolean
        get() = viewModel.isLoggedIn.value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeRealTaskApp(isLoggedIn)
        }
    }
}
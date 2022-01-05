package com.example.formcast

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.formcast.ui.components.NavigationComponent
import com.example.formcast.ui.theme.FormCastTheme
import com.example.formcast.utils.TAG
import com.google.accompanist.permissions.ExperimentalPermissionsApi

import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPermissionsApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FormCastTheme {
                NavigationComponent()
            }
        }
    }
}

package com.example.formcast.ui.components

import android.Manifest
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.formcast.utils.TAG

import com.example.formcast.viewmodel.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
fun NavigationComponent() {
    val navController = rememberNavController()
    val weatherViewModel : WeatherViewModel = hiltViewModel()
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                navController = navController,
                weatherViewModel = weatherViewModel,
                permissionState = permissionState
            )
        }
        composable("weather") {
            WeatherScreen(
                weatherViewModel = weatherViewModel
            )
        }
    }
}
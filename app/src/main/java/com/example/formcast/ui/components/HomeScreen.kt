package com.example.formcast.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.formcast.utils.TAG
import com.example.formcast.viewmodel.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@SuppressLint("MissingPermission")
@ExperimentalPermissionsApi
@Composable
fun HomeScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel,
    permissionState: PermissionState
) {
    var latitude = 0.0
    var longitude = 0.0

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        GrantPermission(
            permissionState = permissionState,
            scaffoldState = scaffoldState,
            context = context,
            coroutineScope = coroutineScope
        )
    }
    if (permissionState.hasPermission) {
        LaunchedEffect(Unit) {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            longitude = location!!.longitude
            latitude = location.latitude
            weatherViewModel.lat.value = latitude
            weatherViewModel.lon.value = longitude
            weatherViewModel.updateWeather()
            navController.navigate("weather")
        }
    }

}


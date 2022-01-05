package com.example.formcast.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.*
import com.example.formcast.utils.TAG
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@ExperimentalPermissionsApi
@Composable
fun GrantPermission(
    permissionState: PermissionState,
    scaffoldState: ScaffoldState,
    context: Context,
    coroutineScope: CoroutineScope
) {
    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
            if (!permissionState.hasPermission) {
                LaunchedEffect(permissionState) {
                    permissionState.launchPermissionRequest()
                }
            }
        },
        permissionNotAvailableContent = {
            ShowRationale(
                locationPermissionState = permissionState,
                scaffoldState = scaffoldState,
                context = context,
                coroutineScope = coroutineScope
            )
        }) {
    }
}


@ExperimentalPermissionsApi
@Composable
fun ShowRationale(
    locationPermissionState: PermissionState,
    scaffoldState: ScaffoldState,
    context: Context,
    coroutineScope: CoroutineScope
) {

    if (!locationPermissionState.hasPermission && !locationPermissionState.shouldShowRationale) {
        LaunchedEffect(locationPermissionState) {
            coroutineScope.launch {
                val result = scaffoldState.snackbarHostState.showSnackbar(
                    message = "Permission denied. Enable location permission in the Settings menu.",
                    actionLabel = "Settings",
                    duration = SnackbarDuration.Indefinite
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        context.startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                        )
                    }
                    SnackbarResult.Dismissed -> {
                    }
                }
            }
        }
    }
}



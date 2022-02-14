package com.example.formcast.ui.components

import android.Manifest
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.formcast.*
import com.example.formcast.denyPermissionInDialog
import com.example.formcast.doNotAskAgainPermissionInDialog
import com.example.formcast.grantPermissionInDialog
import com.example.formcast.grantPermissionProgrammatically
import com.example.formcast.ui.theme.FormCastTheme
import com.example.formcast.utils.TAG
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalPermissionsApi
@HiltAndroidTest
//@UninstallModules(RetrofitModule::class, RepositoryModule::class)
class HomeScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val context = LocalContext.current
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            val permissionState =
                rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
            FormCastTheme {
                GrantPermission(
                    permissionState = permissionState,
                    scaffoldState = scaffoldState,
                    context = context,
                    coroutineScope = coroutineScope
                )
            }
        }
    }

    @Test
    fun grantPermissionTest() {
        grantPermissionInDialog()

    }

    @Test
    fun denyPermissionTest() {
        denyPermissionInDialog()
        grantPermissionInDialog()
    }

    @Test
    fun doNotAskAgainPermissionTest() {
        denyPermissionInDialog()
        doNotAskAgainPermissionInDialog()
    }

    @Test
    fun grantPermissionInBackgroundTest() {
        denyPermissionInDialog()
        doNotAskAgainPermissionInDialog()
        grantPermissionProgrammatically("android.permission.ACCESS_FINE_LOCATION")
        simulateAppComingFromTheBackground(composeRule)
        composeRule.activityRule.scenario.onActivity {
            it.setContent {
                val context = LocalContext.current
                val scaffoldState = rememberScaffoldState()
                val coroutineScope = rememberCoroutineScope()
                val permissionState =
                    rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
                FormCastTheme {
                    GrantPermission(
                        permissionState = permissionState,
                        scaffoldState = scaffoldState,
                        context = context,
                        coroutineScope = coroutineScope
                    )
                }
            }

        }
    }
}





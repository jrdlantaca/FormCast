package com.example.formcast.ui.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.formcast.R
import com.example.formcast.ui.theme.*
import com.example.formcast.utils.TAG
import com.example.formcast.viewmodel.WeatherViewModel
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@SuppressLint("MissingPermission")
@ExperimentalPermissionsApi
@Composable
fun WeatherScreen(
    weatherViewModel: WeatherViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    val loading = weatherViewModel.loading.value
    val latitude = weatherViewModel.lat
    val longitude = weatherViewModel.lon
    val weather by weatherViewModel.weather.observeAsState()
    val data = weather?.data

    val clearSkyGradientBrush = Brush.verticalGradient(
        colors = listOf(
            Blue700, Blue200, Color.White
        )
    )
    val darkSkyGradientBrush = Brush.verticalGradient(
        colors = listOf(
            DarkBlue900, SkyPurple900, SkyPurple700, SkyPurple500
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = if (isSystemInDarkTheme()) {
                    darkSkyGradientBrush
                } else {
                    clearSkyGradientBrush
                }
            ).statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()

        ) {
            CurrentWeatherDetails(
                data = data,
                loading = loading,
                context = context,
                latitude = latitude.value,
                longitude = longitude.value
            )

            HourlyWeatherDetails(data = data, loading = loading, context = context)

            DailyWeatherDetails(data = data, loading = loading, context = context)
        }
    }

}

@Preview
@Composable
fun WeatherPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Blue700, Blue200
                        )
                    )
                )
        ) {

        }
        /*Image(
            painterResource(id = R.drawable.clear),
            contentDescription = "clear",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.FillBounds
        )*/
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            Text(
                text = "CurrentWeatherDetails",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "HourlyWeatherDetails",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "DailyWeatherDetails",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )
        }
    }
}


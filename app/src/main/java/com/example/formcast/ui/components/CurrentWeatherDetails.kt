package com.example.formcast.ui.components

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.formcast.R
import com.example.formcast.utils.Resource
import com.example.formcast.utils.TAG
import com.example.formcast.utils.convertUnix
import com.example.formcast.utils.getImageFromUrl
import com.example.formcast.viewmodel.WeatherViewModel
import com.example.formcast.vo.Weather
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import java.util.*
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherDetails(
    data: List<Weather>?,
    loading: Boolean,
    context: Context,
    latitude: Double,
    longitude: Double,

    ) {

    val geocoder = Geocoder(context, Locale.getDefault())
    val address = geocoder.getFromLocation(latitude, longitude, 1)
    val cityName = address[0].locality
    val stateName = address[0].adminArea
    val countryName = address[0].countryName



    if (data != null) {
        if (data.isNotEmpty()) {
            val localTime = convertUnix(data[0].current!!.dt!!)
            Card(
                shape = RoundedCornerShape(2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
                    .placeholder(
                        visible = loading,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = Color.White.copy(alpha = 0.0f),
                        shape = RoundedCornerShape(4.dp)
                    ),
                backgroundColor = Color.White.copy(alpha = 0.0f),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        Image(
                            painter = rememberImagePainter(
                                data = getImageFromUrl(
                                    data[0].current?.weatherDesc?.get(0)?.icon!!
                                )
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = "${data[0].current?.temp!!.roundToInt()}\u2103",
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = if(isSystemInDarkTheme()){
                                Color.White
                            }else{
                                Color.Black
                            },
                        )
                    }
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = "${data[0].current?.weatherDesc?.get(0)?.description}",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = if(isSystemInDarkTheme()){
                                Color.White
                            }else{
                                Color.Black
                            },

                        )
                    }
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = "$cityName, $stateName",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = if(isSystemInDarkTheme()){
                                Color.White
                            }else{
                                Color.Black
                            },
                        )
                    }
                    Row(horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = localTime,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = if(isSystemInDarkTheme()){
                                Color.White
                            }else{
                                Color.Black
                            },
                        )
                    }
                }
            }
        }
    }
}
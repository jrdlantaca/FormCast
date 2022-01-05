package com.example.formcast.ui.components

import android.content.Context
import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.formcast.R
import com.example.formcast.utils.TAG
import com.example.formcast.utils.convertUnixToHours
import com.example.formcast.utils.getImageFromUrl
import com.example.formcast.vo.HourlyWeather
import com.example.formcast.vo.Weather
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlin.math.roundToInt

@Composable
fun HourlyWeatherDetails(
    data: List<Weather>?,
    loading: Boolean,
    context: Context,
) {

    if (data != null) {
        if (data.isNotEmpty()) {
            Card(
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
                shape = RoundedCornerShape(4.dp),
                backgroundColor = Color.White.copy(alpha = 0.5f),
                elevation = 0.dp
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    itemsIndexed(data[0].hourly!!.subList(1, 6)) { _, item ->
                        HourlyWeatherItem(item = item)
                    }
                }

            }
        }
    }
}


@Composable
fun HourlyWeatherItem(item: HourlyWeather?) {
    val hour = convertUnixToHours(item!!.dt!!)
    val url = getImageFromUrl(item.hourlyDesc!![0]!!.icon!!)

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                text = hour, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                text = "${item.temp!!.roundToInt()}\u2103", textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.ExtraBold
            )
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Image(
                painter = rememberImagePainter(data = url),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Column(modifier = Modifier.padding(top = 4.dp, end = 4.dp)) {
                Image(
                    painterResource(id = R.drawable.raindrop), contentDescription = null,
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Column() {
                Text(
                    text = String.format("%.0f%%", (item.pop!! * 100)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 14.sp, color = Color.Black.copy(alpha = 0.7f)
                )
            }
        }
    }
}
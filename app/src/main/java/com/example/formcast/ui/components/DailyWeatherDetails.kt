package com.example.formcast.ui.components

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.formcast.R
import com.example.formcast.utils.convertUnixToDay
import com.example.formcast.utils.getImageFromUrl
import com.example.formcast.vo.DailyWeather
import com.example.formcast.vo.Weather
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlin.math.roundToInt

@Composable
fun DailyWeatherDetails(
    data: List<Weather>?,
    loading: Boolean,
    context: Context
) {
    if (data != null) {
        if (data.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
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
                LazyColumn(
                    modifier = Modifier.padding(4.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    itemsIndexed(data[0].daily!!.take(7)) { index, item ->
                        DailyWeatherItem(index, item)
                    }
                }
            }
        }
    }
}


@Composable
fun DailyWeatherItem(index: Int, item: DailyWeather?) {
    val day = convertUnixToDay(index, item!!.dt!!)
    val image = getImageFromUrl(item.dailyDesc!![0]!!.icon!!)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val (day_of_week, temp, icon, rain, pop) = createRefs()
        Text(text = day, modifier = Modifier.constrainAs(day_of_week) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start, margin = 8.dp)
        }, fontWeight = FontWeight.Bold)

        Image(
            painter = painterResource(id = R.drawable.raindrop),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(rain) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(pop.start, margin = 4.dp)
                }
                .size(12.dp)
        )

        Text(
            text = String.format("%.0f%%", (item.pop!! * 100)),
            modifier = Modifier.constrainAs(pop) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(temp.start, margin = 8.dp)
            }, fontSize = 14.sp, color = Color.Black.copy(alpha = 0.7f)
        )

        Image(painter = rememberImagePainter(data = image), contentDescription = null,
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(rain.start, margin = 4.dp)
                }
                .size(28.dp)
        )

        Text(
            text = "${item.temp!!.day!!.roundToInt()}\u2103/${item.temp.night!!.roundToInt()}\u2103",
            modifier = Modifier.constrainAs(temp) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end, margin = 8.dp)
            }, fontWeight = FontWeight.Bold
        )


    }
}


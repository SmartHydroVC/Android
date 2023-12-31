package com.example.smarthydro.ui.theme.screen.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smarthydro.R
import com.example.smarthydro.models.SensorModel
import com.example.smarthydro.ui.theme.Beige1
import com.example.smarthydro.ui.theme.Beige2
import com.example.smarthydro.ui.theme.Beige3
import com.example.smarthydro.ui.theme.Blue1
import com.example.smarthydro.ui.theme.Blue2
import com.example.smarthydro.ui.theme.Blue3
import com.example.smarthydro.ui.theme.BlueViolet1
import com.example.smarthydro.ui.theme.BlueViolet2
import com.example.smarthydro.ui.theme.BlueViolet3
import com.example.smarthydro.ui.theme.DeepBlue
import com.example.smarthydro.ui.theme.LightGreen1
import com.example.smarthydro.ui.theme.LightGreen2
import com.example.smarthydro.ui.theme.LightGreen3
import com.example.smarthydro.ui.theme.OrangeYellow1
import com.example.smarthydro.ui.theme.OrangeYellow2
import com.example.smarthydro.ui.theme.OrangeYellow3
import com.example.smarthydro.ui.theme.Red1
import com.example.smarthydro.ui.theme.Red2
import com.example.smarthydro.ui.theme.Red3
import com.example.smarthydro.ui.theme.GreenGood
import com.example.smarthydro.ui.theme.RedBad
import com.example.smarthydro.ui.theme.screen.ReadingType
import com.example.smarthydro.viewmodels.ReadingViewModel
import com.example.smarthydro.viewmodels.SensorViewModel

private const val GET_SENSOR_DATA_DELAY_MS: Long = 15 * 1000

// https://youtu.be/g5-wzZUnIbQ
@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: SensorViewModel,navController: NavHostController, readingViewModel: ReadingViewModel) {
    val sensorData by viewModel.sensorData.observeAsState(SensorModel())
    LaunchedEffect(Unit) {
        viewModel.fetchSensorPeriodically(GET_SENSOR_DATA_DELAY_MS)
    }
    Box(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {
        Column {
            CardSection(
                features  = getFeatures(sensorData),
                navController,readingViewModel, sensorData
            )
        }
    }
}
private fun getFeatures(sensorData: SensorModel): List<Feature> {
    return listOf(
            Feature(
                title = "Water",
                R.drawable.ic_water,
                BlueViolet1,
                BlueViolet2,
                BlueViolet3,
                sensorData.flowRate,

                ),
            Feature(
                title = "Clean Water",
                R.drawable.ic_cleanwater,
                Blue1,
                Blue2,
                Blue3,
                sensorData.pH
            ),
            Feature(
                title = "Temperature",
                R.drawable.ic_temp,
                Red1,
                Red2,
                Red3,
                sensorData.temperature
            ),
            Feature(
                title = "Humidity",
                R.drawable.ic_fire,
                Beige1,
                Beige2,
                Beige3,
                sensorData.humidity
            ),
            Feature(
                title = "Compost",
                R.drawable.ic_plant,
                LightGreen1,
                LightGreen2,
                LightGreen3,
                sensorData.eC
            ),
            Feature(
                title = "Sun Light",
                R.drawable.ic_sun,
                OrangeYellow1,
                OrangeYellow2,
                OrangeYellow3,
                sensorData.light
            ),
        )
}
@ExperimentalFoundationApi
@Composable
fun CardSection(features: List<Feature>, navController: NavHostController, readingViewModel: ReadingViewModel, sensorData: SensorModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(features.size) {
                SensorCard(feature = features[it],navController, readingViewModel = readingViewModel, sensorData)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  SensorCard(
    feature: Feature, navController: NavHostController, readingViewModel: ReadingViewModel, sensorData: SensorModel
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(feature.darkColor.value),
        modifier = Modifier
            .height(210.dp)
            .padding(10.dp),
        shadowElevation = 10.dp,
        onClick =
        {
            readingViewModel.setReadingType(ReadingType(feature.title,sensorData,""))
            navController.navigate("viewData")
        }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            drawPath(
                path = createMediumColoredPath(width, height),
                color = feature.mediumColor
            )
            drawPath(
                path = createLightColoredPath(width, height),
                color = feature.lightColor
            )
        }
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = feature.title,
                    fontSize =  24.sp,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        painter = painterResource(id = feature.iconId),
                        contentDescription = null,
                        modifier = Modifier.size(width = 100.dp, height = 140.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = checkReadingLevel(title = feature.title, readingValue = feature.sensorReading),
                modifier = Modifier.size(width = 100.dp, height = 100.dp)
            ) {
                Text(
                    text = if (feature.sensorReading.isEmpty()) "No Data" else feature.sensorReading,
                    modifier = Modifier.wrapContentSize(),
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}

private fun createMediumColoredPath(width: Float, height: Float): Path {
    // Medium colored path
    val mediumColoredPoint1 = Offset(0f, height * 0.3f)
    val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
    val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
    val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
    val mediumColoredPoint5 = Offset(width * 1.4f, -height.toFloat())

    val mediumColoredPath = Path().apply {
        moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
        standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
        standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
        standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
        standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
        lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
        lineTo(-100f, height.toFloat() + 100f)
        close()
    }
    return mediumColoredPath
}
private fun createLightColoredPath(width: Float, height: Float): Path {
    // Light colored path
    val lightPoint1 = Offset(0f, height * 0.35f)
    val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
    val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
    val lightPoint4 = Offset(width * 0.65f, height)
    val lightPoint5 = Offset(width * 1.4f, -height / 3f)

    val lightColoredPath = Path().apply {
        moveTo(lightPoint1.x, lightPoint1.y)
        standardQuadFromTo(lightPoint1, lightPoint2)
        standardQuadFromTo(lightPoint2, lightPoint3)
        standardQuadFromTo(lightPoint3, lightPoint4)
        standardQuadFromTo(lightPoint4, lightPoint5)
        lineTo(width + 100f, height + 100f)
        lineTo(-100f, height + 100f)
        close()
    }

    return lightColoredPath
}

private fun checkReadingLevel(title: String, readingValue: String): Color {
    val reading = readingValue.toFloatOrNull() ?: 0.0f

    val acceptableRanges = mapOf(
        "Temperature" to Range(18f, 25f),
        "Water" to Range(10f, 100f),
        "Clean Water" to Range(7f, 8f),
        "Humidity" to Range(65f, 75f),
        "Compost" to Range(2f, 4f),
        "Sun Light" to Range(80f, 1000f)
    )

    val range = acceptableRanges[title]
    return if (range != null && reading in range.min..range.max) GreenGood else RedBad
}

data class Range(val min: Float, val max: Float)
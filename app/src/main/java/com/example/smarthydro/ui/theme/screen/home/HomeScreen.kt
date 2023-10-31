package com.example.smarthydro.ui.theme.screen.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smarthydro.ui.theme.Beige1
import com.example.smarthydro.ui.theme.Beige2
import com.example.smarthydro.ui.theme.Beige3
import com.example.smarthydro.ui.theme.BlueViolet1
import com.example.smarthydro.ui.theme.BlueViolet2
import com.example.smarthydro.ui.theme.BlueViolet3
import com.example.smarthydro.ui.theme.ButtonBlue
import com.example.smarthydro.ui.theme.DeepBlue
import com.example.smarthydro.ui.theme.LightGreen1
import com.example.smarthydro.ui.theme.LightGreen2
import com.example.smarthydro.ui.theme.LightGreen3
import com.example.smarthydro.ui.theme.OrangeYellow1
import com.example.smarthydro.ui.theme.OrangeYellow2
import com.example.smarthydro.ui.theme.OrangeYellow3
import com.example.smarthydro.ui.theme.Purple200
import com.example.smarthydro.ui.theme.Purple500
import com.example.smarthydro.ui.theme.Purple700
import com.example.smarthydro.ui.theme.Red1
import com.example.smarthydro.ui.theme.Red2
import com.example.smarthydro.ui.theme.Red3
import com.example.smarthydro.ui.theme.TextWhite
import com.example.smarthydro.R
import com.example.smarthydro.models.SensorModel
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
            GreetingSection()
            FeatureSection(
                features = listOf(
                    Feature(
                        title = "Water",
                        R.drawable.ic_waterlv,
                        BlueViolet1,
                        BlueViolet2,
                        BlueViolet3
                    ),
                    Feature(
                        title = "PH",
                        R.drawable.ic_phlv,
                        LightGreen1,
                        LightGreen2,
                        LightGreen3
                    ),
                    Feature(
                        title = "Temperature",
                        R.drawable.ic_temp,
                        OrangeYellow1,
                        OrangeYellow2,
                        OrangeYellow3
                    ),
                    Feature(
                        title = "Humidity",
                        R.drawable.ic_humidity,
                        Beige1,
                        Beige2,
                        Beige3
                    ),
                    Feature(
                        title = "EC",
                        R.drawable.ic_ec,
                        Purple200,
                        Purple500,
                        Purple700
                    ),
                    Feature(
                        title = "Light",
                        R.drawable.ic_light,
                        Red1,
                        Red2,
                        Red3
                    ),
                ),
                navController,readingViewModel, sensorData
            )
        }
    }
}

@Composable
fun GreetingSection(
    name: String = "Farmer"
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hello, $name",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "We wish you have a good day!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun FeatureSection(features: List<Feature>, navController: NavHostController, readingViewModel: ReadingViewModel, sensorData: SensorModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Features",
            color = Color.White,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(15.dp,15.dp,15.dp,20.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(features.size) {
                FeatureItem(feature = features[it],navController, readingViewModel = readingViewModel, sensorData)
            }
        }
    }
}

@Composable
fun FeatureItem(
    feature: Feature, navController: NavHostController, readingViewModel: ReadingViewModel, sensorData: SensorModel
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkColor)
    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

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

        // Light colored path
        val lightPoint1 = Offset(0f, height * 0.35f)
        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

        val lightColoredPath = Path().apply {
            moveTo(lightPoint1.x, lightPoint1.y)
            standardQuadFromTo(lightPoint1, lightPoint2)
            standardQuadFromTo(lightPoint2, lightPoint3)
            standardQuadFromTo(lightPoint3, lightPoint4)
            standardQuadFromTo(lightPoint4, lightPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawPath(
                path = mediumColoredPath,
                color = feature.mediumColor
            )
            drawPath(
                path = lightColoredPath,
                color = feature.lightColor
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.headlineSmall,
                lineHeight = 26.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Icon(
                painter = painterResource(id = feature.iconId),
                contentDescription = feature.title,
                tint = Color.White,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Text(
                text = "View",
                color = TextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        // Handle the click
                        readingViewModel.setReadingType(ReadingType(feature.title,sensorData,""))
                        navController.navigate("viewData")
                    }
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .padding(vertical = 6.dp, horizontal = 15.dp)
            )
        }
    }
}

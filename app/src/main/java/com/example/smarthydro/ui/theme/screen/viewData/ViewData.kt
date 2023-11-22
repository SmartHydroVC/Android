package com.example.smarthydro.ui.theme.screen.viewData


import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smarthydro.R
import com.example.smarthydro.models.Reading
import com.example.smarthydro.models.SensorModel
import com.example.smarthydro.models.getReadingUnit
import com.example.smarthydro.ui.theme.DeepBlue
import com.example.smarthydro.utils.ToggleButtonUtils
import com.example.smarthydro.viewmodels.ComponentViewModel
import com.example.smarthydro.viewmodels.ReadingViewModel
import com.example.smarthydro.viewmodels.SensorViewModel
import kotlinx.coroutines.launch

class UiState(
    val arcValue: Float = 0f
)
val openAlertDialog = mutableStateOf(false)
var powerState : Boolean = true
var reading: Reading = Reading("",SensorModel(), "","");

suspend fun startAnimation(animation: Animatable<Float, AnimationVector1D>) {
    animation.animateTo(1.00f, keyframes {

    })
}

fun Animatable<Float, AnimationVector1D>.toUiState() = UiState(
    arcValue = value
)

@Composable
fun ViewDataScreen(navHostController: NavHostController, component: ComponentViewModel, readingViewModel: ReadingViewModel, sensorViewModel: SensorViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val animation = remember { Animatable(0f) }
    val sensorData by sensorViewModel.sensorData.observeAsState(SensorModel())

    reading = readingViewModel.getReadingType()!!

    ViewDataScreen(animation.toUiState(),navHostController, reading.heading, component, sensorData ) {
        coroutineScope.launch {
            startAnimation(animation)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ViewDataScreen(state: UiState,navHostController: NavHostController,readingString:String, component: ComponentViewModel, sensorModel: SensorModel, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(DeepBlue)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .padding(top = 25.dp, bottom = 5.dp)
                .fillMaxWidth()
                .background(Color.Transparent),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(onClick = {
                navHostController.navigate("home")
            }) {
                Icon(

                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        reading = getReadingUnit(readingString = readingString, sensorModel)
        Header(reading.heading)
       
       // BarChart(values = barChartInputsPercent, xLabels = xAxis)
        Chart(
            data = mapOf(

                Pair(0.5f,"M"),
                Pair(0.6f,"T"),
                Pair(0.2f,"W"),
                Pair(0.7f,"T"),
                Pair(0.8f,"F"),
                Pair(0.3f,"S"),
                Pair(0.1f,"S"),
                ), max_value = 80
        )

        if (reading.heading == stringResource(R.string.ph) || reading.heading == stringResource(R.string.ec))
            DataControlSection(state = state, onClick = onClick, reading.unit, component, true)
        else
            DataControlSection(state = state, onClick = onClick, reading.unit, component, false)
    }
}

@Composable
fun Header(heading:String) {
    Text(
        text = heading,
        fontSize = 36.sp,
        modifier = Modifier.padding(top = 8.dp, bottom = 26.dp),
        style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
private fun DataControlSection(
    state: UiState,
    onClick: () -> Unit,
    unit: String,
    component: ComponentViewModel,
    isPhOrEc: Boolean
) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        SpeedIndicator().CircularSpeedIndicator(state.arcValue, 240f)
        ControlButtonsRow(onClick, component, isPhOrEc)
        DataValue(reading.readingValue, unit)
    }
}

@Composable
private fun ControlButtonsRow(
    onClick: () -> Unit,
    component: ComponentViewModel,
    isPhOrEc: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        val toggleButtons = ToggleButtonUtils()
        if (isPhOrEc) {
            toggleButtons.ToggleButton(onClick, component, R.drawable.ic_arrow_up)
            Spacer(modifier = Modifier.width(24.dp))
            toggleButtons.IconButtonOnOff(onClick, component)
            Spacer(modifier = Modifier.width(24.dp))
            toggleButtons.ToggleButton(onClick, component, R.drawable.ic_arrow_down)
        } else {
            toggleButtons.IconButtonOnOff(onClick, component)
        }
    }
}

@Composable
fun DataValue(value: String, unit: String) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 45.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(unit, style = MaterialTheme.typography.headlineMedium)
    }
}
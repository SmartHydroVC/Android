package com.example.smarthydro.ui.theme.screen.viewData


import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Typeface
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.smarthydro.utils.AlertDialogUtils
import com.example.smarthydro.R
import com.example.smarthydro.models.Reading
import com.example.smarthydro.models.SensorModel
import com.example.smarthydro.models.getReadingUnit
import com.example.smarthydro.ui.theme.DeepBlue
import com.example.smarthydro.ui.theme.GreenGradient
import com.example.smarthydro.ui.theme.LightGreen1
import com.example.smarthydro.ui.theme.PrimaryColor
import com.example.smarthydro.utils.ControlComponentUtils
import com.example.smarthydro.utils.ToggleButtonUtils
import com.example.smarthydro.viewmodels.ComponentViewModel
import com.example.smarthydro.viewmodels.ReadingViewModel
import com.example.smarthydro.viewmodels.SensorViewModel
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.overlayingComponent
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.chart.segment.SegmentProperties
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.extension.copyColor
import com.patrykandpatrick.vico.core.marker.Marker
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.max

class UiState(
    val arcValue: Float = 0f
)
var powerState : Boolean = true
private var readingValue : String = ""
val openAlertDialog = mutableStateOf(false)
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
    val maxSpeed = remember { mutableStateOf(0f) }
    val sensorData by sensorViewModel.sensorData.observeAsState(SensorModel())
    maxSpeed.value = max(maxSpeed.value, animation.value * 100f)

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
        DataValue(readingValue, unit)
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
        if (isPhOrEc) {
            ToggleButtonUtils().ToggleButton(onClick, component, R.drawable.ic_arrow_up)
            Spacer(modifier = Modifier.width(24.dp))
            IconButtonOnOff(onClick, component)
            Spacer(modifier = Modifier.width(24.dp))
            ToggleButtonUtils().ToggleButton(onClick, component, R.drawable.ic_arrow_down)
        } else {
            IconButtonOnOff(onClick, component)
        }
    }
}

fun changeIconColorBasedOnPowerState(powerState: Boolean, onClick: () -> Unit): Color {
    return if (powerState) {
        onClick()
        Color.Red
    } else {
        Color.Green
    }
}


fun getAlertDialogValue(heading: String): Boolean {
    if (heading == "Clean Water"|| heading == "Compost")
        return true

    return false
}


@Composable
fun IconButtonOnOff(onClick: () -> Unit, componentViewModel: ComponentViewModel) {
    val iconColor by remember { mutableStateOf(Color.Red) }

    IconButton(
        modifier = Modifier
            .size(72.dp)
            .padding(top = 20.dp),
        onClick = {
            powerState = !powerState
            ControlComponentUtils(componentViewModel).controlComponent(reading.heading)
            changeIconColorBasedOnPowerState(powerState, onClick)
        })
    {
        Icon(
            painter = painterResource(id = R.drawable.ic_power),
            contentDescription = "Option to turn on or off the component",
            tint = iconColor,
            modifier = Modifier.size(size = 100.dp)
        )
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
        //Measurement Unit change here
        Text(unit, style = MaterialTheme.typography.headlineMedium)
    }
}


@Preview("Line Chart Dark", widthDp = 300)
@Composable
fun LineChartDark() {
    Surface(
        shape = RoundedCornerShape(8.dp),
    ) {
        Chart(
            data = mapOf(

                Pair(1f, "M"),
                Pair(0.6f, "B"),
                Pair(0.2f, "C"),
                Pair(0.7f, "D"),
                Pair(0.8f, "E"),

                ), max_value = 80
        )
    }
}
package com.example.smarthydro.ui.theme.screen.viewData

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
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
import com.example.smarthydro.ui.theme.DarkerButtonBlue
import com.example.smarthydro.ui.theme.DeepBlue
import com.example.smarthydro.utils.ToggleButtonUtils
import com.example.smarthydro.viewmodels.ComponentViewModel
import com.example.smarthydro.viewmodels.ReadingViewModel
import com.example.smarthydro.viewmodels.SensorViewModel

val openAlertDialog = mutableStateOf(false)
var reading: Reading = Reading("",SensorModel(), "","")
val powerState = mutableStateOf(true)
@Composable
fun ViewDataScreen(navHostController: NavHostController, component: ComponentViewModel, readingViewModel: ReadingViewModel, sensorViewModel: SensorViewModel) {
    val sensorData by sensorViewModel.sensorData.observeAsState(SensorModel())
    reading = readingViewModel.getReadingType()!!
    ViewDataScreen(navHostController, reading.heading, component, sensorData )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ViewDataScreen(navHostController: NavHostController,readingString:String, component: ComponentViewModel, sensorModel: SensorModel) {
    reading = getReadingUnit(readingString = readingString, sensorModel)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(DarkerButtonBlue)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopStart,
        ) {
            IconButton(
                onClick = {
                    navHostController.navigate("home")
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        Header(reading.heading)
        DataValue(value = reading.readingValue, unit = reading.unit)
        Chart(
            data = mapOf(
                Pair(0.5f, "M"),
                Pair(0.6f, "T"),
                Pair(0.2f, "W"),
                Pair(0.7f, "T"),
                Pair(0.8f, "F"),
                Pair(0.3f, "S"),
                Pair(0.1f, "S"),
                ), max_value = 80
        )
        Spacer(modifier = Modifier.height(100.dp))
        DataCard(component = component)
    }
}

@Composable
fun DataCard(component: ComponentViewModel) {

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.LightGray,
        modifier = Modifier
            .height(210.dp)
            .padding(10.dp),
        shadowElevation = 10.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OnlineStatus(
                    text = if (powerState.value) "Online" else "Offline",
                    color = if (powerState.value) Color.Green else Color.Red
                )
                Spacer(modifier = Modifier.width(150.dp))
                InfomationText(text = "")
            }

            Spacer(modifier = Modifier.height(4.dp))

            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            if (reading.heading == stringResource(R.string.ph) || reading.heading == stringResource(R.string.ec))
                ControlButtonsRow(component = component, isPhOrEc = true, powerState = powerState)
            else
                ControlButtonsRow(component = component, isPhOrEc = false, powerState = powerState)
        }
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
fun InfomationText(text:String){
    Text(
        text = text,
        fontSize = 20.sp,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}
@Composable
fun OnlineStatus(text: String, color: Color)
{
    Box(
        modifier = Modifier
            .size(12.dp)
            .background(color, CircleShape)
    )

    Spacer(modifier = Modifier.width(8.dp))

    Text(
        text = text,
        fontSize = 20.sp,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ControlButtonsRow(
    component: ComponentViewModel,
    isPhOrEc: Boolean,
    powerState: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        val toggleButtons = ToggleButtonUtils()
        if (isPhOrEc) {
            toggleButtons.ToggleButton(component, R.drawable.ic_arrow_up)
            Spacer(modifier = Modifier.width(24.dp))
            toggleButtons.IconButtonOnOff(component,powerState)
            Spacer(modifier = Modifier.width(24.dp))
            toggleButtons.ToggleButton(component, R.drawable.ic_arrow_down)
        } else {
            toggleButtons.IconButtonOnOff(component,powerState)
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
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(unit, style = MaterialTheme.typography.headlineMedium)
    }
}
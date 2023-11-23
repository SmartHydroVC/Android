package com.example.smarthydro.ui.theme.screen.viewData

import android.annotation.SuppressLint
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

val openAlertDialog = mutableStateOf(false)
var powerState : Boolean = true
var reading: Reading = Reading("",SensorModel(), "","")



@Preview
@Composable
fun BarChart(){
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                text = "1000",
                fontSize = 45.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text("unit", style = MaterialTheme.typography.headlineMedium)
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
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF1E1E1E),
                modifier = Modifier
                    .height(210.dp)
                    .padding(10.dp),
                shadowElevation = 10.dp,
            ) {
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
                            text = "feature.title",
                            fontSize =  24.sp,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(2.dp))


                        Spacer(modifier = Modifier.height(4.dp))

                    }

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.size(width = 100.dp, height = 100.dp)
                    ) {
                        Text(
                            text =  "No Data",
                            modifier = Modifier.wrapContentSize(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun DataCard()
{
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                text = "1000",
                fontSize = 45.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text("unit", style = MaterialTheme.typography.headlineMedium)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF1E1E1E),
                modifier = Modifier
                    .height(210.dp)
                    .padding(10.dp),
                shadowElevation = 10.dp,
            ) {
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
                            text = "feature.title",
                            fontSize = 24.sp,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun ViewDataScreen(navHostController: NavHostController, component: ComponentViewModel, readingViewModel: ReadingViewModel, sensorViewModel: SensorViewModel) {
    val sensorData by sensorViewModel.sensorData.observeAsState(SensorModel())
    reading = readingViewModel.getReadingType()!!
    ViewDataScreen(navHostController, reading.heading, component, sensorData )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ViewDataScreen(navHostController: NavHostController,readingString:String, component: ComponentViewModel, sensorModel: SensorModel) {
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
            DataControlSection(reading.unit, component, true)
        else
            DataControlSection(reading.unit, component, false)
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
        ControlButtonsRow(component, isPhOrEc)
        DataValue(reading.readingValue, unit)
    }
}

@Composable
private fun ControlButtonsRow(
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
            toggleButtons.ToggleButton(component, R.drawable.ic_arrow_up)
            Spacer(modifier = Modifier.width(24.dp))
            toggleButtons.IconButtonOnOff(component)
            Spacer(modifier = Modifier.width(24.dp))
            toggleButtons.ToggleButton(component, R.drawable.ic_arrow_down)
        } else {
            toggleButtons.IconButtonOnOff(component)
        }
    }
}

@Composable
fun DataValue(value: String, unit: String) {
    Column(
        Modifier.fillMaxSize(),
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
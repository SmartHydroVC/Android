package com.example.smarthydro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarthydro.ui.theme.SmartHydroTheme
import com.example.smarthydro.ui.theme.screen.home.HomeScreen
import com.example.smarthydro.ui.theme.screen.viewData.SpeedTestScreen
import com.example.smarthydro.viewmodels.ComponentViewModel
import com.example.smarthydro.viewmodels.ReadingViewModel
import com.example.smarthydro.viewmodels.SensorViewModel


//https://www.youtube.com/watch?v=hGg0HjcoP9w
sealed class Destination(val route:String){
    object Home : Destination("home")
    object ViewData: Destination("viewData")
}


class MainActivity : ComponentActivity() {
    private val sensorViewModel: SensorViewModel by viewModels()
    private val component: ComponentViewModel by viewModels()
    private val reading : ReadingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHydroTheme {
                val navController = rememberNavController()
                NavAppHost(navController = navController, sensorViewModel = sensorViewModel, component, reading)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavAppHost(navController: NavHostController, sensorViewModel: SensorViewModel, componentViewModel: ComponentViewModel
               , readingViewModel: ReadingViewModel){

    NavHost(navController = navController, startDestination = "home" ){
        composable(Destination.Home.route){ HomeScreen(viewModel = sensorViewModel, navController, readingViewModel = readingViewModel)}
        composable(Destination.ViewData.route){SpeedTestScreen( navController,componentViewModel, readingViewModel = readingViewModel, sensorViewModel = sensorViewModel)}
    }
}

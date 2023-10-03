package com.example.smarthydro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarthydro.ui.theme.SmartHydroTheme
import com.example.smarthydro.ui.theme.screen.home.HomeScreen
import com.example.smarthydro.ui.theme.screen.viewData.SpeedTestScreen
import com.example.smarthydro.ui.theme.screen.login.LoginScreen
import com.example.smarthydro.viewmodels.ComponentViewModel
import com.example.smarthydro.viewmodels.ReadingViewModel
import com.example.smarthydro.viewmodels.SensorViewModel


//https://www.youtube.com/watch?v=hGg0HjcoP9w
sealed class Destination(val route:String){
    object home : Destination("home")
    object viewData: Destination("viewData")
}


class MainActivity : ComponentActivity() {
    private val viewModel: SensorViewModel by viewModels()
    private val component: ComponentViewModel by viewModels()
    private val reading : ReadingViewModel by viewModels()
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHydroTheme {
                val navController = rememberNavController()
                // HomeScreen()
                NavAppHost(navController = navController, viewModel = viewModel, component, reading)
                //HomeScreen(navController)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavAppHost(navController: NavHostController, viewModel: SensorViewModel, componentViewModel: ComponentViewModel
,readingViewModel: ReadingViewModel){

    NavHost(navController = navController, startDestination = "home" ){
        composable(Destination.home.route){ HomeScreen(viewModel = viewModel, navController, readingViewModel = readingViewModel)}
        composable(Destination.viewData.route){SpeedTestScreen( componentViewModel, readingViewModel = readingViewModel)
        }
    }
}

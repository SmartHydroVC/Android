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


//https://www.youtube.com/watch?v=hGg0HjcoP9w
sealed class Destination(val route:String){
    object home : Destination("home")
    object viewData: Destination("viewData/{readingType}"){
        fun createRoute(readingType: String) = "viewData/$readingType"
    }
}
import com.example.smarthydro.ui.theme.screen.login.LoginScreen
import com.example.smarthydro.viewmodels.SensorViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: SensorViewModel by viewModels()
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHydroTheme {
                val navController = rememberNavController()
                // HomeScreen()
                NavAppHost(navController = navController)
                //HomeScreen(navController)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavAppHost(navController: NavHostController){
    NavHost(navController = navController, startDestination = "home" ){
        composable(Destination.home.route){ HomeScreen(navController)}
        composable(Destination.viewData.route){ navBackStackEntry ->
            val readingType = navBackStackEntry.arguments?.getString("readingType")
            if (readingType == null){
                //Display Messge
            }
            else
            {
                SpeedTestScreen(readingType)
            }
        }
    }
}

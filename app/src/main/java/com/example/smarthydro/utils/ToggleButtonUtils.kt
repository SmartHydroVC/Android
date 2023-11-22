package com.example.smarthydro.utils

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smarthydro.R
import com.example.smarthydro.animations.OnOffAnimation
import com.example.smarthydro.ui.theme.screen.viewData.openAlertDialog
import com.example.smarthydro.ui.theme.screen.viewData.powerState
import com.example.smarthydro.ui.theme.screen.viewData.reading
import com.example.smarthydro.viewmodels.ComponentViewModel
import kotlinx.coroutines.launch

class ToggleButtonUtils {
    @Composable
    fun ToggleButton(componentViewModel: ComponentViewModel, @DrawableRes iconId: Int) {
        var iconColor by remember { mutableStateOf(Color.Red) }

        IconButton(
            modifier = Modifier
                .size(72.dp)
                .padding(top = 20.dp),
            onClick = {
                powerState = !powerState
                openAlertDialog.value = getAlertDialogValue(reading.heading)
                ControlComponentUtils(componentViewModel).controlComponent(reading.heading)
                iconColor = changeIconColorBasedOnPowerState(powerState)
            })
        {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Option to toggle the pump to be lower or higher",
                tint = iconColor,
                modifier = Modifier.size(size = 100.dp)
            )
        }

        if (iconId == R.drawable.ic_arrow_up)
            AlertDialogUtils().showAlertDialog(
                openAlertDialog = openAlertDialog,
                componentViewModel =componentViewModel,
                heading = reading.heading,
                dialogTitle ="Decrease Nutrients",
                dialogText ="You are about decrease solution for this!",
                toggleUp = false
            )
        else if (iconId == R.drawable.ic_arrow_down)
            AlertDialogUtils().showAlertDialog(
                openAlertDialog = openAlertDialog,
                componentViewModel =componentViewModel,
                heading = reading.heading,
                dialogTitle ="Decrease Nutrients",
                dialogText ="You are about decrease solution for this!",
                toggleUp = false
            )
    }

    @Composable
    fun IconButtonOnOff(componentViewModel: ComponentViewModel) {
        var iconColor by remember { mutableStateOf(Color.Red) }

        IconButton(
            modifier = Modifier
                .size(72.dp)
                .padding(top = 20.dp),
            onClick = {
                powerState = !powerState
                ControlComponentUtils(componentViewModel).controlComponent(reading.heading)
                iconColor = changeIconColorBasedOnPowerState(powerState)
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

    private fun changeIconColorBasedOnPowerState(powerState: Boolean): Color {
        var color = Color.Red
        if (powerState) {
            LaunchedEffect(animation) {
                launch {
                    OnOffAnimation().onAnimation(animation)
                }
            }
            color = Color.Red
        } else {
            Color.Green
        }

        return color
    }


    private fun getAlertDialogValue(heading: String): Boolean {
        if (heading == "Clean Water"|| heading == "Compost")
            return true

        return false
    }
}
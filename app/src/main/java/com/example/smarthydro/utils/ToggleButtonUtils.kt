package com.example.smarthydro.utils

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smarthydro.R
import com.example.smarthydro.ui.theme.screen.viewData.reading
import com.example.smarthydro.viewmodels.ComponentViewModel

class ToggleButtonUtils {
    @Composable
    fun ToggleButton(
        componentViewModel: ComponentViewModel,
        iconResId: Int,
        openAlertDialog: MutableState<Boolean>,
        dialogTitle: String,
        dialogText: String,
        toggleUp: Boolean,
    ) {
        IconButton(
            modifier = Modifier
                .size(72.dp)
                .padding(top = 20.dp),
            onClick = {
                openAlertDialog.value = getAlertDialogValue(reading.heading)
            })
        {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = "Option to toggle the pump to be lower or higher",
                modifier = Modifier.size(size = 100.dp)
            )
        }

        AlertDialogUtils().showAlertDialog(
            openAlertDialog = openAlertDialog,
            componentViewModel = componentViewModel,
            heading = reading.heading,
            dialogTitle = dialogTitle,
            dialogText = dialogText,
            toggleUp = toggleUp
        )

    }
    @Composable
    fun IconButtonOnOff(componentViewModel: ComponentViewModel, powerState: MutableState<Boolean>) {

        IconButton(
            modifier = Modifier
                .size(72.dp)
                .padding(top = 20.dp),
            onClick = {
                powerState.value = !powerState.value
                ControlComponentUtils(componentViewModel).controlComponent(reading.heading)
            })
        {
            Icon(
                painter = painterResource(id = R.drawable.ic_power),
                contentDescription = "Option to turn on or off the component",
                modifier = Modifier.size(size = 100.dp)
            )
        }
    }




    private fun getAlertDialogValue(heading: String): Boolean {
        return heading == "Clean Water"|| heading == "Compost"
    }
}
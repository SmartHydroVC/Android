package com.example.smarthydro.utils

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.smarthydro.R
import com.example.smarthydro.viewmodels.ComponentViewModel


class AlertDialogUtils {
    @Composable
    fun showAlertDialog(
        openAlertDialog: MutableState<Boolean>,
        componentViewModel: ComponentViewModel,
        heading: String,
        dialogTitle: String,
        dialogText: String,
        toggleUp: Boolean,
    ) {
        if (openAlertDialog.value) {
            AlertDialogModel(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    handleConfirmation(heading, toggleUp, componentViewModel)
                    openAlertDialog.value = false
                },
                dialogTitle = dialogTitle,
                dialogText = dialogText,
                icon = Icons.Default.Info
            )
        }
    }

    private fun handleConfirmation(heading: String, toggleUp: Boolean, componentViewModel: ComponentViewModel) {
        when (heading) {
            "Clean Water" -> {
                if (toggleUp) componentViewModel.setPhUp()
                else componentViewModel.setPhDown()
            }
            "Compost" -> {
                if (toggleUp) componentViewModel.setEcUp()
                else componentViewModel.setEcDown()
            }
        }
    }
    @Composable
    private fun AlertDialogModel(
        onDismissRequest: () -> Unit,
        onConfirmation: () -> Unit,
        dialogTitle: String,
        dialogText: String,
        icon: ImageVector,
    ) {
        AlertDialog(
            icon = {
                Icon(icon, contentDescription = "Info Icon")
            },
            title = {
                Text(text = dialogTitle)
            },
            text = {
                Text(text = dialogText)
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}

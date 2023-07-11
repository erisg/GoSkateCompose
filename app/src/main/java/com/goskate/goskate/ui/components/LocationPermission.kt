package com.goskate.goskate.ui.components
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Composable
fun LocationPermissionRequest(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    val PERMISSION_REQUEST_CODE = 101
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

        if (isPermissionGranted) {
            onPermissionGranted()
        } else {
            val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )

            if (shouldShowRationale) {
                showDialog.value = true
            } else {
                showDialog.value = false
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_CODE,
                )
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                onPermissionDenied()
            },
            title = { Text("Permiso de ubicación") },
            text = { Text("Necesitamos acceder a tu ubicación para brindarte una mejor experiencia.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        ActivityCompat.requestPermissions(
                            context as Activity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            PERMISSION_REQUEST_CODE,
                        )
                    },
                ) {
                    Text("Aceptar")
                }
            },
        )
    }
}

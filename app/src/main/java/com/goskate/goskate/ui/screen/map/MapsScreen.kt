package com.goskate.goskate.ui.screen.map

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.goskate.goskate.R
import com.goskate.goskate.ui.components.BottomSheetComponent
import com.goskate.goskate.ui.components.ChipFilterComponent
import com.goskate.goskate.ui.components.LocationPermissionRequest
import com.goskate.goskate.ui.theme.GoSkateTheme

@Composable
fun MapsScreen() {
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    var isPermissionGranted by remember { mutableStateOf(false) }
    var startRouteCreation by remember { mutableStateOf(false) }
    val singapore = LatLng(4.596821130786782, -74.08351824614991)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 13f)
    }
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style),
                isMyLocationEnabled = true,
            ),
        )
    }
    val shareLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {},
    )

    if (showSheet) {
        BottomSheetComponent(
            onDismiss = {
                showSheet = false
            },
            onCreateRute = {
                startRouteCreation = true
                showSheet = false
            },
            onShareLink = {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "https://gostake.com/spot/22")
                    type = "text/plain"
                }
                val chooserIntent = Intent.createChooser(intent, "Compartir texto con:")
                shareLauncher.launch(chooserIntent)
            },
        )
    }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (
            bannersTop,
            map,
        ) = createRefs()
        LocationPermissionRequest(
            onPermissionDenied = {
                isPermissionGranted = false
            },
            onPermissionGranted = {
                isPermissionGranted = true
            },
        )
        if (isPermissionGranted) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(map) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
            ) {
                Marker(
                    state = MarkerState(position = singapore),
                    title = "",
                    snippet = "",
                    onClick = { marker ->
                        showSheet = true
                        true
                    },
                )
                if (startRouteCreation) {
                    Polyline(
                        points = listOf(
                            LatLng(4.579117498048951, -74.098724377972),
                            LatLng(4.596821130786782, -74.08351824614991),
                        ),
                    )
                }
            }
        }

        ChipFilterComponent(
            modifier = Modifier
                .padding(top = 24.dp)
                .constrainAs(bannersTop) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MapsScreenPreview() {
    GoSkateTheme {
        MapsScreen()
    }
}

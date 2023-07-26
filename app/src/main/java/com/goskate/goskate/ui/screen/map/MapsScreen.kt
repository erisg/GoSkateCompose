package com.goskate.goskate.ui.screen.map

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.goskate.goskate.domain.models.Spot
import com.goskate.goskate.ui.components.BottomSheetComponent
import com.goskate.goskate.ui.components.ChipFilterComponent
import com.goskate.goskate.ui.components.LocationPermissionRequest
import com.goskate.goskate.ui.theme.GoSkateTheme
import com.goskate.goskate.ui.viewmodels.MapsViewModel

@Composable
fun MapsScreen() {
    val viewModel: MapsViewModel = hiltViewModel()
    val spotsState = viewModel.spotsState.collectAsState()
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    var isPermissionGranted by remember { mutableStateOf(false) }
    var isMapLoaded by remember { mutableStateOf(true) }
    var startRouteCreation by remember { mutableStateOf(false) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 12f)
    }
    var selectedSpot by remember() {
        mutableStateOf(Spot())
    }
    val isSuccess by remember(key1 = spotsState.value.data) {
        mutableStateOf(spotsState.value.data)
    }
    val isError by remember(key1 = spotsState.value.messageError) {
        mutableStateOf(spotsState.value.messageError)
    }
    viewModel.getAllSpots()
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions
                    .loadRawResourceStyle(
                        context,
                        R.raw.map_style,
                    ),
                isMyLocationEnabled = true,
            ),
        )
    }
    val shareLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {},
    )
    if (isError.isNotEmpty()) {
        isMapLoaded = false
    }

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
            selectedSpot = selectedSpot,
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
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 13f)
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
                isSuccess.forEach { spot ->
                    Marker(
                        state = MarkerState(
                            position = LatLng(spot.latLng.toDouble(), spot.lonLng.toDouble()),
                        ),
                        title = spot.name,
                        onClick = { marker ->
                            showSheet = true
                            selectedSpot = spot
                            true
                        },
                    )
                }

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
                .padding(top = 32.dp)
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

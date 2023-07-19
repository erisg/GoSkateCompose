package com.goskate.goskate.ui.screen.map

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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.goskate.goskate.R
import com.goskate.goskate.ui.components.BottomSheetComponent
import com.goskate.goskate.ui.components.ChipFilterComponent
import com.goskate.goskate.ui.components.LocationPermissionRequest
import com.goskate.goskate.ui.theme.GoSkateTheme

@Composable
fun MapsScreen() {
    val context = LocalContext.current
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style),
            ),
        )
    }
    var showSheet by remember { mutableStateOf(false) }
    var isPermissionGranted by remember { mutableStateOf(false) }

    if (showSheet) {
        BottomSheetComponent {
            showSheet = false
        }
    }
    val singapore = LatLng(4.573895909588669, -74.10398668418597)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 13f)
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

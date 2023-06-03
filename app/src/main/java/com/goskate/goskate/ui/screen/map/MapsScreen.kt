package com.goskate.goskate.ui.screen.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.goskate.goskate.ui.theme.GoSkateTheme

@Composable
fun MapsScreen() {
    val singapore = LatLng(4.573895909588669, -74.10398668418597)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 12f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "",
            snippet = "",
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

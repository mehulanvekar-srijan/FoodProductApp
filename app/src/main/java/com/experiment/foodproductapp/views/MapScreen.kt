package com.experiment.foodproductapp.views

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.experiment.foodproductapp.viewmodels.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel = viewModel()
) {

    val context = LocalContext.current

    val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)  //To get Current location

    val permission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    if (permission.hasPermission) {

        val mapusa = LatLng(15.590692735945124, 73.8107942417264)
        val verna = LatLng(15.364327819982055, 73.94552294164896)
        //val panjim = LatLng(15.491597654049222,73.82701456546783)
        var current: LatLng? = null

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(mapusa, 10f)
        }
        val properties = MapProperties(isMyLocationEnabled = true)
        val mapUiSettings = MapUiSettings()

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = properties,
            uiSettings = mapUiSettings,
            onMapClick = { Log.d("testMap", "MapScreen: $it") },
            cameraPositionState = cameraPositionState,
            onMyLocationClick = { },
            onMyLocationButtonClick = {
                Log.d("testMap", "onMyLocationButtonClick")

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        Log.d("testMap", "lastLocation: $location")

                        if (location != null) {
                            val lat = location.latitude
                            val lng = location.longitude
                            current = LatLng(lat, lng)
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(LatLng(lat, lng), 20f)
                        }

                    }
                }
                true
            }
        ) {
            Marker(position = mapusa)
            Marker(position = verna)
        }
    } else {
        LaunchedEffect(key1 = Unit) { permission.launchPermissionRequest() }
    }
}

@Preview
@Composable
fun PreviewMapScreen() {
    MapScreen()
}
package com.example.homegenie.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.Geocoder
import android.location.LocationManager
import com.google.android.gms.location.LocationRequest
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.location.LocationManagerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homegenie.models_.CustomerLocationDetailsModel
import com.example.homegenie.ui.theme.Blue001
import com.example.homegenie.ui.viewmodels.LocationState
import com.example.homegenie.ui.viewmodels.MapViewModel
import com.example.homegenie.utils.ProgressBar
import com.example.homegenie.utils.ResultState
import com.example.homegenie.utils.showMsg
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.libraries.places.api.Places
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapsScreen(
    navigateToHome: () -> Unit,
) {
    val mapViewModel: MapViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    //val mapViewModel: MapViewModel = hiltViewModel()
    val context = LocalContext.current
    var modal by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Blue001,
                contentColor = Color.White,
                shape = RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 10.dp, 10.dp, 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Choose your location")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                modal = true
                /*
                coroutineScope.launch(Dispatchers.Main) {
                    mapViewModel.insert(
                        LocationDetailsModel.LocationItem(
                            Firebase.auth.currentUser?.uid,
                            mapViewModel.currentLatLong.latitude,
                            mapViewModel.currentLatLong.longitude,
                            GeoFireUtils.getGeoHashForLocation(
                                GeoLocation(
                                    mapViewModel.currentLatLong.latitude,
                                    mapViewModel.currentLatLong.longitude
                                )
                            )
                        )
                    ).collect {
                        when (it) {
                            is ResultState.Success -> {
                                context.showMsg("Location saved successfully!")
                                navigateToHome()
                            }

                            is ResultState.Failure -> {
                                context.showMsg(it.msg.toString())
                            }

                            ResultState.Loading -> {
                                //isLoading = true
                            }
                        }
                    }
                }
                */

            },
                containerColor = Blue001) {
                Text(
                    text = "Add complete address",
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {

            MapLayout(modifier = Modifier.fillMaxSize())

            if (modal) {
                Dialog(onDismissRequest = { modal = false }) {
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp),
                        shadowElevation = 10.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(30.dp)
                        ) {
                            Text(text = "Add complete address!", style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = mapViewModel.addressUiState.addressDetails.houseNumber,
                                onValueChange = {
                                    mapViewModel.updateAddressUiState(
                                        mapViewModel.addressUiState.addressDetails.copy(
                                            houseNumber = it
                                        )
                                    )
                                },
                                label = { Text("House/Flat Number *", style = MaterialTheme.typography.bodySmall
                                ) },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = true,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                textStyle = MaterialTheme.typography.bodySmall
                            )

                            OutlinedTextField(
                                value = mapViewModel.addressUiState.addressDetails.locality,
                                onValueChange = {
                                    mapViewModel.updateAddressUiState(
                                        mapViewModel.addressUiState.addressDetails.copy(
                                            locality = it
                                        )
                                    )
                                },
                                label = { Text("Sector/Locality *", style = MaterialTheme.typography.bodySmall) },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = true,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                textStyle = MaterialTheme.typography.bodySmall

                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(onClick = {

                                coroutineScope.launch(Dispatchers.Main) {
                                    println(mapViewModel.currentLatLong.latitude)
                                    mapViewModel.insert(
                                        CustomerLocationDetailsModel.CustomerLocationItem(
                                            Firebase.auth.currentUser?.uid,
                                            mapViewModel.currentLatLong.latitude,
                                            mapViewModel.currentLatLong.longitude,
                                            GeoFireUtils.getGeoHashForLocation(
                                                GeoLocation(
                                                    mapViewModel.currentLatLong.latitude,
                                                    mapViewModel.currentLatLong.longitude
                                                )
                                            ),
                                            mapViewModel.addressUiState.addressDetails.houseNumber,
                                            mapViewModel.addressUiState.addressDetails.locality
                                        )
                                    ).collect {
                                        when (it) {
                                            is ResultState.Success -> {
                                                context.showMsg("Location saved successfully!")
                                                navigateToHome()
                                            }

                                            is ResultState.Failure -> {
                                                context.showMsg(it.msg.toString())
                                            }

                                            ResultState.Loading -> {
                                                //isLoading = true
                                            }
                                        }
                                    }
                                }

                            }) {
                                Text(text = "Confirm location", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }

                }



            }
        }

    }


}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun MapLayout(
    modifier: Modifier
) {
    val viewModel: MapViewModel = hiltViewModel()
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    viewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

    viewModel.placesClient = Places.createClient(context)
    viewModel.geoCoder = Geocoder(context)

    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            if (locationEnabled(activity)) {
                viewModel.getCurrentLocation()
            } else {
                viewModel.locationState = LocationState.LocationDisabled
            }
        }
    }

    AnimatedContent(
        viewModel.locationState,
        modifier = modifier.fillMaxSize(), label = ""
    ) { state ->
        when (state) {
            is LocationState.NoPermission -> {
                /*
                Column {
                    Text("We need location permission to continue")
                    Button(onClick = { locationPermissionState.launchMultiplePermissionRequest() }) {
                        Text("Request permission")
                    }
                }
                 */
                LaunchedEffect(key1 = null) {
                    scope.launch(Dispatchers.Main) {
                        locationPermissionState.launchMultiplePermissionRequest()
                    }
                }

            }

            is LocationState.LocationDisabled -> {
                /*
                Column {
                    Text("We need location to continue", style = MaterialTheme.typography.labelSmall)
                    Button(onClick = { requestLocationEnable(activity, viewModel) }) {
                        Text("Enable location", style = MaterialTheme.typography.labelSmall)
                    }
                }

                 */

                Dialog(onDismissRequest = { }) {
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp),
                        shadowElevation = 10.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(15.dp)
                        ) {
                            var text by remember {
                                mutableStateOf("Enable location")
                            }

                            Text(
                                "We need location to continue!",
                                style = MaterialTheme.typography.labelSmall
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(onClick = {
                                requestLocationEnable(activity, viewModel)
                                text = "Continue"
                            }) {
                                Text(text = text, style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }

                }

            }

            is LocationState.LocationLoading -> {
                //Text("Loading Map")
                ProgressBar()
            }

            is LocationState.Error -> {
                /*
                Column {
                    Text("Error fetching your location")
                    Button(onClick = { viewModel.getCurrentLocation() }) {
                        Text("Retry")
                    }
                }
                 */

                Dialog(onDismissRequest = { }) {
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp),
                        shadowElevation = 10.dp
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(15.dp)
                        ) {

                            Text(
                                "Error fetching your location!",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Button(onClick = {
                                viewModel.getCurrentLocation()
                            }) {
                                Text(text = "Retry", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }

                }

            }

            is LocationState.LocationAvailable -> {
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(state.cameraLatLang, 18f)
                }

                val mapUiSettings by remember {
                    mutableStateOf(
                        MapUiSettings(
                            zoomControlsEnabled = false,
                            compassEnabled = false
                        )
                    )
                }
                val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
                val scope = rememberCoroutineScope()

                LaunchedEffect(viewModel.currentLatLong) {
                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(viewModel.currentLatLong))
                }

                LaunchedEffect(cameraPositionState.isMoving) {
                    if (!cameraPositionState.isMoving) {
                        viewModel.getAddress(cameraPositionState.position.target)
                    }
                }

                Box(
                    modifier = modifier.fillMaxSize()
                ) {
                    GoogleMap(modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        uiSettings = mapUiSettings,
                        properties = mapProperties,
                        onMapClick = {
                            scope.launch {
                                cameraPositionState.animate(CameraUpdateFactory.newLatLng(it))
                            }
                        })
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )

                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(8.dp)
                            .fillMaxWidth(),
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedTextField(
                                value = viewModel.text, onValueChange = {
                                    viewModel.text = it
                                    viewModel.searchPlaces(it)
                                }, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp),
                                textStyle = MaterialTheme.typography.bodySmall
                            )
                            AnimatedVisibility(
                                viewModel.locationAutofill.isNotEmpty(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(viewModel.locationAutofill) {
                                        Row(modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                            .clickable {
                                                viewModel.text = it.address
                                                viewModel.locationAutofill.clear()
                                                viewModel.getCoordinates(it)
                                            }) {
                                            Text(it.address, style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                    }
                                }
                                Spacer(Modifier.height(16.dp))
                            }

                        }
                    }
                }
            }
        }
    }


}

private fun locationEnabled(activity: Activity): Boolean {

    val locationManager =
        activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return LocationManagerCompat.isLocationEnabled(locationManager)
}

private fun requestLocationEnable(activity: Activity, viewModel: MapViewModel) {
    activity.let {
        val locationRequest = LocationRequest.create()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        LocationServices.getSettingsClient(it).checkLocationSettings(builder.build())
            .addOnSuccessListener {
                if (it.locationSettingsStates?.isLocationPresent == true) {
                    viewModel.getCurrentLocation()
                }
            }.addOnFailureListener {
                if (it is ResolvableApiException) {
                    try {
                        it.startResolutionForResult(activity, 999)
                    } catch (e: IntentSender.SendIntentException) {
                        e.printStackTrace()
                    }
                }
            }

    }
}

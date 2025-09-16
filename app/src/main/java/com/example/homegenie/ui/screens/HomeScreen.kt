package com.example.homegenie.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.homegenie.R
import com.example.homegenie.data.serviceItems
import com.example.homegenie.ui.theme.Blue001
import com.example.homegenie.ui.viewmodels.HomeScreenViewModel
import com.example.homegenie.utils.ProgressBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToServices: (arg: Int) -> Unit,
    navigateToMap: () -> Unit,
    navigateToRegistration: () -> Unit
) {
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
    val location = homeScreenViewModel.location
    val customer = homeScreenViewModel.response

    if (location.data.item?.lat != null && !location.isLoading && !customer.isLoading) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .clickable { navigateToMap() },
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "location",
                            tint = Blue001
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "${location.data.item.houseNumber}, ${location.data.item.locality}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                    }
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            //.width(120.dp)
                            .padding(0.dp, 0.dp, 30.dp, 0.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_white),
                            contentDescription = "logo",
                            modifier = Modifier
                                .height(50.dp),
                            colorFilter = ColorFilter.tint(Blue001)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    //modifier = Modifier.clickable { context.showMsg("Stay tuned!") },
                    readOnly = true,
                    placeholder = {
                        Text(
                            text = "Search for a service...",
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            "Search",
                            tint = Blue001
                        )
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Filled.Clear,
                            contentDescription = "",
                            tint = Blue001
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(20.dp),
                    //colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Blue001, focusedBorderColor = Blue001)
                )

                Spacer(modifier = Modifier.height(5.dp))
                Divider(modifier = Modifier.padding(15.dp))
                Text(
                    text = "Services you can book!",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(10.dp))


            serviceItems.forEachIndexed() { index, item ->
                ServiceItem(
                    image = item.urls[0],
                    serviceName = item.serviceName,
                    rating = item.ratings,
                    index = index,
                    navigateToServices = navigateToServices
                )
            }



                Spacer(modifier = Modifier.height(10.dp))
        }
    }
    if(location.data.item?.lat == null && !location.isLoading && !customer.isLoading){
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
                        text = "Please, add your location!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(onClick = { navigateToMap() }) {
                        Text(text = "Add location", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
    if(customer.data.item?.name == null && !location.isLoading && !customer.isLoading){
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
                        text = "Please, register to continue!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(onClick = { navigateToRegistration() }) {
                        Text(text = "Register", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

        }
    }

    if(location.isLoading || customer.isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ProgressBar()
        }
    }
}

@Composable
fun ServiceItem(
    image: String,
    serviceName: String,
    rating: Double,
    index: Int,
    navigateToServices: (arg: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp, 10.dp)
            .clickable {
                navigateToServices(index)
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(modifier = Modifier) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.aspectRatio(16 / 9f),
                contentScale = ContentScale.FillBounds
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = serviceName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row {
                    Text(
                        text = rating.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Blue001
                    )
                }
            }
        }
    }
}
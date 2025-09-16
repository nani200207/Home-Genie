package com.example.homegenie.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.homegenie.R
import com.example.homegenie.data.ShopItem
import com.example.homegenie.data.shopItems
import com.example.homegenie.models.PainterService
import com.example.homegenie.models.SalonService
import com.example.homegenie.ui.theme.Blue001
import com.example.homegenie.utils.ResultState
import com.example.homegenie.utils.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShopScreen(
    navigateToConfirmation: () -> Unit
) {

//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(painter = painterResource(id = R.drawable.store), contentDescription = "shop", modifier = Modifier.size(200.dp))
//        Spacer(modifier = Modifier.height(10.dp))
//    }

    var totalPrice by remember {
        mutableDoubleStateOf(0.0)
    }

    var totalProducts by remember { mutableIntStateOf(0) }

    val productsList = remember {
        mutableStateListOf<ShopItem>()
    }

    Scaffold(
        snackbarHost = {
            if(totalProducts > 0) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    shadowElevation = 10.dp,
                    color = Color.Black
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = if (totalProducts > 1) {
                                    "$totalProducts pcs."
                                } else {
                                    "$totalProducts pc"
                                },
                                color = Color.White, style = MaterialTheme.typography.bodySmall
                            )

                            Text(
                                text = "$ ${
                                    BigDecimal(totalPrice).setScale(
                                        2,
                                        RoundingMode.HALF_EVEN
                                    )
                                }",
                                color = Color.White, style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            text = "Confirm",
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    productsList.clear()
                                    totalProducts = 0
                                    totalPrice = 0.0
                                    navigateToConfirmation()
                                }
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            shopItems.forEach { product ->

                var count by remember { mutableIntStateOf(0) }
                count = productsList.count { it == product }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 10.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(0.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(product.url)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .height(160.dp)
                                .width(160.dp),
                            contentScale = ContentScale.FillBounds
                        )
                        //Text(text = "")
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = product.productName,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "$ ${product.productPrice}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Card(
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(Blue001),
                                modifier = Modifier
                                    //.width(80.dp)
                                    .height(60.dp)
                                    .padding(10.dp)
                            ) {
                                if (productsList.contains(product)) {
                                    Row(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(onClick = {
                                            productsList.remove(product)
                                            totalProducts -= 1
                                            totalPrice -= product.productPrice
                                        }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_remove_24),
                                                contentDescription = "remove"
                                            )
                                        }
                                        Text(text = count.toString(),
                                            style = MaterialTheme.typography.labelSmall,
                                        )
                                        IconButton(onClick = {
                                            productsList.add(product)
                                            totalProducts += 1
                                            totalPrice += product.productPrice

                                        }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_add_24),
                                                contentDescription = "add"
                                            )
                                        }
                                    }
                                } else {
                                    Text(
                                        text = "Add",
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxSize()
                                            .clickable {
                                                productsList.add(product)
                                                totalProducts += 1
                                                totalPrice += product.productPrice
                                            },
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(80.dp))
        }

    }

}
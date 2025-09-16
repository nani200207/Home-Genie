package com.example.homegenie.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.sharp.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.homegenie.R
import com.example.homegenie.ui.theme.Blue001
import com.example.homegenie.ui.viewmodels.CartScreenViewModel
import com.example.homegenie.utils.ResultState
import com.example.homegenie.utils.showMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    isVisible: Boolean,
    navigateToConfirmation: () -> Unit
) {
    val cartScreenViewModel: CartScreenViewModel = hiltViewModel()
    val response = cartScreenViewModel.response

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var totalPrice by remember {
        mutableDoubleStateOf(0.0)
    }

    var totalService by remember { mutableLongStateOf(0) }

    println("respdata ${response.data.size}")

    if(totalService < response.data.size) {
        response.data.forEach { item ->
            totalService += item.item?.itemQty!!
            totalPrice += item.item.itemPrice!!
        }
    }

    Scaffold(
        //modifier = Modifier.padding(10.dp),
        topBar = {
            if(isVisible) {
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
                        Text(text = "Services Cart")
                    }
                }
            }
        },
        snackbarHost = {
            if(response.data.isNotEmpty()){

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
                                text = if (totalService > 1) {
                                    "$totalService pcs."
                                } else {
                                    "$totalService pc"
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
                                    response.data.forEach { item ->
                                        scope.launch(Dispatchers.Main) {
                                            cartScreenViewModel
                                                .removeCartItem(item.key!!)
                                                .collect {
                                                    when (it) {
                                                        is ResultState.Success -> {
                                                            //context.showMsg("")
                                                            totalService -= item.item?.itemQty!!
                                                            totalPrice -= item.item.itemPrice!!
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
                                    }
                                    navigateToConfirmation()


                                }
                        )
                    }
                }
            }
        }
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (response.data.isNotEmpty()) {
                item { 
                    Spacer(modifier = Modifier.height(20.dp))
                }
                items(response.data) { item ->
                    val text = when(item.item?.itemQty!!){
                        1L -> "pc."
                        else -> "pcs."
                    }
//                    totalService += item.item.itemQty!!
//                    totalPrice += item.item.itemPrice!!

                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp, 10.dp),
                        shape = RoundedCornerShape(15.dp),
                        elevation = CardDefaults.cardElevation(5.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        //border = BorderStroke(1.dp, Color.LightGray)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.width(100.dp)
                            ) {
                                Text(
                                    text = item.item.itemName!!,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "$ ${item.item.itemPrice}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))

                                    Text(text = "|",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))

                                    Text(text = "${item.item.itemQty} $text",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                            FloatingActionButton(
                                onClick = {
                                    scope.launch(Dispatchers.Main) {
                                        cartScreenViewModel.removeCartItem(item.key!!).collect {
                                            when (it) {
                                                is ResultState.Success -> {
                                                    context.showMsg(it.data)
                                                    totalService -= item.item.itemQty!!
                                                    totalPrice -= item.item.itemPrice!!
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
                                },
                                containerColor = Blue001,
                                modifier = Modifier
                                    //.width(80.dp)
                                    .height(40.dp),
                            ) {
                                Row(modifier = Modifier.padding(10.dp, 0.dp)) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "delete",
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = "Remove",
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }

                }
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            } else if(
                response.isLoading
            ){
                item {
                    Box(
                        modifier = Modifier.height(500.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            else {
                item {
                    Column(
                        modifier = Modifier.height(500.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.cart),
//                            contentDescription = "cart",
//                            modifier = Modifier.height(250.dp)
//                        )
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fcart.jpg?alt=media&token=13839aeb-e0b5-4ecc-b150-2bcea319d081")
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .height(250.dp),
                                //.width(160.dp),
                            //contentScale = ContentScale.FillBounds
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Oops, no item in the cart yet!!",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }

}
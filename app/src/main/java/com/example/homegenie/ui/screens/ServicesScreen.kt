package com.example.homegenie.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.homegenie.R
import com.example.homegenie.data.serviceItems
import com.example.homegenie.models.CarpenterService
import com.example.homegenie.models.ElectricianService
import com.example.homegenie.models.GardenerService
import com.example.homegenie.models.HousekeepingService
import com.example.homegenie.models.MechanicService
import com.example.homegenie.models.PainterService
import com.example.homegenie.models.PlumberService
import com.example.homegenie.models.SalonService
import com.example.homegenie.models.carpenterServices
import com.example.homegenie.models.electricianServices
import com.example.homegenie.models.gardenerServices
import com.example.homegenie.models.housekeepingServices
import com.example.homegenie.models.mechanicServices
import com.example.homegenie.models.painterServices
import com.example.homegenie.models.plumberServices
import com.example.homegenie.models.salonServices
import com.example.homegenie.models_.CartDetailsModel
import com.example.homegenie.ui.theme.Blue001
import com.example.homegenie.ui.viewmodels.ServicesScreenViewModel
import com.example.homegenie.utils.ResultState
import com.example.homegenie.utils.showMsg
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    index: Int,
    //navigateToConfirmation: () -> Unit,
    navigateToCart: (arg: Boolean) -> Unit) {

    val servicesScreenViewModel: ServicesScreenViewModel = hiltViewModel()

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var totalPrice by remember {
        mutableDoubleStateOf(0.0)
    }

    var totalService by remember { mutableIntStateOf(0) }


//    val listType = when (index) {
//        0 -> salonServices
//        1 -> plumberServices
//        2 -> housekeepingServices
//        3 -> carpenterServices
//        4 -> electricianServices
//        5 -> gardenerServices
//        6 -> painterServices
//        7 -> mechanicServices
//        else -> salonServices
//    }

    val salonList = remember {
        mutableStateListOf<SalonService>()
    }

    val plumberList = remember {
        mutableStateListOf<PlumberService>()
    }

    val houseList = remember {
        mutableStateListOf<HousekeepingService>()
    }

    val carpenterList = remember {
        mutableStateListOf<CarpenterService>()
    }

    val electricianList = remember {
        mutableStateListOf<ElectricianService>()
    }

    val gardenerList = remember {
        mutableStateListOf<GardenerService>()
    }

    val painterList = remember {
        mutableStateListOf<PainterService>()
    }

    val mechanicList = remember {
        mutableStateListOf<MechanicService>()
    }


    Scaffold(
        modifier = Modifier.padding(10.dp, 0.dp),
        snackbarHost = {
            if (totalService != 0) {
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
                                    "$totalService services"
                                } else {
                                    "$totalService service"
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
                                    when (index) {
                                        0 -> salonList.forEach { item ->
                                            scope.launch {
                                                servicesScreenViewModel
                                                    .insertItemToCart(
                                                        item = CartDetailsModel.CartItem(
                                                            uid = Firebase.auth.currentUser?.uid,
                                                            itemName = item.serviceName,
                                                            itemPrice = item.servicePrice,
                                                            itemQty = 1
                                                        )
                                                    )
                                                    .collect {
                                                        when (it) {
                                                            is ResultState.Success -> {
                                                                context.showMsg(it.data)

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
                                        0 -> salonList.forEach { item ->
                                            scope.launch {
                                                servicesScreenViewModel
                                                    .insertItemToCart(
                                                        item = CartDetailsModel.CartItem(
                                                            uid = Firebase.auth.currentUser?.uid,
                                                            itemName = item.serviceName,
                                                            itemPrice = item.servicePrice,
                                                            itemQty = 1
                                                        )
                                                    )
                                                    .collect {
                                                        when (it) {
                                                            is ResultState.Success -> {
                                                                context.showMsg(it.data)

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
                                        1 -> plumberList.forEach { item ->
                                            scope.launch {
                                                servicesScreenViewModel
                                                    .insertItemToCart(
                                                        item = CartDetailsModel.CartItem(
                                                            uid = Firebase.auth.currentUser?.uid,
                                                            itemName = item.serviceName,
                                                            itemPrice = item.servicePrice,
                                                            itemQty = 1
                                                        )
                                                    )
                                                    .collect {
                                                        when (it) {
                                                            is ResultState.Success -> {
                                                                context.showMsg(it.data)

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
                                        2 -> houseList.forEach { item ->
                                            scope.launch {
                                                servicesScreenViewModel
                                                    .insertItemToCart(
                                                        item = CartDetailsModel.CartItem(
                                                            uid = Firebase.auth.currentUser?.uid,
                                                            itemName = item.serviceName,
                                                            itemPrice = item.servicePrice,
                                                            itemQty = 1
                                                        )
                                                    )
                                                    .collect {
                                                        when (it) {
                                                            is ResultState.Success -> {
                                                                context.showMsg(it.data)

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
                                        3 -> carpenterList.forEach { item ->
                                            scope.launch {
                                                servicesScreenViewModel
                                                    .insertItemToCart(
                                                        item = CartDetailsModel.CartItem(
                                                            uid = Firebase.auth.currentUser?.uid,
                                                            itemName = item.serviceName,
                                                            itemPrice = item.servicePrice,
                                                            itemQty = 1
                                                        )
                                                    )
                                                    .collect {
                                                        when (it) {
                                                            is ResultState.Success -> {
                                                                context.showMsg(it.data)

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
                                        4 -> electricianList.forEach { item ->
                                            scope.launch {
                                                servicesScreenViewModel
                                                    .insertItemToCart(
                                                        item = CartDetailsModel.CartItem(
                                                            uid = Firebase.auth.currentUser?.uid,
                                                            itemName = item.serviceName,
                                                            itemPrice = item.servicePrice,
                                                            itemQty = 1
                                                        )
                                                    )
                                                    .collect {
                                                        when (it) {
                                                            is ResultState.Success -> {
                                                                context.showMsg(it.data)

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
                                        5 -> gardenerList.forEach { item ->
                                            scope.launch {
                                                servicesScreenViewModel
                                                    .insertItemToCart(
                                                        item = CartDetailsModel.CartItem(
                                                            uid = Firebase.auth.currentUser?.uid,
                                                            itemName = item.serviceName,
                                                            itemPrice = item.servicePrice,
                                                            itemQty = 1
                                                        )
                                                    )
                                                    .collect {
                                                        when (it) {
                                                            is ResultState.Success -> {
                                                                context.showMsg(it.data)

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
                                        6 -> painterList.forEach { item ->
                                            scope.launch {
                                                servicesScreenViewModel
                                                    .insertItemToCart(
                                                        item = CartDetailsModel.CartItem(
                                                            uid = Firebase.auth.currentUser?.uid,
                                                            itemName = item.serviceName,
                                                            itemPrice = item.servicePrice,
                                                            itemQty = 1
                                                        )
                                                    )
                                                    .collect {
                                                        when (it) {
                                                            is ResultState.Success -> {
                                                                context.showMsg(it.data)

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
                                        7 -> mechanicList.forEach { item ->
                                            scope.launch {
                                                servicesScreenViewModel
                                                    .insertItemToCart(
                                                        item = CartDetailsModel.CartItem(
                                                            uid = Firebase.auth.currentUser?.uid,
                                                            itemName = item.serviceName,
                                                            itemPrice = item.servicePrice,
                                                            itemQty = 1
                                                        )
                                                    )
                                                    .collect {
                                                        when (it) {
                                                            is ResultState.Success -> {
                                                                context.showMsg(it.data)

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
                                    }
                                    navigateToCart(true)
                                }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = serviceItems[index].serviceName)
            Spacer(modifier = Modifier.height(20.dp))
            BannerSalon(
                serviceItems[index].urls
            )
            Spacer(modifier = Modifier.height(20.dp))
            Divider()
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Book your services!")
            Spacer(modifier = Modifier.height(10.dp))

            when (index) {
                0 -> salonServices.forEach { item ->
                    SubServiceItem(
                        serviceName = item.serviceName,
                        servicePrice = item.servicePrice,
                        isSelected = salonList.contains(item),
                        onClick = { price, selected ->
                            if (selected) {
                                totalService--
                                totalPrice -= price
                                salonList.remove(item)

                            } else {
                                totalService++
                                totalPrice += price
                                salonList.add(item)
                            }
                        }
                    )
                }

                1 -> plumberServices.forEach { item ->
                    SubServiceItem(
                        serviceName = item.serviceName,
                        servicePrice = item.servicePrice,
                        isSelected = plumberList.contains(item),
                        onClick = { price, selected ->
                            if (selected) {
                                totalService--
                                totalPrice -= price
                                plumberList.remove(item)
                            } else {
                                totalService++
                                totalPrice += price
                                plumberList.add(item)
                            }
                        }
                    )
                }

                2 -> housekeepingServices.forEach { item ->
                    SubServiceItem(
                        serviceName = item.serviceName,
                        servicePrice = item.servicePrice,
                        isSelected = houseList.contains(item),
                        onClick = { price, selected ->
                            if (selected) {
                                totalService--
                                totalPrice -= price
                                houseList.remove(item)
                            } else {
                                totalService++
                                totalPrice += price
                                houseList.add(item)
                            }
                        }
                    )
                }

                3 -> carpenterServices.forEach { item ->
                    SubServiceItem(
                        serviceName = item.serviceName,
                        servicePrice = item.servicePrice,
                        isSelected = carpenterList.contains(item),
                        onClick = { price, selected ->
                            if (selected) {
                                totalService--
                                totalPrice -= price
                                carpenterList.remove(item)
                            } else {
                                totalService++
                                totalPrice += price
                                carpenterList.add(item)
                            }
                        }
                    )
                }

                4 -> electricianServices.forEach { item ->
                    SubServiceItem(
                        serviceName = item.serviceName,
                        servicePrice = item.servicePrice,
                        isSelected = electricianList.contains(item),
                        onClick = { price, selected ->
                            if (selected) {
                                totalService--
                                totalPrice -= price
                                electricianList.remove(item)
                            } else {
                                totalService++
                                totalPrice += price
                                electricianList.add(item)
                            }
                        }
                    )
                }

                5 -> gardenerServices.forEach { item ->
                    SubServiceItem(
                        serviceName = item.serviceName,
                        servicePrice = item.servicePrice,
                        isSelected = gardenerList.contains(item),
                        onClick = { price, selected ->
                            if (selected) {
                                totalService--
                                totalPrice -= price
                                gardenerList.remove(item)
                            } else {
                                totalService++
                                totalPrice += price
                                gardenerList.add(item)
                            }
                        }
                    )
                }

                6 -> painterServices.forEach { item ->
                    SubServiceItem(
                        serviceName = item.serviceName,
                        servicePrice = item.servicePrice,
                        isSelected = painterList.contains(item),
                        onClick = { price, selected ->
                            if (selected) {
                                totalService--
                                totalPrice -= price
                                painterList.remove(item)
                            } else {
                                totalService++
                                totalPrice += price
                                painterList.add(item)
                            }
                        }
                    )
                }

                7 -> mechanicServices.forEach { item ->
                    SubServiceItem(
                        serviceName = item.serviceName,
                        servicePrice = item.servicePrice,
                        isSelected = mechanicList.contains(item),
                        onClick = { price, selected ->
                            if (selected) {
                                totalService--
                                totalPrice -= price
                                mechanicList.remove(item)
                            } else {
                                totalService++
                                totalPrice += price
                                mechanicList.add(item)
                            }
                        }
                    )
                }

            }

            Spacer(modifier = Modifier.height(80.dp))

        }
    }
}

@Composable
fun SubServiceItem(
    serviceName: String,
    servicePrice: Double,
    isSelected: Boolean = false,
    onClick: (Double, Boolean) -> Unit
) {
    val text = when (isSelected) {
        true -> "Remove"
        false -> "Add"
    }
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(5.dp, 10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(Color.White),
        //border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.width(100.dp)
            ) {
                Text(
                    text = serviceName,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "$ $servicePrice",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            FloatingActionButton(
                onClick = {
                    onClick(
                        servicePrice,
                        isSelected
                    )
                },
                containerColor = Blue001,
                modifier = Modifier
                    .width(80.dp)
                    .height(40.dp),
            ) {
                Text(
                    text = text,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BannerSalon(
    list: List<String>
) {

    val pagerState = rememberPagerState()
    val bannerIndex = remember { mutableStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            bannerIndex.value = page
        }
    }

    /// auto scroll

    LaunchedEffect(Unit) {
        while (true) {
            delay(3_000)
            tween<Float>(500)

            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % pagerState.pageCount
            )
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .aspectRatio(4 / 3f)

        //.height(190.dp)
        //.padding(horizontal = 24.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            count = list.size,
            modifier = Modifier
                .fillMaxSize()
            //.size(200.dp)
        ) { index ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(list[index])
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            repeat(list.size) { index ->
                val height = 12.dp
                val width = if (index == bannerIndex.value) 28.dp else 12.dp
                val color = if (index == bannerIndex.value) Blue001 else Color.White

                Surface(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(width, height)
                        .clip(RoundedCornerShape(20.dp)),
                    color = color,
                ) {
                }
            }
        }
    }
}
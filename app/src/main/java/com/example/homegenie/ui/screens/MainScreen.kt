package com.example.homegenie.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homegenie.data.navBarItems
import com.example.homegenie.ui.theme.Blue001


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigateToServices : (arg: Int) -> Unit,
    navigateToLogin : () -> Unit,
    navigateToConfirmation: () -> Unit,
    navigateToMap: () -> Unit,
    navigateToRegistration: () -> Unit
) {
    var index by remember {
        mutableIntStateOf(0)
    }
    Scaffold(
        topBar = {
            if (index != 0) {
                val title = when (index){
                    1 -> "Services Cart"
                    2 -> "Shop"
                    3 -> "Profile"
                    else -> ""
                }
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
                        Text(text = title)
                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                tonalElevation = 20.dp,
                modifier = Modifier.shadow(elevation = 20.dp)
            ) {
                navBarItems.forEachIndexed { i, item ->
                    NavigationBarItem(
                        selected = i == index,
                        onClick = { index = i },
                        icon = {
                               Icon(imageVector = item.icon, contentDescription = "")
                        },
                        colors = NavigationBarItemDefaults.colors(
                            Color.White,
                            Color.Black,
                            Blue001,
                            Blue001,
                            Color.Black
                        ),
                        label = {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 12.sp
                            )
                        })

                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (index) {
                0 -> HomeScreen(navigateToServices = navigateToServices, navigateToMap = navigateToMap, navigateToRegistration = navigateToRegistration)
                1 -> CartScreen(isVisible = false, navigateToConfirmation = navigateToConfirmation)
                2 -> ShopScreen(navigateToConfirmation = navigateToConfirmation)
                3 -> ProfileScreen(navigateToLogin)
            }
        }

    }
}
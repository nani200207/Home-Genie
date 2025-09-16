package com.example.homegenie.ui.screens

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.homegenie.R
import com.example.homegenie.ui.theme.Blue001
import kotlinx.coroutines.delay

@Composable
fun ConfirmationScreen(
    navigateToMain : () -> Unit
){
    val media = MediaPlayer.create(LocalContext.current, R.raw.tone)

    LaunchedEffect(key1 = null){
        media.start()

        delay(3000)
        navigateToMain()
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            //Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Surface(modifier = Modifier.wrapContentSize(), color = Blue001, shape = CircleShape) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(200.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            //}
            Text(
                text = "Thanks, your booking has been confirmed!",
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}
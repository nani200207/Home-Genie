package com.example.homegenie.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Blue001,
    secondary = Blue001,
    tertiary = Pink80,
    background = White,

    onPrimary = White,
    onSecondary = Black,
    onBackground = Black
    //onPrimary = Black,
    //onSecondary = Black,
    //background = Black
)

private val LightColorScheme = lightColorScheme(
    primary = Blue001,
    secondary = Blue001,
    tertiary = Pink40,
    background = White,
    onPrimary = White,
    onSecondary = Black,
    onBackground = Black
)

//private val DarkColorScheme = darkColorScheme(
//        primary = Purple80,
//        secondary = PurpleGrey80,
//        tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//        primary = Purple40,
//        secondary = PurpleGrey40,
//        tertiary = Pink40
//)

        /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */


@Composable
fun HomeGenieTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Blue001
        )
        systemUiController.setNavigationBarColor(
            color = Color.White
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        //shapes = Shapes,
        content = content
    )
}
package com.example.homegenie.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.homegenie.R

val NewFont = FontFamily(Font(R.font.livvic))

val Typography = Typography(
        displayLarge = TextStyle(
                fontFamily = NewFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                fontStyle = FontStyle.Normal
        ),
        displayMedium = TextStyle(
                fontFamily = NewFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal
        ),
        displaySmall = TextStyle(
                fontFamily = NewFont,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                fontStyle = FontStyle.Normal
        ),
        bodySmall = TextStyle(
                fontFamily = NewFont,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                fontStyle = FontStyle.Normal
        ),
        bodyMedium = TextStyle(
                fontFamily = NewFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal
        ),
        bodyLarge = TextStyle(
                fontFamily = NewFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                fontStyle = FontStyle.Normal
        ),
        labelSmall = TextStyle(
                fontFamily = NewFont,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                fontStyle = FontStyle.Normal
        )

)

// Set of Material typography styles to start with
//val Typography = Typography(
//        bodyLarge = TextStyle(
//                fontFamily = FontFamily.Default,
//                fontWeight = FontWeight.Normal,
//                fontSize = 16.sp,
//                lineHeight = 24.sp,
//                letterSpacing = 0.5.sp
//        )
        /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
//)

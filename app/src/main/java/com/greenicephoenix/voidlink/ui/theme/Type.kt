package com.greenicephoenix.voidlink.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.greenicephoenix.voidlink.R // Imports your app's resources

// Define the custom dot-matrix font family
val DotMatrixFont = FontFamily(
    Font(R.font.custom_dot_matrix, FontWeight.Normal) // Ensure the filename matches your .ttf file exactly
)

// Set of Material typography styles to start with
val Typography = Typography(
    // Headers & Numbers (Using the custom dot-matrix font)
    displayLarge = TextStyle(
        fontFamily = DotMatrixFont,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = DotMatrixFont,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    // Body Text (Standard Sans-Serif for readability)
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = DotMatrixFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.sp // Spaced out for techy look
    )
)
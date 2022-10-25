package com.experiment.foodproductapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

var systemUiController : SystemUiController? = null

@Composable
fun ChangeBarColors(statusColor: Color = Orange, navigationBarColor: Color = Orange) {
    systemUiController = rememberSystemUiController()
    systemUiController?.setStatusBarColor(color = statusColor, darkIcons = true)
    systemUiController?.setNavigationBarColor(color = navigationBarColor, darkIcons = true)
}

@Composable
fun FoodProductAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
//        DarkColorPalette
        LightColorPalette
    } else {
        LightColorPalette
    }

    rememberSystemUiController().apply {
        this.setSystemBarsColor(Purple700)
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
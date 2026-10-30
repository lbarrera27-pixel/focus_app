package com.focus.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Familia tipográfica. En Figma es Inter; aquí usa la fuente del sistema hasta que
 * agregues Inter (ver README.md, sección "Agregar la fuente Inter").
 */
val FocusFont: FontFamily = FontFamily.Default

/** Los 10 estilos de texto de Figma ("FOCUS/…"). Ningún texto baja de 11 sp. */
@Immutable
data class FocusTypography(
    val display: TextStyle = TextStyle(fontFamily = FocusFont, fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 34.sp, letterSpacing = (-0.8).sp),
    val title1: TextStyle = TextStyle(fontFamily = FocusFont, fontWeight = FontWeight.Normal, fontSize = 22.sp, lineHeight = 27.sp, letterSpacing = (-0.88).sp),
    val title2: TextStyle = TextStyle(fontFamily = FocusFont, fontWeight = FontWeight.Bold, fontSize = 20.sp, lineHeight = 25.sp, letterSpacing = (-0.4).sp),
    val title3: TextStyle = TextStyle(fontFamily = FocusFont, fontWeight = FontWeight.Bold, fontSize = 16.sp, lineHeight = 21.sp, letterSpacing = (-0.2).sp),
    val body: TextStyle = TextStyle(fontFamily = FocusFont, fontWeight = FontWeight.Normal, fontSize = 13.sp, lineHeight = 18.sp),
    val bodyBold: TextStyle = TextStyle(fontFamily = FocusFont, fontWeight = FontWeight.Bold, fontSize = 13.sp, lineHeight = 18.sp),
    val text: TextStyle = TextStyle(fontFamily = FocusFont, fontWeight = FontWeight.Normal, fontSize = 11.sp, lineHeight = 15.sp),
    val textSemibold: TextStyle = TextStyle(fontFamily = FocusFont, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, lineHeight = 15.sp),
    val textBold: TextStyle = TextStyle(fontFamily = FocusFont, fontWeight = FontWeight.Bold, fontSize = 11.sp, lineHeight = 15.sp),
    /** Sobretítulo: se escribe en MAYÚSCULAS (ej. "PLAN ADAPTATIVO"). */
    val overline: TextStyle = TextStyle(fontFamily = FocusFont, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, lineHeight = 14.sp, letterSpacing = 0.9.sp),
)

package com.focus.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val LocalFocusColors = staticCompositionLocalOf { LightFocusColors }
private val LocalFocusTypography = staticCompositionLocalOf { FocusTypography() }

/** Radios y espacios que se repiten en el diseño. */
object FocusShapes {
    val card: Dp = 17.dp
    val hero: Dp = 22.dp
    val button: Dp = 15.dp
    val input: Dp = 14.dp
    val small: Dp = 12.dp
}

object FocusSpacing {
    /** Margen lateral de todas las pantallas (24 en Figma). */
    val screen: Dp = 24.dp
    val section: Dp = 12.dp
    val card: Dp = 16.dp
}

/** Acceso corto: FocusTheme.colors.textPrimary, FocusTheme.type.title1… */
object FocusTheme {
    val colors: FocusColors
        @Composable @ReadOnlyComposable get() = LocalFocusColors.current
    val type: FocusTypography
        @Composable @ReadOnlyComposable get() = LocalFocusTypography.current
}

/**
 * Tema de la app. Sigue el modo claro/oscuro del teléfono, igual que el modo
 * "Light"/"Dark" de las variables de Figma.
 */
@Composable
fun FocusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkFocusColors else LightFocusColors
    // Material 3 se alimenta de los mismos colores, para que sus componentes combinen.
    val scheme = if (darkTheme) {
        darkColorScheme(
            primary = colors.brandPrimary, onPrimary = colors.textOnBrandStrong,
            background = colors.bgApp, onBackground = colors.textPrimary,
            surface = colors.bgSurface, onSurface = colors.textPrimary,
            surfaceVariant = colors.bgSurfaceMuted, onSurfaceVariant = colors.textSecondary,
            outline = colors.borderInput, error = colors.textDanger,
        )
    } else {
        lightColorScheme(
            primary = colors.brandPrimary, onPrimary = colors.textOnBrandStrong,
            background = colors.bgApp, onBackground = colors.textPrimary,
            surface = colors.bgSurface, onSurface = colors.textPrimary,
            surfaceVariant = colors.bgSurfaceMuted, onSurfaceVariant = colors.textSecondary,
            outline = colors.borderInput, error = colors.textDanger,
        )
    }
    CompositionLocalProvider(
        LocalFocusColors provides colors,
        LocalFocusTypography provides FocusTypography(),
    ) {
        MaterialTheme(colorScheme = scheme, content = content)
    }
}

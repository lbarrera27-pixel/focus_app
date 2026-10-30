package com.focus.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Colores de FOCUS!. Son los mismos valores que la colección de variables
 * "FOCUS / Color" de Figma (modos Light y Dark).
 */
@Immutable
data class FocusColors(
    // Texto
    val textPrimary: Color,
    val textStrong: Color,
    val textSecondaryStrong: Color,
    val textSecondary: Color,
    val textAccent: Color,
    val textAccentStrong: Color,
    val textOnBrand: Color,
    val textOnBrandStrong: Color,
    val textOnBrandSoft: Color,
    val textSuccess: Color,
    val textSuccessOnBrand: Color,
    val textInfo: Color,
    val textDanger: Color,
    val textOnDanger: Color,
    // Marca
    val brandPrimary: Color,
    val brandSoft: Color,
    val brandSubtle: Color,
    // Fondos
    val bgApp: Color,
    val bgSurface: Color,
    val bgSurfaceMuted: Color,
    val bgSurfaceSunken: Color,
    val bgDanger: Color,
    val bgDangerSoft: Color,
    val bgSuccessSoft: Color,
    val bgInfoSoft: Color,
    val bgToggleOff: Color,
    // Bordes
    val borderDefault: Color,
    val borderInput: Color,
    val borderBrand: Color,
    val borderFocus: Color,
    // Íconos
    val iconPrimary: Color,
    val iconSecondary: Color,
    val iconMuted: Color,
    val iconOnBrand: Color,
    val isDark: Boolean,
)

/** Degradado de las tarjetas principales (Tiempo de pantalla, Tu meta). Igual en ambos modos. */
val HeroGradientStart = Color(0xFFFCD34D)
val HeroGradientEnd = Color(0xFFF59E0B)

val LightFocusColors = FocusColors(
    textPrimary = Color(0xFF0F172A),
    textStrong = Color(0xFF334155),
    textSecondaryStrong = Color(0xFF475569),
    textSecondary = Color(0xFF64748B),
    textAccent = Color(0xFFB45309),
    textAccentStrong = Color(0xFF92400E),
    textOnBrand = Color(0xFF78350F),
    textOnBrandStrong = Color(0xFF0F172A),
    textOnBrandSoft = Color(0xFF8A5700),
    textSuccess = Color(0xFF047857),
    textSuccessOnBrand = Color(0xFF064E3B),
    textInfo = Color(0xFF0369A1),
    textDanger = Color(0xFFB91C1C),
    textOnDanger = Color(0xFFFFFFFF),
    brandPrimary = Color(0xFFF59E0B),
    brandSoft = Color(0xFFFFEDC3),
    brandSubtle = Color(0xFFFFF7E6),
    bgApp = Color(0xFFF8FAFC),
    bgSurface = Color(0xFFFFFFFF),
    bgSurfaceMuted = Color(0xFFF1F5F9),
    bgSurfaceSunken = Color(0xFFEEF2F6),
    bgDanger = Color(0xFFB91C1C),
    bgDangerSoft = Color(0xFFFEE2E2),
    bgSuccessSoft = Color(0xFFD1FAE5),
    bgInfoSoft = Color(0xFFE0E7FF),
    bgToggleOff = Color(0xFFCBD5E1),
    borderDefault = Color(0xFFE8EDF3),
    borderInput = Color(0xFFE2E8F0),
    borderBrand = Color(0xFFFBE3A6),
    borderFocus = Color(0xFFF59E0B),
    iconPrimary = Color(0xFF0F172A),
    iconSecondary = Color(0xFF64748B),
    iconMuted = Color(0xFF94A3B8),
    iconOnBrand = Color(0xFF8A5700),
    isDark = false,
)

val DarkFocusColors = FocusColors(
    textPrimary = Color(0xFFF1F5F9),
    textStrong = Color(0xFFE2E8F0),
    textSecondaryStrong = Color(0xFFCBD5E1),
    textSecondary = Color(0xFF94A3B8),
    textAccent = Color(0xFFFBBF24),
    textAccentStrong = Color(0xFFFCD34D),
    textOnBrand = Color(0xFF78350F),
    textOnBrandStrong = Color(0xFF0F172A),
    textOnBrandSoft = Color(0xFFFCD34D),
    textSuccess = Color(0xFF34D399),
    textSuccessOnBrand = Color(0xFF064E3B),
    textInfo = Color(0xFF38BDF8),
    textDanger = Color(0xFFF87171),
    textOnDanger = Color(0xFFFFFFFF),
    brandPrimary = Color(0xFFF59E0B),
    brandSoft = Color(0xFF3A2A0A),
    brandSubtle = Color(0xFF251C0B),
    bgApp = Color(0xFF0B1220),
    bgSurface = Color(0xFF131C2E),
    bgSurfaceMuted = Color(0xFF1C2638),
    bgSurfaceSunken = Color(0xFF0F1828),
    bgDanger = Color(0xFFDC2626),
    bgDangerSoft = Color(0xFF3B1515),
    bgSuccessSoft = Color(0xFF0B3B2C),
    bgInfoSoft = Color(0xFF1E2550),
    bgToggleOff = Color(0xFF334155),
    borderDefault = Color(0xFF243049),
    borderInput = Color(0xFF2B3850),
    borderBrand = Color(0xFF5C4515),
    borderFocus = Color(0xFFF59E0B),
    iconPrimary = Color(0xFFF1F5F9),
    iconSecondary = Color(0xFF94A3B8),
    iconMuted = Color(0xFF64748B),
    iconOnBrand = Color(0xFFFCD34D),
    isDark = true,
)

package com.focus.app.data

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Datos de ejemplo, los mismos que aparecen en las pantallas de Figma.
 * Cuando conectes la API (focus-api) o el uso real del teléfono, reemplaza este archivo.
 */
enum class FrictionLevel(val number: Int) { One(1), Two(2), Three(3) }

enum class AppState { Activa, ConLimite, Bloqueada, Pausada }

data class TrackedApp(
    val id: String,
    val name: String,
    val letter: String,
    val icon: Brush,
    val detail: String,
    val level: FrictionLevel,
    val state: AppState,
    val enabled: Boolean,
    val usedSlots: Int,
    val totalSlots: Int,
)

object SampleData {
    const val userName = "Dani"
    const val todayLabel = "Martes, 14 de mayo"
    const val streakDays = 12

    val apps = listOf(
        TrackedApp(
            id = "instagram", name = "Instagram", letter = "IG",
            icon = Brush.linearGradient(listOf(Color(0xFF7C3AED), Color(0xFFEC4899), Color(0xFFF59E0B))),
            detail = "Hasta las 18:00", level = FrictionLevel.Two, state = AppState.Bloqueada, enabled = true, usedSlots = 0, totalSlots = 3,
        ),
        TrackedApp(
            id = "tiktok", name = "TikTok", letter = "TT", icon = Brush.linearGradient(listOf(Color(0xFF111827), Color(0xFF111827))),
            detail = "2 de 3 restantes", level = FrictionLevel.Three, state = AppState.ConLimite, enabled = true, usedSlots = 2, totalSlots = 3,
        ),
        TrackedApp(
            id = "clash", name = "Clash Royale", letter = "CR", icon = Brush.linearGradient(listOf(Color(0xFF2563EB), Color(0xFF1D4ED8))),
            detail = "1 de 2 restantes", level = FrictionLevel.One, state = AppState.ConLimite, enabled = true, usedSlots = 1, totalSlots = 2,
        ),
        TrackedApp(
            id = "netflix", name = "Netflix", letter = "N", icon = Brush.linearGradient(listOf(Color(0xFF1E293B), Color(0xFF1E293B))),
            detail = "Hasta las 21:00", level = FrictionLevel.Three, state = AppState.Bloqueada, enabled = true, usedSlots = 0, totalSlots = 1,
        ),
        TrackedApp(
            id = "x", name = "X (Twitter)", letter = "X", icon = Brush.linearGradient(listOf(Color(0xFF64748B), Color(0xFF64748B))),
            detail = "Pausada", level = FrictionLevel.One, state = AppState.Pausada, enabled = false, usedSlots = 0, totalSlots = 0,
        ),
    )

    /** Límite diario por semana del plan (minutos). Semana actual = índice 2 (S3). */
    val weeklyLimits = listOf(270, 240, 210, 195, 180, 165, 150, 150)
    const val currentWeek = 2

    /** Minutos de pantalla por día de esta semana (L–D) y la meta diaria. */
    val dailyMinutes = listOf(150, 128, 230, 110, 140, 245, 96)
    const val dailyGoalMinutes = 210

    fun formatMinutes(min: Int): String {
        val h = min / 60
        val m = min % 60
        return when {
            h == 0 -> "${m}m"
            m == 0 -> "${h}h"
            else -> "${h}h ${m}m"
        }
    }

    /** Formato corto de las barras: "4h 30" en lugar de "4h 30m". */
    fun formatShort(min: Int): String {
        val h = min / 60
        val m = min % 60
        return if (m == 0) "${h}h" else "${h}h $m"
    }
}

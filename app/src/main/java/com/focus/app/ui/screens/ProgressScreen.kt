package com.focus.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingDown
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.focus.app.data.SampleData
import com.focus.app.ui.components.FocusCard
import com.focus.app.ui.components.HeroCard
import com.focus.app.ui.components.IconTile
import com.focus.app.ui.components.Pill
import com.focus.app.ui.components.ScreenHeader
import com.focus.app.ui.components.SectionHeader
import com.focus.app.ui.components.SegmentedControl
import com.focus.app.ui.theme.FocusTheme

/** Rosa de "Pasaste el límite" (igual en ambos modos, como en Figma). */
private val OverLimit = Color(0xFFFECACA)

/** Pantalla 22 · Progreso. */
@Composable
fun ProgressScreen(onOpenMenu: () -> Unit, onOpenAchievements: () -> Unit) {
    val c = FocusTheme.colors
    val t = FocusTheme.type
    var period by rememberSaveable { mutableIntStateOf(0) }

    TabScreen {
        ScreenHeader(eyebrow = "Tu evolución", title = "Progreso") {
            Pill("Mayo 2026", c.brandSoft, c.textOnBrandSoft)
            MenuButton(onOpenMenu)
        }
        SegmentedControl(listOf("Semana", "Mes", "Año"), period, { period = it })

        HeroCard {
            Text("HORAS RECUPERADAS ESTE MES", style = t.overline, color = c.textOnBrandStrong)
            Text("14 h 20 min", style = t.display, color = c.textOnBrandStrong)
            Text("Frente a tu uso inicial de 4h 30m al día", style = t.text, color = c.textOnBrandStrong)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatCard(Icons.Outlined.LocalFireDepartment, "${SampleData.streakDays} días", "Racha actual", c.textAccent, Modifier.weight(1f))
            StatCard(Icons.AutoMirrored.Outlined.TrendingDown, "−34 %", "vs. semana pasada", c.textSuccess, Modifier.weight(1f))
            StatCard(Icons.Outlined.TrackChanges, "86 %", "días en meta", c.textPrimary, Modifier.weight(1f))
        }

        FocusCard {
            SectionHeader("Tiempo de pantalla por día", action = "Meta ${SampleData.formatMinutes(SampleData.dailyGoalMinutes)}")
            DailyBars()
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Legend(c.brandPrimary, "Dentro de la meta")
                Legend(OverLimit, "Pasaste el límite")
            }
        }

        FocusCard(spacing = 10.dp, onClick = onOpenAchievements) {
            SectionHeader("Logros recientes", action = "Ver todos")
            Achievement(Icons.Outlined.EmojiEvents, "Primera semana completa", "Cumpliste tu meta 7 días seguidos")
            Achievement(Icons.Outlined.DarkMode, "Noches tranquilas", "5 noches sin TikTok después de las 23:00")
        }
    }
}

@Composable
private fun StatCard(icon: ImageVector, value: String, label: String, color: Color, modifier: Modifier = Modifier) {
    FocusCard(modifier, padding = PaddingValues(12.dp), spacing = 4.dp) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
        Text(value, style = FocusTheme.type.title3, color = color)
        Text(label, style = FocusTheme.type.text, color = FocusTheme.colors.textSecondary)
    }
}

@Composable
private fun DailyBars() {
    val c = FocusTheme.colors
    val days = listOf("L", "M", "M", "J", "V", "S", "D")
    val max = SampleData.dailyMinutes.max().toFloat()
    Row(Modifier.fillMaxWidth().height(150.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Bottom) {
        SampleData.dailyMinutes.forEachIndexed { i, minutes ->
            val ok = minutes <= SampleData.dailyGoalMinutes
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier.fillMaxWidth().height((120 * minutes / max).dp).clip(RoundedCornerShape(8.dp))
                        .background(if (ok) c.brandPrimary else OverLimit),
                )
                Spacer(Modifier.height(6.dp))
                Text(days[i], style = FocusTheme.type.textSemibold, color = c.textSecondary)
            }
        }
    }
}

@Composable
private fun Legend(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(color))
        Spacer(Modifier.width(6.dp))
        Text(label, style = FocusTheme.type.text, color = FocusTheme.colors.textSecondaryStrong)
    }
}

@Composable
private fun Achievement(icon: ImageVector, title: String, subtitle: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconTile(icon, size = 36.dp)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(title, style = FocusTheme.type.bodyBold, color = FocusTheme.colors.textPrimary)
            Text(subtitle, style = FocusTheme.type.text, color = FocusTheme.colors.textSecondary)
        }
    }
}

@Preview(showBackground = true, heightDp = 1050)
@Composable
private fun ProgressPreview() = FocusTheme(darkTheme = true) { ProgressScreen({}, {}) }

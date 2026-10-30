package com.focus.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.focus.app.data.SampleData
import com.focus.app.ui.components.ButtonType
import com.focus.app.ui.components.FocusButton
import com.focus.app.ui.components.FocusCard
import com.focus.app.ui.components.HeroCard
import com.focus.app.ui.components.LinearMeter
import com.focus.app.ui.components.Pill
import com.focus.app.ui.components.ScreenHeader
import com.focus.app.ui.components.SectionHeader
import com.focus.app.ui.theme.FocusTheme

private data class PlanChange(val app: String, val metric: String, val from: String, val to: String)
private data class AiEvent(val whenText: String, val title: String, val detail: String, val dot: Color)

/** Pantalla 09E · Tu plan. */
@Composable
fun PlanScreen(onOpenMenu: () -> Unit, onAdjustGoal: () -> Unit, onTalkToAi: () -> Unit) {
    val c = FocusTheme.colors
    val t = FocusTheme.type
    TabScreen {
        ScreenHeader(eyebrow = "Plan adaptativo", title = "Tu plan") {
            Pill("Semana 3 de 8", c.brandSoft, c.textOnBrandSoft)
            MenuButton(onOpenMenu)
        }

        HeroCard {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("TU META", style = t.overline, color = c.textOnBrandStrong)
                Pill("Va 50%", Color.White.copy(alpha = 0.8f), c.textOnBrandStrong)
            }
            Text("Bajar a 2h 30m al día", style = t.title2, color = c.textOnBrandStrong)
            Text("Límite actual 3h 30m · te falta 1h por bajar", style = t.text, color = c.textOnBrandStrong)
            LinearMeter(0.5f, track = Color.White.copy(alpha = 0.55f), color = c.textOnBrandStrong)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Inicio · 4h 30m", style = t.textSemibold, color = c.textOnBrandStrong)
                Text("Meta · 2h 30m", style = t.textSemibold, color = c.textSuccessOnBrand)
            }
        }

        FocusCard {
            SectionHeader("Límite diario por semana", action = "8 semanas")
            WeeklyBars()
            Text("Meta alcanzada en la semana 7. La IA puede adelantarla o retrasarla según tu progreso.", style = t.text, color = c.textSecondary)
        }

        FocusCard(spacing = 0.dp) {
            Row(Modifier.fillMaxWidth().padding(bottom = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Esto cambia esta semana", style = t.bodyBold, color = c.textPrimary)
                Pill("3 ajustes", c.brandSoft, c.textOnBrandSoft)
            }
            val changes = listOf(
                PlanChange("Instagram", "Aperturas por día", "3", "2"),
                PlanChange("TikTok", "Tiempo por día", "25 min", "20 min"),
                PlanChange("Clash Royale", "Fricción", "Nivel 1", "Nivel 2"),
            )
            changes.forEachIndexed { i, ch ->
                if (i > 0) Box(Modifier.fillMaxWidth().height(1.dp).background(c.bgSurfaceSunken))
                Row(Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(ch.app, style = t.bodyBold, color = c.textPrimary)
                        Text(ch.metric, style = t.text, color = c.textSecondary)
                    }
                    Text(ch.from, style = t.bodyBold, color = c.textSecondary)
                    Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = "cambia a", tint = c.brandPrimary, modifier = Modifier.padding(horizontal = 6.dp).size(14.dp))
                    Text(ch.to, style = t.bodyBold, color = c.textAccent)
                }
            }
        }

        FocusCard {
            SectionHeader("Lo que ha hecho la IA", action = "Ver todo", actionIsLink = true)
            val events = listOf(
                AiEvent("Hace 2 días", "Bajó el límite de TikTok", "De 25 a 20 min porque cumpliste 5 días seguidos.", Color(0xFF10B981)),
                AiEvent("Hace 4 días", "Subió la fricción de Instagram", "Abriste la app 4 veces de noche en la misma semana.", Color(0xFFF59E0B)),
                AiEvent("Hace 1 semana", "Creó tu plan de 8 semanas", "Basado en tu uso inicial de 4h 30m al día.", Color(0xFF64748B)),
            )
            events.forEach { e ->
                Row {
                    Box(Modifier.padding(top = 4.dp).size(10.dp).clip(CircleShape).background(e.dot))
                    Spacer(Modifier.width(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(e.whenText, style = t.text, color = c.textSecondary)
                        Text(e.title, style = t.bodyBold, color = c.textPrimary)
                        Text(e.detail, style = t.text, color = c.textSecondaryStrong)
                    }
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FocusButton("Ajustar mi meta", onClick = onAdjustGoal, type = ButtonType.Secondary, modifier = Modifier.weight(1f))
            FocusButton("Hablar con la IA", onClick = onTalkToAi, modifier = Modifier.weight(1f))
        }
    }
}

/** Gráfica de barras S1–S8: pasadas en gris, la actual en naranja, las futuras en naranja claro. */
@Composable
private fun WeeklyBars() {
    val c = FocusTheme.colors
    val t = FocusTheme.type
    val max = SampleData.weeklyLimits.max().toFloat()
    Row(Modifier.fillMaxWidth().height(150.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Bottom) {
        SampleData.weeklyLimits.forEachIndexed { i, minutes ->
            val current = i == SampleData.currentWeek
            val color = when {
                i < SampleData.currentWeek -> c.borderInput
                current -> c.brandPrimary
                else -> c.brandSoft
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                Text(SampleData.formatShort(minutes), style = t.text, color = if (current) c.textAccent else c.textSecondary, maxLines = 1)
                Spacer(Modifier.height(4.dp))
                Box(Modifier.fillMaxWidth().height((96 * minutes / max).dp).clip(RoundedCornerShape(8.dp)).background(color))
                Spacer(Modifier.height(6.dp))
                Text("S${i + 1}", style = if (current) t.textBold else t.textSemibold, color = if (current) c.textAccent else c.textSecondary)
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 1200)
@Composable
private fun PlanPreview() = FocusTheme { PlanScreen({}, {}, {}) }

package com.focus.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.focus.app.data.AppState
import com.focus.app.data.SampleData
import com.focus.app.data.TrackedApp
import com.focus.app.ui.components.AppIcon
import com.focus.app.ui.components.FocusCard
import com.focus.app.ui.components.HeroCard
import com.focus.app.ui.components.IconTile
import com.focus.app.ui.components.Pill
import com.focus.app.ui.components.ProgressRing
import com.focus.app.ui.components.RiskGauge
import com.focus.app.ui.components.ScreenHeader
import com.focus.app.ui.components.SectionHeader
import com.focus.app.ui.components.TextLink
import com.focus.app.ui.theme.FocusShapes
import com.focus.app.ui.theme.FocusSpacing
import com.focus.app.ui.theme.FocusTheme
import com.focus.app.ui.theme.HeroGradientEnd
import com.focus.app.ui.theme.HeroGradientStart

/** Marco común de las 4 pestañas: fondo, márgenes, scroll y espacio para la barra flotante. */
@Composable
internal fun TabScreen(content: @Composable ColumnScope.() -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(FocusTheme.colors.bgApp)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(start = FocusSpacing.screen, end = FocusSpacing.screen, top = 11.dp, bottom = 130.dp),
        verticalArrangement = Arrangement.spacedBy(FocusSpacing.section),
        content = content,
    )
}

@Composable
internal fun MenuButton(onClick: () -> Unit) {
    IconButton(onClick = onClick, modifier = Modifier.size(36.dp)) {
        Icon(Icons.Outlined.Menu, contentDescription = "Menú", tint = FocusTheme.colors.iconPrimary)
    }
}

/** Pantalla 09B · Inicio. */
@Composable
fun HomeScreen(
    onOpenMenu: () -> Unit,
    onSeeAllApps: () -> Unit,
    onOpenPlan: () -> Unit,
    onOpenProgress: () -> Unit,
    onStartFocus: () -> Unit,
    onSchedule: () -> Unit,
) {
    val c = FocusTheme.colors
    val t = FocusTheme.type
    TabScreen {
        ScreenHeader(eyebrow = SampleData.todayLabel, title = "Buen día, ${SampleData.userName}") {
            Pill("${SampleData.streakDays} días", c.bgSurface, c.textPrimary, icon = Icons.Outlined.LocalFireDepartment, onClick = onOpenProgress,
                modifier = Modifier.border(1.dp, c.borderDefault, RoundedCornerShape(FocusShapes.small)))
            MenuButton(onOpenMenu)
        }

        // Tiempo de pantalla
        HeroCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ProgressRing(progress = 138f / 210f) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("2h 18m", style = t.title2, color = c.textOnBrandStrong)
                        Text("de 3h 30m", style = t.text, color = c.textOnBrandStrong)
                    }
                }
                Spacer(Modifier.width(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("TIEMPO DE PANTALLA", style = t.overline, color = c.textOnBrandStrong)
                    Text("34% menos", style = t.title2, color = c.textSuccessOnBrand)
                    Text("que el martes pasado", style = t.text, color = c.textOnBrandStrong)
                    Spacer(Modifier.height(4.dp))
                    Pill("Buen progreso", Color.White.copy(alpha = 0.85f), c.textSuccessOnBrand)
                }
            }
        }

        // Modo foco + Compañero IA
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            val shape = RoundedCornerShape(18.dp)
            Row(
                Modifier
                    .weight(1f)
                    .shadow(10.dp, shape, ambientColor = HeroGradientEnd, spotColor = HeroGradientEnd)
                    .clip(shape)
                    .background(Brush.horizontalGradient(listOf(HeroGradientStart, HeroGradientEnd)))
                    .clickable(role = Role.Button, onClick = onStartFocus)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconTile(Icons.Outlined.TrackChanges, size = 36.dp, background = Color.White.copy(alpha = 0.45f), tint = c.textOnBrandStrong)
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text("Iniciar modo foco", style = t.bodyBold, color = c.textOnBrandStrong)
                    Text("25 min · 6 apps silenciadas", style = t.text, color = c.textOnBrandStrong)
                }
                Box(Modifier.size(32.dp).clip(CircleShape).background(c.textOnBrandStrong), contentAlignment = Alignment.Center) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
            }
            FocusCard(Modifier.width(60.dp).height(60.dp), padding = PaddingValues(0.dp)) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(Icons.Outlined.AutoAwesome, contentDescription = "Compañero IA", tint = c.brandPrimary)
                }
            }
        }

        // Tus apps hoy
        SectionHeader("Tus apps hoy", action = "Ver todas", actionIsLink = true, onAction = onSeeAllApps)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SampleData.apps.take(3).forEach { AppTodayCard(it, Modifier.weight(1f)) }
        }

        // Riesgo de recaída
        FocusCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    RiskGauge(value = 62)
                    Text("MEDIO", style = t.overline, color = c.textAccent)
                    Text("62 / 100", style = t.text, color = c.textSecondary)
                }
                Spacer(Modifier.width(14.dp))
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("RIESGO DE RECAÍDA", style = t.overline, color = c.textSecondary)
                    Text("Subió en los últimos 30 min", style = t.bodyBold, color = c.textPrimary)
                    Text("Hora tardía + impulso alto", style = t.text, color = c.textSecondary)
                    TextLink("Ver por qué ›", onClick = onOpenProgress)
                }
            }
        }

        // Sugerencia de la IA
        FocusCard(background = c.brandSubtle, border = c.borderBrand) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconTile(Icons.Outlined.AutoAwesome, size = 38.dp)
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text("ENFOQUE SUGIERE", style = t.overline, color = c.textAccent)
                    Text("Tu punto crítico suele ser a las 16:00. ¿Activamos Modo Foco antes?", style = t.textBold, color = c.textPrimary)
                }
                Spacer(Modifier.width(8.dp))
                Pill("Programar", c.brandPrimary, c.textOnBrandStrong, onClick = onSchedule)
            }
        }

        // Plan adaptativo
        FocusCard(onClick = onOpenPlan) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconTile(Icons.Outlined.BarChart, size = 38.dp)
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text("PLAN ADAPTATIVO", style = t.overline, color = c.textSecondary)
                    Text("−15% esta semana", style = t.bodyBold, color = c.textPrimary)
                    Text("Meta ajustada según tu progreso", style = t.text, color = c.textSecondary)
                }
                Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = null, tint = c.iconSecondary)
            }
        }
    }
}

/** Tarjeta pequeña de app en "Tus apps hoy". */
@Composable
private fun AppTodayCard(app: TrackedApp, modifier: Modifier = Modifier) {
    val c = FocusTheme.colors
    val blocked = app.state == AppState.Bloqueada
    FocusCard(modifier, padding = PaddingValues(10.dp), background = if (blocked) c.bgSurfaceMuted else c.bgSurface, spacing = 6.dp) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
            AppIcon(app.letter, app.icon, size = 32.dp)
            Spacer(Modifier.weight(1f))
            if (blocked) Icon(Icons.Outlined.Lock, contentDescription = "Bloqueada", tint = c.iconOnBrand, modifier = Modifier.size(14.dp))
        }
        Text(app.name, style = FocusTheme.type.textBold, color = c.textPrimary, maxLines = 1)
        UsageDots(app)
        Text(
            app.detail, style = FocusTheme.type.text, maxLines = 1,
            color = if (blocked) c.textAccent else c.textSecondary,
        )
    }
}

/** Puntos de aperturas usadas (naranja = usada). Con texto al lado para no depender solo del color. */
@Composable
internal fun UsageDots(app: TrackedApp) {
    val c = FocusTheme.colors
    if (app.totalSlots == 0) return
    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
        repeat(app.totalSlots) { i ->
            Box(Modifier.size(6.dp).clip(CircleShape).background(if (i < app.usedSlots) c.brandPrimary else c.borderInput))
        }
    }
}

@Preview(showBackground = true, heightDp = 1100)
@Composable
private fun HomePreview() = FocusTheme { HomeScreen({}, {}, {}, {}, {}, {}) }

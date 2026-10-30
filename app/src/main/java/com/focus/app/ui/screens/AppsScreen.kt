package com.focus.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.focus.app.data.AppState
import com.focus.app.data.FrictionLevel
import com.focus.app.data.SampleData
import com.focus.app.data.TrackedApp
import com.focus.app.ui.components.AppIcon
import com.focus.app.ui.components.FocusCard
import com.focus.app.ui.components.FocusChip
import com.focus.app.ui.components.FocusSwitch
import com.focus.app.ui.components.IconTile
import com.focus.app.ui.components.Pill
import com.focus.app.ui.components.ScreenHeader
import com.focus.app.ui.components.SearchField
import com.focus.app.ui.theme.FocusTheme

private val filters = listOf("Todas", "Bloqueadas", "Con límite", "Pausadas")

/** Pantalla 09D · Mis apps. */
@Composable
fun AppsScreen(onOpenMenu: () -> Unit, onAddApp: () -> Unit, onOpenApp: (TrackedApp) -> Unit) {
    val c = FocusTheme.colors
    val t = FocusTheme.type
    val apps = remember { mutableStateListOf(*SampleData.apps.toTypedArray()) }
    var query by rememberSaveable { mutableStateOf("") }
    var filter by rememberSaveable { mutableStateOf(0) }
    var suggestionApplied by rememberSaveable { mutableStateOf(false) }

    val visible = apps.filter { app ->
        app.name.contains(query, ignoreCase = true) && when (filter) {
            1 -> app.state == AppState.Bloqueada
            2 -> app.state == AppState.ConLimite
            3 -> app.state == AppState.Pausada
            else -> true
        }
    }

    TabScreen {
        ScreenHeader(eyebrow = "Vigilancia activa", title = "Mis apps") {
            Pill("Añadir", c.brandSoft, c.textOnBrandSoft, icon = Icons.Outlined.Add, onClick = onAddApp)
            MenuButton(onOpenMenu)
        }
        SearchField(query, { query = it }, placeholder = "Buscar app")
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            filters.forEachIndexed { i, label -> FocusChip(label, selected = i == filter, onClick = { filter = i }) }
        }

        // Resumen
        FocusCard(padding = PaddingValues(vertical = 14.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Stat("${apps.size}", "APPS", c.textPrimary, Modifier.weight(1f))
                Box(Modifier.width(1.dp).height(34.dp).background(c.borderDefault))
                Stat("${apps.count { it.state == AppState.Bloqueada }}", "BLOQUEADAS", c.textPrimary, Modifier.weight(1f))
                Box(Modifier.width(1.dp).height(34.dp).background(c.borderDefault))
                Stat("7", "APERTURAS HOY", c.textAccent, Modifier.weight(1f))
            }
        }

        if (visible.isEmpty()) {
            Text("No hay apps en este filtro.", style = t.body, color = c.textSecondary, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(24.dp))
        }
        visible.forEach { app ->
            AppRow(
                app = app,
                onToggle = { on ->
                    val i = apps.indexOfFirst { it.id == app.id }
                    apps[i] = app.copy(enabled = on, state = if (on) AppState.ConLimite else AppState.Pausada, detail = if (on) "Límite activo" else "Pausada")
                },
                onClick = { onOpenApp(app) },
            )
        }

        if (!suggestionApplied) {
            FocusCard(background = c.brandSubtle, border = c.borderBrand) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconTile(Icons.Outlined.AutoAwesome, size = 38.dp)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("LA IA SUGIERE", style = t.overline, color = c.textAccent)
                        Text("Clash Royale lleva 3 semanas en Nivel 1. ¿Subimos a Nivel 2?", style = t.textBold, color = c.textPrimary)
                    }
                    Spacer(Modifier.width(8.dp))
                    Pill("Aplicar", c.brandPrimary, c.textOnBrandStrong, onClick = {
                        val i = apps.indexOfFirst { it.id == "clash" }
                        if (i >= 0) apps[i] = apps[i].copy(level = FrictionLevel.Two)
                        suggestionApplied = true
                    })
                }
            }
        }
    }
}

@Composable
private fun Stat(value: String, label: String, color: Color, modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = FocusTheme.type.title2, color = color)
        Text(label, style = FocusTheme.type.overline, color = FocusTheme.colors.textSecondary)
    }
}

/** Componente "Fila de app": icono, nombre, detalle, nivel de fricción y toggle. */
@Composable
fun AppRow(app: TrackedApp, onToggle: (Boolean) -> Unit, onClick: () -> Unit) {
    val c = FocusTheme.colors
    val t = FocusTheme.type
    val blocked = app.state == AppState.Bloqueada
    FocusCard(
        padding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
        background = if (blocked) c.bgSurfaceMuted else c.bgSurface,
        onClick = onClick,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AppIcon(app.letter, app.icon, size = 42.dp)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(app.name, style = t.bodyBold, color = if (app.enabled) c.textPrimary else c.textSecondary)
                    if (blocked) {
                        Spacer(Modifier.width(6.dp))
                        Box(Modifier.size(18.dp).background(c.brandSoft, RoundedCornerShape(6.dp)), contentAlignment = Alignment.Center) {
                            Icon(Icons.Outlined.Lock, contentDescription = "Bloqueada", tint = c.iconOnBrand, modifier = Modifier.size(11.dp))
                        }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    UsageDots(app)
                    if (app.totalSlots > 0) Spacer(Modifier.width(6.dp))
                    Text(app.detail, style = if (blocked) t.textBold else t.text, color = if (blocked) c.textAccent else c.textSecondary)
                }
            }
            LevelChip(app.level, enabled = app.enabled)
            Spacer(Modifier.width(10.dp))
            FocusSwitch(app.enabled, onToggle)
        }
    }
}

/** Nivel de fricción: misma familia de color, más intensa a mayor nivel (y el número siempre visible). */
@Composable
fun LevelChip(level: FrictionLevel, enabled: Boolean = true) {
    val c = FocusTheme.colors
    val (bg, fg) = when {
        !enabled -> c.bgSurfaceMuted to c.textSecondary
        level == FrictionLevel.One -> c.brandSoft to c.textOnBrandSoft
        level == FrictionLevel.Two -> c.bgInfoSoft to c.textInfo
        else -> c.bgDangerSoft to c.textDanger
    }
    Text(
        "Nivel ${level.number}",
        style = FocusTheme.type.textBold,
        color = fg,
        modifier = Modifier.background(bg, RoundedCornerShape(8.dp)).border(1.dp, fg.copy(alpha = 0.15f), RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp),
    )
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun AppsPreview() = FocusTheme { AppsScreen({}, {}, {}) }

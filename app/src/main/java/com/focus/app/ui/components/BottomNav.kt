package com.focus.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.focus.app.ui.theme.FocusTheme

/** Las 4 pestañas de la barra inferior. */
enum class MainTab(val route: String, val label: String, val icon: ImageVector) {
    Inicio("inicio", "Inicio", Icons.Outlined.Home),
    Apps("apps", "Apps", Icons.Outlined.GridView),
    Plan("plan", "Plan", Icons.Outlined.AutoAwesome),
    Progreso("progreso", "Progreso", Icons.Outlined.BarChart),
}

/**
 * Componente "Barra de navegación" flotante, con el degradado que desvanece el
 * contenido que pasa por detrás (capa "BottomNav / Fondo degradado" en Figma).
 */
@Composable
fun FloatingBottomNav(current: MainTab, onSelect: (MainTab) -> Unit, modifier: Modifier = Modifier) {
    val c = FocusTheme.colors
    val shape = RoundedCornerShape(24.dp)
    Box(
        modifier
            .fillMaxWidth()
            .background(Brush.verticalGradient(0f to Color.Transparent, 0.45f to c.bgApp.copy(alpha = 0.92f), 1f to c.bgApp))
            .navigationBarsPadding()
            .padding(top = 40.dp, bottom = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            Modifier
                .shadow(18.dp, shape)
                .clip(shape)
                .background(c.bgSurface)
                .border(1.dp, c.borderDefault, shape)
                .padding(8.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            MainTab.entries.forEach { tab ->
                val selected = tab == current
                val bg by animateColorAsState(if (selected) c.brandSoft else Color.Transparent, label = "tabBg")
                Column(
                    Modifier
                        .width(56.dp)
                        .height(52.dp)
                        .clip(RoundedCornerShape(17.dp))
                        .background(bg)
                        .selectable(selected = selected, role = Role.Tab, onClick = { onSelect(tab) }),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(tab.icon, contentDescription = null, tint = if (selected) c.iconOnBrand else c.iconMuted, modifier = Modifier.size(22.dp))
                    Text(
                        tab.label,
                        style = FocusTheme.type.textSemibold,
                        color = if (selected) c.textOnBrandSoft else c.textSecondary,
                        modifier = Modifier.padding(top = 2.dp),
                    )
                }
            }
        }
    }
}

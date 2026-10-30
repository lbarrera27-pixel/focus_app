package com.focus.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.focus.app.ui.theme.FocusShapes
import com.focus.app.ui.theme.FocusSpacing
import com.focus.app.ui.theme.FocusTheme
import com.focus.app.ui.theme.HeroGradientEnd
import com.focus.app.ui.theme.HeroGradientStart
import kotlin.math.cos
import kotlin.math.sin

/** Componente "Tarjeta": fondo de superficie, borde y radio 17. */
@Composable
fun FocusCard(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(FocusSpacing.card),
    background: Color = FocusTheme.colors.bgSurface,
    border: Color = FocusTheme.colors.borderDefault,
    onClick: (() -> Unit)? = null,
    spacing: Dp = 12.dp,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = RoundedCornerShape(FocusShapes.card)
    Column(
        modifier
            .fillMaxWidth()
            .clip(shape)
            .background(background)
            .border(1.dp, border, shape)
            .then(if (onClick != null) Modifier.clickable(role = Role.Button, onClick = onClick) else Modifier)
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(spacing),
        content = content,
    )
}

/** Tarjeta naranja con degradado (Tiempo de pantalla, Tu meta, Horas recuperadas). */
@Composable
fun HeroCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val shape = RoundedCornerShape(FocusShapes.hero)
    Column(
        modifier
            .fillMaxWidth()
            .shadow(16.dp, shape, ambientColor = HeroGradientEnd, spotColor = HeroGradientEnd)
            .clip(shape)
            .background(Brush.linearGradient(listOf(HeroGradientStart, HeroGradientEnd)))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = content,
    )
}

/** Encabezado de una tarjeta: título a la izquierda, acción o dato a la derecha. */
@Composable
fun SectionHeader(title: String, modifier: Modifier = Modifier, action: String? = null, actionIsLink: Boolean = false, onAction: () -> Unit = {}) {
    val c = FocusTheme.colors
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, style = FocusTheme.type.bodyBold, color = c.textPrimary)
        if (action != null) {
            if (actionIsLink) TextLink(action, onClick = onAction)
            else Text(action, style = FocusTheme.type.textSemibold, color = c.textSecondary)
        }
    }
}

/** Encabezado de pestaña: sobretítulo naranja + título grande + acciones a la derecha. */
@Composable
fun ScreenHeader(eyebrow: String, title: String, modifier: Modifier = Modifier, actions: @Composable RowScope.() -> Unit = {}) {
    val c = FocusTheme.colors
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(eyebrow.uppercase(), style = FocusTheme.type.overline, color = c.textAccent)
            Text(title, style = FocusTheme.type.title1, color = c.textPrimary, modifier = Modifier.padding(top = 6.dp))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically, content = actions)
    }
}

/** Chip pequeño de estado (ej. "Semana 3 de 8", "12 días", "Nivel 2"). */
@Composable
fun Pill(text: String, background: Color, color: Color, modifier: Modifier = Modifier, icon: ImageVector? = null, onClick: (() -> Unit)? = null) {
    Row(
        modifier
            .clip(RoundedCornerShape(FocusShapes.small))
            .background(background)
            .then(if (onClick != null) Modifier.clickable(role = Role.Button, onClick = onClick) else Modifier)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        if (icon != null) Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(13.dp))
        Text(text, style = FocusTheme.type.textBold, color = color)
    }
}

/** Cuadro de ícono de color (los iconos de las tarjetas y logros). */
@Composable
fun IconTile(icon: ImageVector, modifier: Modifier = Modifier, size: Dp = 40.dp, background: Color = FocusTheme.colors.brandSoft, tint: Color = FocusTheme.colors.iconOnBrand) {
    Box(modifier.size(size).clip(RoundedCornerShape(size * 0.3f)).background(background), contentAlignment = Alignment.Center) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(size * 0.45f))
    }
}

/** Ícono de app (letra sobre color o degradado). */
@Composable
fun AppIcon(letter: String, brush: Brush, modifier: Modifier = Modifier, size: Dp = 38.dp) {
    Box(modifier.size(size).clip(RoundedCornerShape(size * 0.29f)).background(brush), contentAlignment = Alignment.Center) {
        Text(letter, style = FocusTheme.type.bodyBold, color = Color.White)
    }
}

/** Anillo de progreso (tiempo de pantalla). */
@Composable
fun ProgressRing(
    progress: Float,
    modifier: Modifier = Modifier,
    size: Dp = 118.dp,
    stroke: Dp = 12.dp,
    track: Color = Color.White.copy(alpha = 0.55f),
    color: Color = FocusTheme.colors.textOnBrandStrong,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxWidth().fillMaxHeight()) {
            val s = stroke.toPx()
            val arc = Size(this.size.width - s, this.size.height - s)
            val tl = Offset(s / 2, s / 2)
            drawArc(track, 0f, 360f, false, tl, arc, style = Stroke(s))
            drawArc(color, -90f, 360f * progress.coerceIn(0f, 1f), false, tl, arc, style = Stroke(s, cap = StrokeCap.Round))
        }
        content()
    }
}

/** Barra de progreso horizontal. */
@Composable
fun LinearMeter(progress: Float, modifier: Modifier = Modifier, height: Dp = 8.dp, track: Color = FocusTheme.colors.bgSurfaceMuted, color: Color = FocusTheme.colors.brandPrimary) {
    Box(modifier.fillMaxWidth().height(height).clip(CircleShape).background(track)) {
        Box(Modifier.fillMaxWidth(progress.coerceIn(0f, 1f)).fillMaxHeight().clip(CircleShape).background(color))
    }
}

/** Medidor semicircular verde–naranja–rojo (Riesgo de recaída). value: 0..100 */
@Composable
fun RiskGauge(value: Int, modifier: Modifier = Modifier, size: Dp = 112.dp) {
    val needle = FocusTheme.colors.textPrimary
    Canvas(modifier.size(width = size, height = size * 0.62f)) {
        val s = 11.dp.toPx()
        val d = this.size.width - s
        val tl = Offset(s / 2, s / 2)
        val arc = Size(d, d)
        drawArc(Color(0xFF10B981), 180f, 58f, false, tl, arc, style = Stroke(s))
        drawArc(Color(0xFFF59E0B), 241f, 58f, false, tl, arc, style = Stroke(s))
        drawArc(Color(0xFFEF4444), 302f, 58f, false, tl, arc, style = Stroke(s))
        val center = Offset(this.size.width / 2, s / 2 + d / 2)
        val angle = Math.toRadians(180.0 + 180.0 * value.coerceIn(0, 100) / 100.0)
        val len = d / 2 - s * 1.4f
        val tip = Offset(center.x + (len * cos(angle)).toFloat(), center.y + (len * sin(angle)).toFloat())
        drawLine(needle, center, tip, strokeWidth = 3.dp.toPx(), cap = StrokeCap.Round)
        drawCircle(needle, radius = 5.dp.toPx(), center = center)
    }
}

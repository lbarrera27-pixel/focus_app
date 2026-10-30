package com.focus.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.focus.app.ui.theme.FocusShapes
import com.focus.app.ui.theme.FocusTheme

/** Variantes del componente "Button" de Figma. */
enum class ButtonType { Primary, Secondary, Ghost, Danger }

/** Componente "Button" (Tipo = Primario / Secundario / Fantasma / Peligro). */
@Composable
fun FocusButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.Primary,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val c = FocusTheme.colors
    val shape = RoundedCornerShape(FocusShapes.button)
    val (bg, fg) = when (type) {
        ButtonType.Primary -> c.brandPrimary to c.textOnBrandStrong
        ButtonType.Secondary -> c.bgSurface to c.textPrimary
        ButtonType.Ghost -> Color.Transparent to c.textAccent
        ButtonType.Danger -> c.bgDanger to c.textOnDanger
    }
    Row(
        modifier = modifier
            .then(if (type == ButtonType.Primary && enabled) Modifier.shadow(10.dp, shape, ambientColor = c.brandPrimary, spotColor = c.brandPrimary) else Modifier)
            .clip(shape)
            .background(bg)
            .then(if (type == ButtonType.Secondary) Modifier.border(1.dp, c.borderInput, shape) else Modifier)
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .alpha(if (enabled) 1f else 0.45f)
            .padding(horizontal = 16.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, tint = fg, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, style = FocusTheme.type.bodyBold, color = fg)
    }
}

/** Componente "Botón grande" del onboarding: 56 dp, texto a la izquierda y flecha opcional. */
@Composable
fun BigButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    showArrow: Boolean = true,
) {
    val c = FocusTheme.colors
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(12.dp, shape, ambientColor = c.brandPrimary, spotColor = c.brandPrimary)
            .clip(shape)
            .background(c.brandPrimary)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (showArrow) Arrangement.SpaceBetween else Arrangement.Center,
    ) {
        Text(text, style = FocusTheme.type.bodyBold.copy(fontSize = 15.sp), color = c.textOnBrandStrong)
        if (showArrow) {
            Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, contentDescription = null, tint = c.textOnBrandStrong)
        }
    }
}

/** Componente "Botón atrás" (38 × 38). */
@Composable
fun BackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val c = FocusTheme.colors
    val shape = RoundedCornerShape(FocusShapes.small)
    Box(
        modifier = modifier
            .size(38.dp)
            .clip(shape)
            .background(c.bgSurface)
            .border(1.dp, c.borderDefault, shape)
            .clickable(role = Role.Button, onClickLabel = "Regresar", onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(Icons.AutoMirrored.Outlined.KeyboardArrowLeft, contentDescription = "Regresar", tint = c.iconPrimary)
    }
}

/** Componente "Botón icono" (40 × 40), p. ej. los botones − / + de la meta. */
@Composable
fun IconSquareButton(icon: ImageVector, contentDescription: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val c = FocusTheme.colors
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(FocusShapes.small))
            .background(c.bgSurfaceMuted)
            .clickable(role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = contentDescription, tint = c.iconPrimary, modifier = Modifier.size(18.dp))
    }
}

/** Componente "Botón social" (Google, Apple, Facebook). */
@Composable
fun SocialButton(provider: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val c = FocusTheme.colors
    val shape = RoundedCornerShape(FocusShapes.input)
    val (mark, markColor) = when (provider) {
        "Google" -> "G" to Color(0xFF4285F4)
        "Apple" -> "" to c.textPrimary
        else -> "f" to Color(0xFF1877F2)
    }
    Row(
        modifier = modifier
            .height(46.dp)
            .clip(shape)
            .background(c.bgSurface)
            .border(1.dp, c.borderInput, shape)
            .clickable(role = Role.Button, onClickLabel = "Continuar con $provider", onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(mark, color = markColor, fontWeight = FontWeight.Black, fontSize = 15.sp)
        Spacer(Modifier.width(6.dp))
        Text(provider, style = FocusTheme.type.bodyBold, color = c.textPrimary)
    }
}

/** Enlace de texto (ej. "Ver todas", "¿Olvidaste tu contraseña?"). */
@Composable
fun TextLink(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, color: Color = FocusTheme.colors.textAccent) {
    Text(
        text = text,
        style = FocusTheme.type.textBold,
        color = color,
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 2.dp, vertical = 4.dp),
    )
}

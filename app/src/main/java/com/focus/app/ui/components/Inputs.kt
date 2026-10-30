package com.focus.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.focus.app.ui.theme.FocusShapes
import com.focus.app.ui.theme.FocusTheme

/** Componente "Campo de texto" (Estado = Normal / Enfocado). */
@Composable
fun FocusTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onTogglePassword: (() -> Unit)? = null,
    hint: String? = null,
) {
    val c = FocusTheme.colors
    val t = FocusTheme.type
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    val borderColor by animateColorAsState(if (focused) c.borderFocus else c.borderInput, label = "border")
    val borderWidth by animateDpAsState(if (focused) 1.5.dp else 1.dp, label = "borderWidth")
    val shape = RoundedCornerShape(FocusShapes.input)

    Column(modifier, verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Text(label, style = t.textSemibold, color = c.textStrong)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = t.body.copy(color = c.textPrimary),
            cursorBrush = SolidColor(c.brandPrimary),
            interactionSource = interaction,
            keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType),
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { inner ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(shape)
                        .background(c.bgSurface)
                        .border(borderWidth, borderColor, shape)
                        .padding(horizontal = 16.dp, vertical = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) Text(placeholder, style = t.body, color = c.textSecondary)
                        inner()
                    }
                    if (isPassword && onTogglePassword != null) {
                        TextLink(if (passwordVisible) "Ocultar" else "Mostrar", onClick = onTogglePassword)
                    }
                }
            },
        )
        if (hint != null) Text(hint, style = t.text, color = c.textSecondary)
    }
}

/** Buscador redondeado ("Buscar app"). */
@Composable
fun SearchField(value: String, onValueChange: (String) -> Unit, placeholder: String, modifier: Modifier = Modifier) {
    val c = FocusTheme.colors
    val shape = RoundedCornerShape(FocusShapes.input)
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = FocusTheme.type.body.copy(color = c.textPrimary),
        cursorBrush = SolidColor(c.brandPrimary),
        modifier = modifier.fillMaxWidth(),
        decorationBox = { inner ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(c.bgSurface)
                    .border(1.dp, c.borderInput, shape)
                    .padding(horizontal = 14.dp, vertical = 13.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(Icons.Outlined.Search, contentDescription = null, tint = c.iconSecondary, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(10.dp))
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) Text(placeholder, style = FocusTheme.type.body, color = c.textSecondary)
                    inner()
                }
            }
        },
    )
}

/** Componente "Toggle" (On / Off), 42 × 24 como en Figma. */
@Composable
fun FocusSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier, contentDescription: String? = null) {
    val c = FocusTheme.colors
    val track by animateColorAsState(if (checked) c.brandPrimary else c.bgToggleOff, label = "track")
    val knobX by animateDpAsState(if (checked) 18.dp else 0.dp, label = "knob")
    Box(
        modifier
            .size(width = 42.dp, height = 24.dp)
            .clip(CircleShape)
            .background(track)
            .toggleable(value = checked, role = Role.Switch, onValueChange = onCheckedChange)
            .padding(3.dp),
    ) {
        Box(
            Modifier
                .offset(x = knobX)
                .size(18.dp)
                .shadow(2.dp, CircleShape)
                .background(Color.White, CircleShape),
        )
    }
}

/** Componente "Chip" (Activo / Inactivo). */
@Composable
fun FocusChip(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val c = FocusTheme.colors
    val shape = RoundedCornerShape(FocusShapes.small)
    Text(
        text = text,
        style = if (selected) FocusTheme.type.textBold else FocusTheme.type.textSemibold,
        color = if (selected) c.textOnBrandSoft else c.textSecondaryStrong,
        modifier = modifier
            .clip(shape)
            .background(if (selected) c.brandSoft else c.bgSurface)
            .border(1.dp, if (selected) c.borderBrand else c.borderInput, shape)
            .selectable(selected = selected, role = Role.Tab, onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 9.dp),
    )
}

/** Control segmentado (ej. Semana / Mes / Año, Iniciar sesión / Crear cuenta). */
@Composable
fun SegmentedControl(options: List<String>, selectedIndex: Int, onSelect: (Int) -> Unit, modifier: Modifier = Modifier) {
    val c = FocusTheme.colors
    Row(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(13.dp))
            .background(c.bgSurfaceSunken)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        options.forEachIndexed { i, label ->
            val on = i == selectedIndex
            Box(
                Modifier
                    .weight(1f)
                    .then(if (on) Modifier.shadow(2.dp, RoundedCornerShape(10.dp)) else Modifier)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (on) c.bgSurface else Color.Transparent)
                    .selectable(selected = on, role = Role.Tab, onClick = { onSelect(i) })
                    .padding(vertical = 9.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(label, style = if (on) FocusTheme.type.textBold else FocusTheme.type.textSemibold, color = if (on) c.textPrimary else c.textSecondary)
            }
        }
    }
}

/** Casilla de verificación con texto. */
@Composable
fun FocusCheckbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit, label: String, modifier: Modifier = Modifier) {
    val c = FocusTheme.colors
    Row(
        modifier
            .clip(RoundedCornerShape(8.dp))
            .toggleable(value = checked, role = Role.Checkbox, onValueChange = onCheckedChange)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (checked) c.brandPrimary else c.bgSurface)
                .border(1.5.dp, if (checked) c.brandPrimary else c.borderInput, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) Icon(Icons.Outlined.Check, contentDescription = null, tint = c.textOnBrandStrong, modifier = Modifier.size(14.dp))
        }
        Spacer(Modifier.width(10.dp))
        Text(label, style = FocusTheme.type.text, color = c.textStrong)
    }
}

/** Línea divisoria con texto en medio ("o continúa con"). */
@Composable
fun DividerWithText(text: String, modifier: Modifier = Modifier) {
    val c = FocusTheme.colors
    Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.weight(1f).height(1.dp).background(c.borderInput))
        Text(text, style = FocusTheme.type.text, color = c.textSecondary, modifier = Modifier.padding(horizontal = 10.dp))
        Box(Modifier.weight(1f).height(1.dp).background(c.borderInput))
    }
}

/** Círculo de selección (radio) con check. */
@Composable
fun RadioDot(selected: Boolean, modifier: Modifier = Modifier) {
    val c = FocusTheme.colors
    Box(
        modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(if (selected) c.brandPrimary else c.bgSurface)
            .border(1.5.dp, if (selected) c.brandPrimary else c.borderInput, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        if (selected) Icon(Icons.Outlined.Check, contentDescription = null, tint = c.textOnBrandStrong, modifier = Modifier.size(13.dp))
    }
}

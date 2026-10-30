package com.focus.app.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.focus.app.ui.components.BackButton
import com.focus.app.ui.components.DividerWithText
import com.focus.app.ui.components.FocusButton
import com.focus.app.ui.components.FocusCheckbox
import com.focus.app.ui.components.FocusTextField
import com.focus.app.ui.components.SegmentedControl
import com.focus.app.ui.components.SocialButton
import com.focus.app.ui.components.TextLink
import com.focus.app.ui.theme.FocusSpacing
import com.focus.app.ui.theme.FocusTheme

/** Marco común de las pantallas sin barra inferior: fondo, márgenes y scroll. */
@Composable
internal fun FormScreen(onBack: (() -> Unit)?, rightLink: String?, onRightLink: () -> Unit = {}, content: @Composable () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(FocusTheme.colors.bgApp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = FocusSpacing.screen, vertical = 11.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            if (onBack != null) BackButton(onBack) else Spacer(Modifier.size(38.dp))
            Spacer(Modifier.weight(1f))
            if (rightLink != null) TextLink(rightLink, onClick = onRightLink, color = FocusTheme.colors.textSecondary)
        }
        content()
    }
}

/** Logo de FOCUS! dibujado en código (anillo + punto + órbita naranja). */
@Composable
fun FocusLogoMark(size: Dp = 80.dp) {
    val ink = FocusTheme.colors.textPrimary
    val orange = FocusTheme.colors.brandPrimary
    Canvas(Modifier.size(size)) {
        val r = this.size.minDimension / 2
        drawCircle(orange.copy(alpha = 0.35f), radius = r, style = Stroke(1.dp.toPx()))
        drawCircle(ink, radius = r * 0.62f, style = Stroke(r * 0.22f))
        drawCircle(ink, radius = r * 0.12f)
        drawCircle(orange, radius = r * 0.09f, center = Offset(center.x + r * 0.72f, center.y - r * 0.68f))
    }
}

@Composable
fun FocusWordmark() {
    Text(
        buildAnnotatedString {
            append("FOCUS")
            withStyle(SpanStyle(color = FocusTheme.colors.brandPrimary)) { append("!") }
        },
        fontSize = 34.sp,
        fontWeight = FontWeight.Black,
        letterSpacing = (-1).sp,
        color = FocusTheme.colors.textPrimary,
    )
}

/** Pantalla 17 · Inicio de sesión. */
@Composable
fun LoginScreen(onBack: (() -> Unit)?, onLogin: () -> Unit, onCreateAccount: () -> Unit, onForgotPassword: () -> Unit) {
    val c = FocusTheme.colors
    var email by rememberSaveable { mutableStateOf("dani@correo.com") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }

    FormScreen(onBack = onBack, rightLink = "Ayuda") {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            FocusLogoMark()
            FocusWordmark()
            Text("Bienvenido de vuelta", style = FocusTheme.type.title1, color = c.textPrimary)
            Text("Entra para retomar tu plan donde lo dejaste.", style = FocusTheme.type.body, color = c.textSecondary, textAlign = TextAlign.Center)
        }
        SegmentedControl(listOf("Iniciar sesión", "Crear cuenta"), selectedIndex = 0, onSelect = { if (it == 1) onCreateAccount() })
        FocusTextField("Correo", email, { email = it }, placeholder = "tu@correo.com", keyboardType = KeyboardType.Email)
        FocusTextField(
            "Contraseña", password, { password = it }, placeholder = "Tu contraseña",
            isPassword = true, passwordVisible = showPassword, onTogglePassword = { showPassword = !showPassword },
        )
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) { TextLink("¿Olvidaste tu contraseña?", onClick = onForgotPassword) }
        DividerWithText("o continúa con")
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Google", "Apple", "Facebook").forEach { SocialButton(it, onClick = onLogin, modifier = Modifier.weight(1f)) }
        }
        Text(
            "Al continuar aceptas los Términos y la Política de privacidad.",
            style = FocusTheme.type.text, color = c.textSecondary, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),
        )
        FocusButton("Iniciar sesión", onClick = onLogin, modifier = Modifier.fillMaxWidth(), enabled = email.isNotBlank() && password.isNotBlank())
    }
}

/** Pantalla 23 · Crear cuenta. */
@Composable
fun SignUpScreen(onBack: () -> Unit, onCreated: () -> Unit, onGoToLogin: () -> Unit) {
    val c = FocusTheme.colors
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var accepted by rememberSaveable { mutableStateOf(false) }
    val passwordOk = password.length >= 8 && password.any { it.isDigit() }
    val canSubmit = name.isNotBlank() && email.contains('@') && passwordOk && accepted

    FormScreen(onBack = onBack, rightLink = "Ayuda") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 10.dp)) {
            Text("Crea tu cuenta", style = FocusTheme.type.title1, color = c.textPrimary)
            Text("Empieza tu plan en menos de 2 minutos.", style = FocusTheme.type.body, color = c.textSecondary)
        }
        FocusTextField("Nombre", name, { name = it }, placeholder = "Tu nombre")
        FocusTextField("Correo", email, { email = it }, placeholder = "tu@correo.com", keyboardType = KeyboardType.Email)
        FocusTextField(
            "Contraseña", password, { password = it }, placeholder = "Mínimo 8 caracteres",
            isPassword = true, passwordVisible = showPassword, onTogglePassword = { showPassword = !showPassword },
            hint = "Mínimo 8 caracteres e incluye un número.",
        )
        FocusCheckbox(accepted, { accepted = it }, "Acepto los Términos y la Política de privacidad")
        FocusButton("Crear cuenta", onClick = onCreated, modifier = Modifier.fillMaxWidth(), enabled = canSubmit)
        DividerWithText("o regístrate con")
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Google", "Apple", "Facebook").forEach { SocialButton(it, onClick = onCreated, modifier = Modifier.weight(1f)) }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text("¿Ya tienes cuenta? ", style = FocusTheme.type.text, color = c.textSecondary)
            TextLink("Inicia sesión", onClick = onGoToLogin)
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Preview(showBackground = true, heightDp = 860)
@Composable
private fun LoginPreview() = FocusTheme { LoginScreen(onBack = {}, onLogin = {}, onCreateAccount = {}, onForgotPassword = {}) }

@Preview(showBackground = true, heightDp = 860)
@Composable
private fun SignUpPreview() = FocusTheme(darkTheme = true) { SignUpScreen(onBack = {}, onCreated = {}, onGoToLogin = {}) }

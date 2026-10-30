package com.focus.app.ui.components

import android.provider.Settings
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.focus.app.ui.theme.FocusTheme
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/** Cómo se siente Foqui; cada estado tiene su cara y su animación. */
enum class FoquiEstado {
    /** Inicio y saludos. */
    Normal,
    /** Algo nuevo que ver. */
    Atenta,
    /** Sesión de Modo Foco activa. */
    Enfocado,
    /** Meta semanal cumplida. */
    Feliz,
    /** Límite de uso superado. */
    Cansado,
}

/**
 * Foqui, la mascota de FOCUS!: la diana del logo con un solo ojo y su órbita naranja.
 * Se dibuja con Canvas, así se ve nítido a cualquier tamaño, y se anima solo en un loop
 * de 3 s. Si el teléfono tiene las animaciones desactivadas, se queda quieto.
 *
 * Uso: `Foqui(FoquiEstado.Enfocado, Modifier.width(160.dp))`
 */
@Composable
fun Foqui(
    estado: FoquiEstado,
    modifier: Modifier = Modifier,
    animado: Boolean = true,
) {
    val oscuro = FocusTheme.colors.isDark
    val context = LocalContext.current
    val sinAnimaciones = remember {
        Settings.Global.getFloat(context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f) == 0f
    }
    val tiempo = rememberInfiniteTransition(label = "foqui").animateFloat(
        initialValue = 0f,
        targetValue = LOOP,
        animationSpec = infiniteRepeatable(tween((LOOP * 1000).toInt(), easing = LinearEasing)),
        label = "tiempo",
    )

    // Se dibuja en las coordenadas del SVG original (200 x 204) con margen para saltos y la cinta
    Canvas(
        modifier
            .aspectRatio(ANCHO / ALTO)
            .semantics { contentDescription = "Foqui, la mascota de Focus" },
    ) {
        // Leer el tiempo aquí solo redibuja, no recompone
        val t = if (animado && !sinAnimaciones) tiempo.value else 0f
        val s = size.width / ANCHO
        scale(s, s, pivot = Offset.Zero) {
            translate(-MIN_X, -MIN_Y) { dibujarFoqui(estado, t, oscuro) }
        }
    }
}

private const val LOOP = 3f
private const val MIN_X = -20f
private const val MIN_Y = -40f
private const val ANCHO = 240f
private const val ALTO = 256f

private val Navy = Color(0xFF101A33)
private val Naranja = Color(0xFFF7941D)
private val Blanco = Color.White
private val Contorno = Color(0xFFE8ECF5)

private fun trazo(ancho: Float) = Stroke(width = ancho, cap = StrokeCap.Round, join = StrokeJoin.Round)
private fun ruta(d: String) = PathParser().parsePathString(d).toPath()

private val sonrisa = ruta("M90 140 Q100 148 110 140")
private val ojoFeliz = ruta("M68 104 Q100 64 132 104")
private val bocaFeliz = ruta("M84 132 Q100 156 116 132 Z")
private val cinta = ruta("M67 48 Q100 58 133 48 L152 62 Q100 76 48 62 Z")
private val colaArriba = ruta("M150 62 Q168 50 186 52 Q175 61 156 68 Z")
private val colaAbajo = ruta("M152 68 Q170 76 182 92 Q165 85 150 72 Z")
private val bocaDecidida = ruta("M90 142 Q101 148 111 140")
private val parpadoCansado = ruta("M62 94 A38 38 0 0 1 138 94 Z")
private val bocaCansada = ruta("M86 142 Q93 136 100 142 Q107 148 114 142")

private fun suave(x: Float) = x * x * (3 - 2 * x)
private fun onda(t: Float, periodo: Float) = sin(2 * PI.toFloat() * t / periodo)

/** Interpola una lista de [offset, ...valores] en p (0..1). */
private fun claves(k: List<FloatArray>, p: Float): FloatArray {
    for (i in 1 until k.size) {
        if (p <= k[i][0]) {
            val a = k[i - 1]
            val b = k[i]
            val u = suave((p - a[0]) / (b[0] - a[0]))
            return FloatArray(a.size - 1) { j -> a[j + 1] + (b[j + 1] - a[j + 1]) * u }
        }
    }
    return k.last().copyOfRange(1, k.last().size)
}

// Salto de Feliz: anticipación, estirar en el aire y aplastar al caer
private const val SALTO_INI = 1.7f
private const val SALTO_DUR = 0.9f
private val saltoClaves = listOf(
    floatArrayOf(0f, 0f, 1f, 1f),
    floatArrayOf(0.18f, 0f, 1.16f, 0.82f),
    floatArrayOf(0.5f, -40f, 0.88f, 1.14f),
    floatArrayOf(0.8f, 0f, 1.2f, 0.8f),
    floatArrayOf(1f, 0f, 1f, 1f),
)

private fun DrawScope.dibujarFoqui(estado: FoquiEstado, t: Float, oscuro: Boolean) {
    var dy = 0f
    var sx = 1f
    var sy = 1f
    var sombra = 1f
    var antena = 0f

    when (estado) {
        FoquiEstado.Feliz -> {
            val p = (t - SALTO_INI) / SALTO_DUR
            if (p in 0f..1f) {
                val (y, a, b) = claves(saltoClaves, p)
                dy = y; sx = a; sy = b
                sombra = claves(listOf(floatArrayOf(0f, 1f), floatArrayOf(0.5f, 0.6f), floatArrayOf(1f, 1f)), p)[0]
            }
            // La antena sigue el movimiento con retraso
            val pa = (t - SALTO_INI - 0.12f) / SALTO_DUR
            if (pa in 0f..1f) {
                antena = claves(
                    listOf(
                        floatArrayOf(0f, 0f), floatArrayOf(0.55f, 14f), floatArrayOf(0.75f, -10f),
                        floatArrayOf(0.9f, 5f), floatArrayOf(1f, 0f),
                    ),
                    pa,
                )[0]
            }
        }
        FoquiEstado.Atenta -> dy = -8f * abs(onda(t, 1f))
        else -> Unit
    }

    // Respiración: lenta cuando está enfocado o cansado
    val periodo = if (estado == FoquiEstado.Enfocado || estado == FoquiEstado.Cansado) 3f else 1.5f
    val r = 0.016f * onda(t, periodo)
    sx *= 1 + r
    sy *= 1 - r

    drawOval(
        Navy.copy(alpha = 0.12f),
        topLeft = Offset(100f - 62f * sombra, 200f - 7f * sombra),
        size = Size(124f * sombra, 14f * sombra),
    )

    withTransform({
        translate(0f, dy)
        scale(sx, sy, pivot = Offset(100f, 197f))
    }) {
        orbita(t, delante = false)

        // Pies
        for (x in listOf(72f, 128f)) {
            drawOval(Navy, Offset(x - 18f, 179f), Size(36f, 18f))
            if (oscuro) drawOval(Contorno, Offset(x - 18f, 179f), Size(36f, 18f), style = Stroke(3f))
        }

        // Antena
        rotate(antena, pivot = Offset(100f, 42f)) {
            drawLine(if (oscuro) Contorno else Navy, Offset(100f, 42f), Offset(100f, 24f), 5f, StrokeCap.Round)
            drawCircle(Naranja, 8f, Offset(100f, 17f))
        }
        if (estado == FoquiEstado.Enfocado) {
            // Pulso de "modo foco encendido" cada 1.5 s
            val pulso = (t % 1.5f) / 1.5f
            drawCircle(Naranja.copy(alpha = 0.8f * (1 - pulso)), 8f + 16f * pulso, Offset(100f, 17f), style = Stroke(3f))
        }

        // Cuerpo
        drawCircle(Navy, 72f, Offset(100f, 112f))
        if (oscuro) drawCircle(Contorno, 72f, Offset(100f, 112f), style = Stroke(3f))
        rotate(-35f, pivot = Offset(62f, 62f)) {
            drawOval(Blanco.copy(alpha = 0.18f), Offset(48f, 55f), Size(28f, 14f))
        }

        cara(estado, t)
        orbita(t, delante = true)
    }
}

/** Mitad de atrás o de enfrente de la órbita, con sus dos bolitas dando vueltas. */
private fun DrawScope.orbita(t: Float, delante: Boolean) {
    withTransform({
        translate(100f, 140f)
        rotate(-18f, pivot = Offset.Zero)
    }) {
        val esquina = Offset(-92f, -26f)
        val tam = Size(184f, 52f)
        if (delante) {
            drawArc(Naranja, 0f, 180f, useCenter = false, topLeft = esquina, size = tam, style = Stroke(5f))
        } else {
            drawOval(Naranja, esquina, tam, style = Stroke(5f))
        }
        // Empieza donde las pone el logo y da una vuelta por loop
        val giro = 5 * PI.toFloat() / 6 + 2 * PI.toFloat() * t / LOOP
        for (i in 0..1) {
            val a = giro + i * PI.toFloat()
            val y = 26f * sin(a)
            if ((y > 0) == delante) drawCircle(Naranja, 8f, Offset(92f * cos(a), y))
        }
    }
}

private fun DrawScope.mejillas(alfa: Float = 0.7f) {
    for (x in listOf(54f, 146f)) drawOval(Naranja.copy(alpha = alfa), Offset(x - 8f, 123.5f), Size(16f, 9f))
}

/** Escala vertical del ojo para parpadear entre [inicio] e [inicio] + 0.18 s. */
private fun parpadeo(t: Float, inicio: Float): Float {
    val p = t - inicio
    return if (p in 0f..0.18f) 1 - 0.9f * sin(PI.toFloat() * p / 0.18f) else 1f
}

private fun DrawScope.cara(estado: FoquiEstado, t: Float) {
    when (estado) {
        FoquiEstado.Feliz -> {
            drawPath(ojoFeliz, Blanco, style = trazo(10f))
            mejillas(0.85f)
            drawPath(bocaFeliz, Blanco)
            drawPath(bocaFeliz, Blanco, style = trazo(3f))
        }

        FoquiEstado.Enfocado -> {
            // La pupila "lee": avanza de izquierda a derecha y regresa rápido, dos renglones por loop
            val renglon = (t % 1.5f) / 1.5f
            val px = if (renglon < 0.82f) -7f + 14f * suave(renglon / 0.82f) else 7f - 14f * suave((renglon - 0.82f) / 0.18f)
            scale(1f, parpadeo(t, 1.25f), pivot = Offset(100f, 98f)) {
                drawCircle(Blanco, 34f, Offset(100f, 98f))
                drawCircle(Navy, 17f, Offset(100f + px, 103f))
                drawCircle(Blanco, 4.5f, Offset(106f + px, 98f))
            }
            // Cinta naranja en la frente; las puntas ondean, la de abajo con retraso
            drawPath(cinta, Naranja)
            rotate(6f * onda(t, 0.75f), pivot = Offset(151f, 65f)) {
                rotate(4f * onda(t - 0.08f, 0.75f), pivot = Offset(152f, 64f)) { drawPath(colaArriba, Naranja) }
                rotate(-5f * onda(t - 0.16f, 0.75f), pivot = Offset(152f, 70f)) { drawPath(colaAbajo, Naranja) }
                drawCircle(Naranja, 7f, Offset(151f, 65f))
            }
            drawPath(bocaDecidida, Blanco, style = trazo(4f))
        }

        FoquiEstado.Cansado -> {
            drawCircle(Blanco, 36f, Offset(100f, 94f))
            drawCircle(Navy, 14f, Offset(100f, 108f))
            drawPath(parpadoCansado, Navy)
            drawLine(Blanco, Offset(64f, 94f), Offset(136f, 94f), 4f, StrokeCap.Round)
            drawPath(bocaCansada, Blanco, style = trazo(4f))
            // El "!" del logo se tambalea
            rotate(8f * onda(t, 1.5f), pivot = Offset(171f, 60f)) {
                drawRoundRect(Naranja, Offset(166f, 14f), Size(11f, 32f), CornerRadius(5.5f))
                drawCircle(Naranja, 6f, Offset(171.5f, 58f))
            }
        }

        FoquiEstado.Atenta -> {
            scale(1f, parpadeo(t, 0.9f), pivot = Offset(100f, 92f)) {
                drawCircle(Blanco, 38f, Offset(100f, 92f))
                drawCircle(Navy, 19f, Offset(102f, 86f))
                drawCircle(Blanco, 5f, Offset(109f, 79f))
            }
            mejillas()
            drawOval(Blanco, Offset(94f, 136f), Size(12f, 14f))
        }

        FoquiEstado.Normal -> {
            scale(1f, parpadeo(t, 0.9f), pivot = Offset(100f, 94f)) {
                drawCircle(Blanco, 36f, Offset(100f, 94f))
                drawCircle(Navy, 16f, Offset(100f, 97f))
                drawCircle(Blanco, 5f, Offset(107f, 89f))
            }
            mejillas()
            drawPath(sonrisa, Blanco, style = trazo(4f))
        }
    }
}

@Preview(showBackground = true, widthDp = 720)
@Composable
private fun FoquiPreview() = FocusTheme {
    Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FoquiEstado.entries.forEach { Foqui(it, Modifier.width(130.dp)) }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0B1220, widthDp = 720)
@Composable
private fun FoquiOscuroPreview() = FocusTheme(darkTheme = true) {
    Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FoquiEstado.entries.forEach { Foqui(it, Modifier.width(130.dp)) }
    }
}

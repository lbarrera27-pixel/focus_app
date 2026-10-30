package com.focus.app.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.focus.app.ui.components.FloatingBottomNav
import com.focus.app.ui.components.MainTab
import com.focus.app.ui.screens.AppsScreen
import com.focus.app.ui.screens.FormScreen
import com.focus.app.ui.screens.HomeScreen
import com.focus.app.ui.screens.LoginScreen
import com.focus.app.ui.screens.PlanScreen
import com.focus.app.ui.screens.ProgressScreen
import com.focus.app.ui.screens.SignUpScreen
import com.focus.app.ui.theme.FocusTheme

/** Rutas de las pantallas. Las que aún no existen abren una pantalla "Próximamente". */
object Routes {
    const val Login = "login"
    const val SignUp = "signup"
    const val Menu = "menu"
    const val AddApp = "add-app"
    const val AppDetail = "app-detail"
    const val ScheduleFocus = "schedule-focus"
    const val FocusMode = "focus-mode"
    const val AdjustGoal = "adjust-goal"
    const val AiCompanion = "ai-companion"
    const val Achievements = "achievements"
    const val ForgotPassword = "forgot-password"
}

private val pending = mapOf(
    Routes.Menu to "Menú (09C)",
    Routes.AddApp to "Añadir app (25)",
    Routes.AppDetail to "Detalle de app (19)",
    Routes.ScheduleFocus to "Programar Modo Foco (28)",
    Routes.FocusMode to "Modo Foco (08)",
    Routes.AdjustGoal to "Ajustar mi meta (27)",
    Routes.AiCompanion to "Compañero IA (11)",
    Routes.Achievements to "Meta cumplida (29)",
    Routes.ForgotPassword to "Recuperar contraseña (24)",
)

@Composable
fun FocusApp(navController: NavHostController = rememberNavController()) {
    val backStack by navController.currentBackStackEntryAsState()
    val currentTab = MainTab.entries.firstOrNull { it.route == backStack?.destination?.route }

    fun go(route: String) = navController.navigate(route)
    fun goTab(tab: MainTab) = navController.navigate(tab.route) {
        popUpTo(MainTab.Inicio.route) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }

    Box(Modifier.fillMaxSize()) {
        NavHost(navController, startDestination = Routes.Login) {
            composable(Routes.Login) {
                LoginScreen(
                    onBack = null,
                    onLogin = { navController.navigate(MainTab.Inicio.route) { popUpTo(Routes.Login) { inclusive = true } } },
                    onCreateAccount = { go(Routes.SignUp) },
                    onForgotPassword = { go(Routes.ForgotPassword) },
                )
            }
            composable(Routes.SignUp) {
                SignUpScreen(
                    onBack = { navController.popBackStack() },
                    onCreated = { navController.navigate(MainTab.Inicio.route) { popUpTo(Routes.Login) { inclusive = true } } },
                    onGoToLogin = { navController.popBackStack() },
                )
            }
            composable(MainTab.Inicio.route) {
                HomeScreen(
                    onOpenMenu = { go(Routes.Menu) },
                    onSeeAllApps = { goTab(MainTab.Apps) },
                    onOpenPlan = { goTab(MainTab.Plan) },
                    onOpenProgress = { goTab(MainTab.Progreso) },
                    onStartFocus = { go(Routes.FocusMode) },
                    onSchedule = { go(Routes.ScheduleFocus) },
                )
            }
            composable(MainTab.Apps.route) {
                AppsScreen(onOpenMenu = { go(Routes.Menu) }, onAddApp = { go(Routes.AddApp) }, onOpenApp = { go(Routes.AppDetail) })
            }
            composable(MainTab.Plan.route) {
                PlanScreen(onOpenMenu = { go(Routes.Menu) }, onAdjustGoal = { go(Routes.AdjustGoal) }, onTalkToAi = { go(Routes.AiCompanion) })
            }
            composable(MainTab.Progreso.route) {
                ProgressScreen(onOpenMenu = { go(Routes.Menu) }, onOpenAchievements = { go(Routes.Achievements) })
            }
            pending.forEach { (route, title) ->
                composable(route) { ComingSoonScreen(title, onBack = { navController.popBackStack() }) }
            }
        }
        if (currentTab != null) {
            FloatingBottomNav(current = currentTab, onSelect = ::goTab, modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

/** Marcador para las pantallas del siguiente bloque. */
@Composable
private fun ComingSoonScreen(title: String, onBack: () -> Unit) {
    FormScreen(onBack = onBack, rightLink = null) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = FocusTheme.type.title1, color = FocusTheme.colors.textPrimary, textAlign = TextAlign.Center)
            Text("Esta pantalla está en el siguiente bloque.", style = FocusTheme.type.body, color = FocusTheme.colors.textSecondary)
        }
    }
}

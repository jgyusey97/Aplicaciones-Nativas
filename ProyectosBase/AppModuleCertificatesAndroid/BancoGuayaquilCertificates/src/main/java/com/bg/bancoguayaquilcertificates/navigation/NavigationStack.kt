package com.bg.bancoguayaquilcertificates.navigation

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bg.bancoguayaquilcertificates.views.*
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NavigationStack(navController: NavHostController) {
    val viewModel: CertificatesViewModel = viewModel()

    val slideInAnim = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth },
        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
    )

    val fadeInAnim = fadeIn(
        initialAlpha = 0f,
        animationSpec = tween(durationMillis = 500, easing = EaseOut)
    )

    val animViewEnter = fadeInAnim.plus(slideInAnim)
    val fadeOutAnim = fadeOut(animationSpec = tween(durationMillis = 300))

    NavHost(navController = navController, startDestination = Screen.CertificateReason.route) {
        composable(
            route = Screen.CertificateReason.route,
            enterTransition = { animViewEnter },
            exitTransition = { fadeOutAnim },
        ) {
            CertificateReason(navController, viewModel)
        }
        composable(
            route = Screen.ChooseCertificate.route,
            enterTransition = { animViewEnter },
            exitTransition = { fadeOutAnim },
        ) {
            ChooseCertificate(navController, viewModel)
        }
        composable(
            route = Screen.CertificateDetail.route,
            enterTransition = { animViewEnter },
            exitTransition = { fadeOutAnim },
        ) {
            CertificateDetail(navController, viewModel)
        }
    }
}

enum class Routes {
    CertificateReason,
    ChooseCertificate,
    CertificateDetail,
}

sealed class Screen(val route: String) {
    object CertificateReason : Screen("CertificateReason_Screen")
    object ChooseCertificate : Screen("ChooseCertificate_Screen")
    object CertificateDetail : Screen("CertificateDetail_Screen")
}

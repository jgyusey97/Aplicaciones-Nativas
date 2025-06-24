package com.bg.bancoguayaquilcertificates.views

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bg.bancoguayaquilcertificates.extensions.toTitleCase
import com.bg.bancoguayaquilcertificates.models.CertificateReason
import com.bg.bancoguayaquilcertificates.models.certificateReasons
import com.bg.bancoguayaquilcertificates.services.AnalyticsCategory
import com.bg.bancoguayaquilcertificates.services.AnalyticsEvent
import com.bg.bancoguayaquilcertificates.services.AnalyticsService
import com.bg.bancoguayaquilcertificates.ui.theme.CertificatesTheme
import com.bg.bancoguayaquilcertificates.views.components.OptionItem
import com.bg.bancoguayaquilcertificates.views.components.SimpleTopAppBar
import com.bg.bancoguayaquilcertificates.views.modals.ModalValidationForCode

@Composable
fun CertificateReason(
    navController: NavController? = null,
    viewModel: CertificatesViewModel
) {
    val certificateTypes by viewModel.certificateTypes.collectAsState()

    val activity = LocalActivity.current
    val localCtx = LocalContext.current
    val view = "RECOMENDACIONES"

    val onBack = {
        activity?.finish()
        viewModel.resetState()
    }

    fun onPresentView() {
        val params = mapOf(
            "view" to view,
            "category" to AnalyticsCategory.certificados,
        )

        AnalyticsService.instance.sendEvent(
            AnalyticsEvent.SCREEN_VIEW,
            params
        )
    }

    fun onSelectReason(item: CertificateReason) {
        viewModel.updateState {
            copy(certReason = item)
        }

        val params = mapOf(
            "view" to view,
            "category" to AnalyticsCategory.certificados,
            "name" to "Seleccionar recomendación",
            "value" to item.value,
        )

        navController?.navigate("ChooseCertificate_Screen")
        AnalyticsService.instance.sendEvent(AnalyticsEvent.ITEM_TAP, params)
    }

    LaunchedEffect(Unit) {
        if (certificateTypes is UiState.Idle) {
            viewModel.loadCertificateTypes()
        }

        onPresentView()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
    ) {
        SimpleTopAppBar(
            title = "¿Para qué necesitas tu certificado?",
            subTitle = "Te recomendaremos la opción que mejor se ajuste a tus necesidades:",
            onBack = onBack
        )

        certificateReasons.forEach {
            OptionItem(
                text = it.value.toTitleCase(),
                onSelect = { onSelectReason(it) }
            )
        }

        when (val e = certificateTypes) {
            is UiState.Error -> {
                val errCode = e.error.code
                val errMessage = e.error.message
                var isVisible by remember { mutableStateOf(true) }

                viewModel.showToast(localCtx, errMessage)

                ModalValidationForCode(
                    isModalVisible = isVisible,
                    validationCode = errCode,
                    onDismiss = { isVisible = false }
                )
            }

            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CertificateReasonPreview() {
    CertificatesTheme {
        CertificateReason(viewModel = viewModel())
    }
}

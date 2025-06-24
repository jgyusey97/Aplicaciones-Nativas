package com.bg.bancoguayaquilcertificates.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import com.bg.bancoguayaquilcertificates.models.CertificateType
import com.bg.bancoguayaquilcertificates.services.AnalyticsCategory
import com.bg.bancoguayaquilcertificates.services.AnalyticsEvent
import com.bg.bancoguayaquilcertificates.services.AnalyticsService
import com.bg.bancoguayaquilcertificates.ui.theme.CertificatesTheme
import com.bg.bancoguayaquilcertificates.views.components.OptionItem
import com.bg.bancoguayaquilcertificates.views.components.OptionItemVariant
import com.bg.bancoguayaquilcertificates.views.components.PrimaryButton
import com.bg.bancoguayaquilcertificates.views.components.SimpleTopAppBar
import com.bg.bancoguayaquilcertificates.views.modals.ModalValidationForCode

@Composable
fun ChooseCertificate(
    navController: NavController? = null,
    viewModel: CertificatesViewModel
) {
    val state by viewModel.certificatesState.collectAsState()
    val certificateTypes by viewModel.certificateTypes.collectAsState()
    val certificatesRefs by viewModel.certificatesRefs.collectAsState()

    val certReason = state.certReason
    val certType = state.certType

    val localCtx = LocalContext.current
    val view = "ELEGIR_CERTIFICADO"

    val onBack: () -> Unit = {
        navController?.popBackStack()
        viewModel.updateState {
            copy(certType = null)
        }
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

    fun onSelectCertType(item: CertificateType) {
        if (certType?.extCode != item.extCode) {
            viewModel.updateState {
                copy(certType = item)
            }

            val params = mapOf(
                "view" to view,
                "category" to AnalyticsCategory.certificados,
                "name" to "Seleccionar tipo de certificado",
                "value" to item.description,
            )

            AnalyticsService.instance.sendEvent(
                AnalyticsEvent.ITEM_TAP,
                params
            )
        }
    }

    LaunchedEffect(Unit) {
        if (certType != null) {
            viewModel.updateState { copy(certType = null) }
        }

        if (certificatesRefs is UiState.Error) {
            viewModel.setCertificatesRefs(UiState.Idle)
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
            title = "Elige tu certificado",
            onBack = onBack
        )

        when (val s = certificateTypes) {
            is UiState.Loading -> CircularProgressIndicator()

            is UiState.Success -> {
                val data = s.data

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 10.dp)
                ) {
                    items(data, key = { it.description }) {
                        val isRecommended =
                            certReason != null && certReason.recommended?.contains(it.extCode) == true

                        OptionItem(
                            text = it.description.toTitleCase(),
                            variant = OptionItemVariant.Radio,
                            selected = certType?.extCode == it.extCode,
                            badgeText = if (isRecommended) "Recomendado" else null,
                            onSelect = { onSelectCertType(it) }
                        )
                    }
                }
            }

            is UiState.Error -> {
                val errCode = s.error.code
                var isVisible by remember { mutableStateOf(true) }

                ModalValidationForCode(
                    isModalVisible = isVisible,
                    validationCode = errCode,
                    onDismiss = {
                        isVisible = false
                    }
                )
            }

            else -> {}
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            PrimaryButton(
                text = "Listo",
                enabled = certType != null,
                isLoading = certificatesRefs is UiState.Loading,
                onClick = {
                    if (certType != null) {
                        viewModel.loadDetailCertificate(certType.extCode) {
                            navController?.navigate("CertificateDetail_Screen")
                        }

                        val params = mapOf(
                            "view" to view,
                            "category" to AnalyticsCategory.certificados,
                            "name" to "Listo",
                        )

                        AnalyticsService.instance.sendEvent(
                            AnalyticsEvent.BUTTON_TAP,
                            params
                        )
                    }
                },
            )
        }

        when (val e = certificatesRefs) {
            is UiState.Error -> {
                val errCode = e.error.code
                val errMessage = e.error.message
                var isVisible by remember { mutableStateOf(true) }

                viewModel.showToast(localCtx, errMessage)

                ModalValidationForCode(
                    isModalVisible = isVisible,
                    validationCode = errCode,
                    onDismiss = {
                        isVisible = false

                        viewModel.updateState {
                            copy(certType = null)
                        }

                        viewModel.setCertificatesRefs(UiState.Idle)
                    }
                )
            }

            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChooseCertificatePreview() {
    CertificatesTheme {
        ChooseCertificate(viewModel = viewModel())
    }
}

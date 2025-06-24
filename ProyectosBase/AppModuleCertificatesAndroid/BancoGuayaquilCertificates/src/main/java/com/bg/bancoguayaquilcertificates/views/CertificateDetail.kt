package com.bg.bancoguayaquilcertificates.views

import android.annotation.SuppressLint
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bg.bancoguayaquilcertificates.extensions.toTitleCase
import com.bg.bancoguayaquilcertificates.models.CertificateData
import com.bg.bancoguayaquilcertificates.services.AnalyticsCategory
import com.bg.bancoguayaquilcertificates.services.AnalyticsEvent
import com.bg.bancoguayaquilcertificates.services.AnalyticsService
import com.bg.bancoguayaquilcertificates.ui.theme.CertificatesTheme
import com.bg.bancoguayaquilcertificates.views.components.PrimaryButton
import com.bg.bancoguayaquilcertificates.views.components.SegmentOption
import com.bg.bancoguayaquilcertificates.views.components.SegmentOptions
import com.bg.bancoguayaquilcertificates.views.components.SimpleTopAppBar
import com.bg.bancoguayaquilcertificates.views.modals.CertificateSentModal
import com.bg.bancoguayaquilcertificates.views.modals.ModalValidationForCode
import com.bg.bancoguayaquilcertificates.views.modals.ProductsModal

@SuppressLint("MemberExtensionConflict")
@Composable
fun CertificateDetail(
    navController: NavController?,
    viewModel: CertificatesViewModel
) {
    val state by viewModel.certificatesState.collectAsState()
    val certificatesRefs by viewModel.certificatesRefs.collectAsState()
    val referenceLetter by viewModel.referenceLetter.collectAsState()

    val certType = state.certType
    val addressed = state.addressed
    val selectLanguage = state.selectLanguage
    val languages = state.languages
    val balanceTypes = state.balanceTypes
    val productsToCert = state.productsToCert

    val selectBalanceType = state.selectBalanceType
    val selectProductCert = state.selectProductCert

    var includeBalance by remember { mutableStateOf(false) }
    var showModalProducts by remember { mutableStateOf(false) }
    var showCertSent by remember { mutableStateOf(false) }

    val addressedFocus = remember { FocusRequester() }
    var showAddressedError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val activity = LocalActivity.current
    val localCtx = LocalContext.current
    val view = "FORM_SOLICITAR_CERTIFICADO"

    val onBack = {
        navController?.popBackStack()
        viewModel.resetCertFormState()
    }

    val languagesOptions = languages.map { item ->
        SegmentOption(
            id = item.controlId,
            value = item.description.toTitleCase()
        )
    }

    val availableBalanceTypes = balanceTypes.filter { it.hostCode != "N" }

    fun toggleIncludeBalance(checked: Boolean) {
        includeBalance = checked

        if (!checked) {
            val noBalanceType = balanceTypes.find { it.hostCode == "N" }

            viewModel.updateState {
                copy(selectBalanceType = noBalanceType?.hostCode ?: "")
            }
            return
        }

        val firstBalanceType = availableBalanceTypes[0]
        viewModel.updateState {
            copy(selectBalanceType = firstBalanceType?.hostCode ?: "")
        }
    }

    fun formatProductCert(): String? {
        if (selectProductCert == null) return null

        val prodName = viewModel.formatProductName(selectProductCert.productType)
        val prodDesc = viewModel.formatProductDesc(selectProductCert.productCode)

        if (prodName !== null && prodDesc !== null) {
            return "${prodName.toTitleCase()} - $prodDesc"
        }

        if (prodName !== null) return prodName.toTitleCase()

        return null
    }

    fun onSentCertificate() {
        if (certType?.description.isNullOrEmpty()) {
            onBack()
            return
        }

        if (addressed.isBlank() || addressed.length < 4) {
            addressedFocus.requestFocus()
            showAddressedError = true
            return
        }

        if (selectProductCert == null) {
            showModalProducts = true
            return
        }

        viewModel.requestReferenceLetter {
            showCertSent = true
        }

        val params = mapOf(
            "view" to view,
            "category" to AnalyticsCategory.certificados,
            "name" to "Enviar certificado",
        )

        AnalyticsService.instance.sendEvent(
            AnalyticsEvent.BUTTON_TAP,
            params,
        )
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

    LaunchedEffect(Unit) {
        if (addressed.isNotBlank()) {
            viewModel.updateState { copy(addressed = "") }
        }

        onPresentView()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
    ) {
        SimpleTopAppBar(
            title = "Certificados bancarios",
            onBack = onBack
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ButtonSelect(
                label = "Tipo de certificado",
                text = certType?.description?.toTitleCase(),
                onClick = onBack
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(addressedFocus),
                value = addressed,
                label = { Text("¿A quién va dirigido?") },
                placeholder = { Text("Ej.: A quién corresponda") },
                singleLine = true,
                onValueChange = { newVal ->
                    val maxlength = 30
                    val aliasPattern = Regex("^[A-Za-z0-9 ñÑáéíóúÁÉÍÓÚ]+$")

                    showAddressedError = (newVal.length < 4)

                    if (
                        newVal.isEmpty() ||
                        (newVal.length <= maxlength && aliasPattern.matches(newVal))
                    ) {
                        viewModel.updateState { copy(addressed = newVal) }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFD2006E),
                    focusedLabelColor = Color(0xFFD2006E),
                    errorBorderColor = Color(0xFFF13838),
                    errorSupportingTextColor = Color(0xFFF13838),
                    errorLabelColor = Color(0xFFF13838),
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus(true) }
                ),
                isError = showAddressedError,
                supportingText = {
                    if (showAddressedError) Text("Debe tener al menos 4 caracteres")
                }
            )

            ButtonSelect(
                label = "Producto a certificar",
                text = formatProductCert(),
                onClick = { showModalProducts = true }
            )

            Column {
                Text(
                    "¿En qué idioma lo necesitas?",
                    fontWeight = FontWeight.SemiBold,
                )
                SegmentOptions(
                    value = selectLanguage,
                    options = languagesOptions,
                    onSelect = {
                        viewModel.updateState {
                            copy(selectLanguage = it.id)
                        }

                        val params = mapOf(
                            "view" to view,
                            "category" to AnalyticsCategory.certificados,
                            "name" to "Seleccionar idioma certificado",
                            "value" to it.value,
                        )

                        AnalyticsService.instance.sendEvent(
                            AnalyticsEvent.SEGMENT_TAP,
                            params
                        )
                    },
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            IncludeBalanceToggle(
                isChecked = includeBalance,
                onCheckedChange = { toggleIncludeBalance(it) }
            )

            AnimatedVisibility(includeBalance) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Color(0xFFF9F9F9)),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    availableBalanceTypes.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple(),
                                    onClick = {
                                        viewModel.updateState {
                                            copy(selectBalanceType = item.hostCode)
                                        }
                                    }
                                )
                                .padding(vertical = 14.dp, horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = item.description.toTitleCase(),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp
                            )

                            RadioButton(
                                selected = selectBalanceType == item.hostCode,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFD2006E)
                                )
                            )
                        }
                    }
                }
            }
        }

        PrimaryButton(
            text = "Enviar certificado",
            isLoading = referenceLetter is UiState.Loading,
            onClick = { onSentCertificate() }
        )

        ProductsModal(
            isOpen = showModalProducts,
            products = productsToCert,
            onDismiss = { showModalProducts = false },
            onSelectProduct = { prod ->
                if (certificatesRefs is UiState.Success) {
                    val productsRaw =
                        (certificatesRefs as UiState.Success<CertificateData>).data?.productDetails

                    val productFound =
                        productsRaw?.find { item -> item.productCode == prod.productCode }

                    if (productFound !== null) {
                        viewModel.updateState { copy(selectProductCert = productFound) }
                        showModalProducts = false
                    }
                }
            },
        )

        CertificateSentModal(
            isOpen = showCertSent,
            email = viewModel.getUserEmail(),
            onDismiss = { activity?.finish() }
        )

        when (val e = referenceLetter) {
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
                    }
                )
            }

            else -> {}
        }
    }
}

@Composable
fun ButtonSelect(
    label: String,
    text: String? = null,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick?.invoke() }
            .background(color = Color(0xFFF9F9F9))
            .height(65.dp)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(text = label, fontSize = 12.sp)

            text?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Ir",
            tint = Color.Gray
        )
    }
}

@Composable
fun IncludeBalanceToggle(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "¿Quieres incluir el saldo?",
            fontWeight = FontWeight.SemiBold,
        )

        Switch(
            checked = isChecked,
            onCheckedChange = { onCheckedChange(it) },
            colors = SwitchDefaults.colors(
                checkedIconColor = Color(0xFFD2006E),
                checkedTrackColor = Color(0xFFD2006E),
            ),

            thumbContent = {
                if (isChecked) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CertificateDetailPreview() {
    CertificatesTheme {
        CertificateDetail(null, viewModel = viewModel())
    }
}

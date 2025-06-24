package com.bg.bancoguayaquilcertificates.views.modals

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.bg.bancoguayaquilcertificates.R
import com.bg.bancoguayaquilcertificates.views.components.PrimaryButton
import com.bg.bancoguayaquilcertificates.views.components.SecondaryButton
import androidx.core.net.toUri
import com.bg.bancoguayaquilcertificates.models.ValidationCodeType
import com.bg.bancoguayaquilcertificates.models.ValidationConfiguration
import com.bg.bancoguayaquilcertificates.ui.theme.CertificatesTheme

/**
 * Displays a modal dialog with validation messages based on error codes from banking operations.
 *
 * This composable function shows different validation screens depending on the error code received
 * from banking services. It handles various scenarios like connection issues, payment requirements,
 * account needs, and other banking-related validations.
 *
 * ## Supported Validation Types:
 * - **GENERAL**: Generic connection/system errors (codes: 5091, 5092, 5093, 5094, 5095, 5097)
 * - **NEED_SAVING_ACCOUNT**: User needs to open a savings account
 * - **NEED_PAY**: User has pending payments (codes: 5089, 5024, 5026)
 * - **OVERDUE_DEBT**: User has overdue debt (code: 5090)
 * - **OVERDUE_OVERDRAFT_DEBT**: User has overdue credit card debt (code: 5027)
 * - **HAS_NOT_PRODUCT**: User needs active banking products (codes: 5025, 5028)
 * - **UNASSIGNED_OFFICER**: User has no assigned account officer (code: 5080)
 *
 * @param isModalVisible Mutable state controlling the visibility of the modal dialog.
 *                       Set to `false` to hide the modal, `true` to show it.
 * @param validationCode The error code or validation type that determines which
 *                       validation configuration to display. Defaults to "GENERAL"
 *                       if no specific code matches or if not provided.
 *
 * @see ValidationCodeType for available validation types
 * @see ValidationConfiguration for modal configuration structure
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalValidationForCode(
    isModalVisible: Boolean,
    onDismiss: () -> Unit,
    validationCode: String = "GENERAL",
) {
    val context = LocalContext.current
    val urlWhatsApp = "https://wa.me/0983730100"
    val urlContractSavingAccount = "https://personas.bancoguayaquil.com/contrataciones/cuentas/"
    val urlContractProducts = "https://www.bancoguayaquil.com/cuentas/"

    fun openBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = url.toUri()
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
    val validationConfigurationsMap = mapOf(
        ValidationCodeType.GENERAL.value to ValidationConfiguration(
            title = "Tenemos un problema de conexión",
            description = "Lo sentimos, parece que la conexión se interrumpió. Por favor, inténtalo de nuevo más tarde.",
            primaryButtonText = "Cerrar",
            imageResource = R.drawable.general_error,
            primaryButtonAction = { onDismiss() }
        ),
        ValidationCodeType.NEED_SAVING_ACCOUNT.value to ValidationConfiguration(
            title = "Necesitas abrir una Cuenta de Ahorros para pagar tu certificado bancario",
            description = "Ábrela desde la App y recibe una tarjeta de débito física y virtual para comprar en tus tiendas favoritas, acceder a eventos exclusivos y pagar desde tu celular.",
            primaryButtonText = "Cerrar",
            imageResource = R.drawable.need_saving_account,
            primaryButtonAction = { onDismiss() },
            secondaryButtonText = "Abrir Cuenta de Ahorros",
            secondaryButtonAction = {
                openBrowser(context, urlContractSavingAccount)
            }
        ),
        ValidationCodeType.NEED_PAY.value to ValidationConfiguration(
            title = "Tienes un pago pendiente",
            description = "No podemos generar tu certificado porque tienes un pago pendiente con el banco. Por favor, comunícate con un asesor para tener más información.",
            primaryButtonText = "Cerrar",
            imageResource = R.drawable.need_pay,
            primaryButtonAction = { onDismiss() },
            secondaryButtonText = "Contactar a un asesor",
            secondaryButtonAction = {
                openBrowser(context, urlWhatsApp)
            }
        ),
        ValidationCodeType.OVERDUE_DEBT.value to ValidationConfiguration(
            title = "Tienes un pago pendiente",
            description = "No podemos generar tu certificado porque tienes un pago pendiente con el banco. Por favor, comunícate con un asesor para tener más información.",
            primaryButtonText = "Cerrar",
            imageResource = R.drawable.need_pay,
            primaryButtonAction = { onDismiss() },
            secondaryButtonText = "Contactar a un asesor",
            secondaryButtonAction = {
                openBrowser(context, urlWhatsApp)
            }
        ),
        ValidationCodeType.OVERDUE_OVERDRAFT_DEBT.value to ValidationConfiguration(
            title = "No pudimos generar tu certificado",
            description = "Lo sentimos, tienes una deuda en tu tarjeta de crédito y no pudimos procesar tu certificado bancario. Por favor, comunícate con un asesor para tener más información.",
            primaryButtonText = "Cerrar",
            imageResource = R.drawable.need_pay,
            primaryButtonAction = { onDismiss() },
            secondaryButtonText = "Contactar a un asesor",
            secondaryButtonAction = {
                openBrowser(context, urlWhatsApp)
            }
        ),
        ValidationCodeType.HAS_NOT_PRODUCT.value to ValidationConfiguration(
            title = "No pudimos generar tu certificado",
            description = "Para generar un certificado bancario necesitas tener productos activos en el banco. Contrata uno y accede a este y más beneficios.",
            primaryButtonText = "Cerrar",
            imageResource = R.drawable.need_products,
            primaryButtonAction = { onDismiss() },
            secondaryButtonText = "Contratar un producto",
            secondaryButtonAction = {
                openBrowser(context, urlContractProducts)
            }
        ),
        ValidationCodeType.UNASSIGNED_OFFICER.value to ValidationConfiguration(
            title = "No tienes un oficial de cuenta asignado",
            description = "No podemos generar tu certificado debido a que no tienes un oficial de cuenta asignado. Por favor, comunícate con un asesor para tener más información.",
            primaryButtonText = "Cerrar",
            imageResource = R.drawable.unassigned_officer,
            primaryButtonAction = { onDismiss() },
            secondaryButtonText = "Contactar a un asesor",
            secondaryButtonAction = {
                openBrowser(context, urlWhatsApp)
            }
        )
    )

    val currentValidationConfig = remember { mutableStateOf(validationConfigurationsMap[ValidationCodeType.GENERAL.value]) }

    fun determineValidationType() {
        val generalErrorCodes = arrayOf("5091", "5092","5093","5095","5094","5094","5097")
        val paymentRequiredErrorCodes = arrayOf("5089","5024","5026")

        if(validationCode == ValidationCodeType.GENERAL.value){
            currentValidationConfig.value = validationConfigurationsMap[ValidationCodeType.GENERAL.value]
            return
        }

        if(validationCode == ValidationCodeType.NEED_SAVING_ACCOUNT.value){
            currentValidationConfig.value = validationConfigurationsMap[ValidationCodeType.NEED_SAVING_ACCOUNT.value]
            return
        }

        val matchedGeneralErrorCode = generalErrorCodes.find { it == validationCode  }
        if (matchedGeneralErrorCode != null) {
            currentValidationConfig.value = validationConfigurationsMap[ValidationCodeType.GENERAL.value]
            return
        }

        val matchedPaymentRequiredCode = paymentRequiredErrorCodes.find { it == validationCode  }
        if(matchedPaymentRequiredCode != null){
            currentValidationConfig.value = validationConfigurationsMap[ValidationCodeType.NEED_PAY.value]
            return
        }

        if(validationCode == "5090"){
            currentValidationConfig.value = validationConfigurationsMap[ValidationCodeType.OVERDUE_DEBT.value]
            return
        }

        if(validationCode == "5028"){
            currentValidationConfig.value = validationConfigurationsMap[ValidationCodeType.HAS_NOT_PRODUCT.value]
            return
        }

        if(validationCode == "5025"){
            currentValidationConfig.value = validationConfigurationsMap[ValidationCodeType.HAS_NOT_PRODUCT.value]
            return
        }

        if(validationCode == "5027"){
            currentValidationConfig.value = validationConfigurationsMap[ValidationCodeType.OVERDUE_OVERDRAFT_DEBT.value]
            return
        }

        if(validationCode == "5080"){
            currentValidationConfig.value = validationConfigurationsMap[ValidationCodeType.UNASSIGNED_OFFICER.value]
            return
        }

        currentValidationConfig.value = validationConfigurationsMap[ValidationCodeType.GENERAL.value]
    }

    LaunchedEffect(validationCode) {
        determineValidationType()
    }

    if (isModalVisible) {
        BasicAlertDialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier.background(color = Color.White),
                shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
            ) {
                Scaffold(
                    bottomBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                currentValidationConfig.value?.let { config ->
                                    if (config.secondaryButtonText != null && config.secondaryButtonAction != null) {
                                        SecondaryButton(config.secondaryButtonText, config.secondaryButtonAction)
                                    }
                                }
                                currentValidationConfig.value?.let { config ->
                                    PrimaryButton(
                                        config.primaryButtonText,
                                        config.primaryButtonAction
                                    )
                                }
                            }
                        }
                    },
                    content = { paddingValues ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.White)
                                .padding(paddingValues)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            currentValidationConfig.value?.imageResource.let { imageResource ->
                                Image(
                                    painter = painterResource(id = imageResource!!),
                                    contentDescription = "Header image",
                                    )
                            }
                            Spacer(Modifier.height(80.dp))
                            currentValidationConfig.value?.title?.let { titleText ->
                                Text(
                                    text = titleText,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 19.sp
                                )
                            }
                            Spacer(Modifier.height(40.dp))
                            currentValidationConfig.value?.description?.let { descriptionText ->
                                Text(
                                    text = descriptionText,
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp,
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModalValidationsPreview() {
    var isModalVisible by remember { mutableStateOf(true) }

    CertificatesTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Background content
            Text(
                text = "Background Content",
                modifier = Modifier.align(Alignment.Center)
            )

            // Modal
            ModalValidationForCode(
                isModalVisible = isModalVisible,
                validationCode = "5089",
                onDismiss = { isModalVisible = false }
            )
        }
    }
}

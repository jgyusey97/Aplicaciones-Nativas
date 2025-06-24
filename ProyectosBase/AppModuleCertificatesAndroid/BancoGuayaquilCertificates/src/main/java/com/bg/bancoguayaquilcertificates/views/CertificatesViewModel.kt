package com.bg.bancoguayaquilcertificates.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bg.bancoguayaquilcertificates.models.CatalogDetailByType
import com.bg.bancoguayaquilcertificates.models.CertificateData
import com.bg.bancoguayaquilcertificates.models.CertificateReason
import com.bg.bancoguayaquilcertificates.models.CertificateRequest
import com.bg.bancoguayaquilcertificates.models.CertificateType
import com.bg.bancoguayaquilcertificates.models.GetCertificateRequest
import com.bg.bancoguayaquilcertificates.models.ProductCertOption
import com.bg.bancoguayaquilcertificates.models.ProductDetail
import com.bg.bancoguayaquilcertificates.services.CertificatesService
import com.bg.bancoguayaquilmultichannel.models.ServiceError
import com.bg.bancoguayaquilsession.BancoGuayaquilSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ErrorState(
    var code: String = "",
    var message: String = ""
)

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val error: ErrorState) : UiState<Nothing>()
}

data class CertificatesState(
    val certReason: CertificateReason? = null,
    val certType: CertificateType? = null,
    val addressed: String = "",
    val selectLanguage: String = "",
    val selectBalanceType: String = "",
    val selectProductCert: ProductDetail? = null,
    val languages: List<CatalogDetailByType> = emptyList(),
    val balanceTypes: List<CatalogDetailByType> = emptyList(),
    val productsToCert: List<ProductCertOption> = emptyList(),
)

class CertificatesViewModel : ViewModel() {
    private val _service = CertificatesService()
    private val _session = BancoGuayaquilSession.instance.getSession()

    private val _certificatesState = MutableStateFlow(CertificatesState())
    private val _certificateTypes: MutableStateFlow<UiState<List<CertificateType>>> =
        MutableStateFlow(UiState.Idle)

    private val _certificatesRefs: MutableStateFlow<UiState<CertificateData>> =
        MutableStateFlow(UiState.Idle)

    private val _referenceLetter: MutableStateFlow<UiState<Boolean>> =
        MutableStateFlow(UiState.Idle)

    val certificatesState = _certificatesState.asStateFlow()
    val certificateTypes = _certificateTypes.asStateFlow()
    val certificatesRefs = _certificatesRefs.asStateFlow()
    val referenceLetter = _referenceLetter.asStateFlow()

    fun updateState(update: CertificatesState.() -> CertificatesState) {
        _certificatesState.update { current ->
            current.update()
        }
    }

    fun updateCatalogue(catalogue: List<CatalogDetailByType>) {
        val languagesCat = catalogue
            .filter { it.catalogType == "IDIOMAS" }

        val balanceTypesCat = catalogue
            .filter { it.catalogType == "TIPOSALDOS" }

        val noBalanceType = balanceTypesCat.find { it.hostCode == "N" }

        updateState {
            copy(
                selectLanguage = if (languagesCat.isNotEmpty()) languagesCat[0].controlId else "",
                selectBalanceType = noBalanceType?.hostCode ?: "",
                languages = languagesCat,
                balanceTypes = balanceTypesCat,
            )
        }
    }

    fun updateProductsCert(products: List<ProductDetail>) {
        if (products.isEmpty()) return

        val validProducts = products.filter { it.productType != null || it.productCode != null }

        val productsCertOptions = validProducts.map { item ->
            ProductCertOption(
                productCode = item.productCode,
                productName = formatProductName(item.productType),
                productDesc = formatProductDesc(item.productCode)
            )
        }

        if (productsCertOptions.isNotEmpty()) {
            updateState { copy(productsToCert = productsCertOptions) }
        }

        if (products.size == 1) {
            val product = validProducts
                .find { it.productCode?.lowercase() != "x" }

            updateState {
                copy(selectProductCert = product)
            }
        }
    }

    fun formatProductName(productType: String, default: String = "Cédula"): String {
        val result = when {
            productType?.contains(":") == true -> productType.split(":")[1]
            else -> productType
        }

        if (result.isNullOrEmpty()) return default
        return result
    }

    fun formatProductDesc(productCode: String): String? = when {
        productCode?.lowercase() != "x" -> productCode
        else -> null
    }

    fun getUserEmail(): String? {
        val user = _session?.user
        return user?.email?.lowercase()
    }

    fun resetState() {
        val idle = UiState.Idle

        _certificatesState.value = CertificatesState()
        setCertificateTypes(idle)
        setCertificatesRefs(idle)
        setReferenceLetter(idle)
    }

    fun resetCertFormState() {
        updateState {
            copy(
                certType = null,
                addressed = "",
                selectProductCert = null,
                selectLanguage = "",
                selectBalanceType = "",
            )
        }
    }

    fun showToast(ctx: Context, message: String): Unit {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
    }

    fun setCertificateTypes(state: UiState<List<CertificateType>>) {
        _certificateTypes.value = state
    }

    fun setCertificatesRefs(state: UiState<CertificateData>) {
        _certificatesRefs.value = state
    }

    fun setReferenceLetter(state: UiState<Boolean>) {
        _referenceLetter.value = state
    }

    fun loadCertificateTypes() {
        viewModelScope.launch {
            _certificateTypes.value = UiState.Loading

            try {
                val res = _service.getCertificateTypes()
                _certificateTypes.value = UiState.Success(res)
            } catch (e: ServiceError.ResponseError) {
                _certificateTypes.value = UiState.Error(
                    ErrorState(e.code, e.message)
                )
            } catch (e: Exception) {
                _certificateTypes.value = UiState.Error(
                    ErrorState(message = e.message ?: "Unknown error")
                )
            }
        }
    }

    fun loadDetailCertificate(extCode: String, onDone: () -> Unit) {
        viewModelScope.launch {
            _certificatesRefs.value = UiState.Loading

            try {
                val res = _service.getDetailCertificate(extCode)
                _certificatesRefs.value = UiState.Success(res)
                updateCatalogue(res.catalogDetailsByType)
                updateProductsCert(res.productDetails)

                Log.d("loadDetailCertificate", "Res $res")

                onDone()
            } catch (e: ServiceError.ResponseError) {
                Log.d("loadDetailCertificate", "ResponseError $e")

                _certificatesRefs.value = UiState.Error(
                    ErrorState(e.code, e.message)
                )
            } catch (e: Exception) {
                Log.d("loadDetailCertificate", "Exception $e")

                _certificatesRefs.value = UiState.Error(
                    ErrorState(message = e.message ?: "Unknown error")
                )
            }
        }
    }

    fun requestReferenceLetter(onDone: () -> Unit) {
        viewModelScope.launch {
            _referenceLetter.value = UiState.Loading

            try {
                val state = certificatesState.value

                val data = GetCertificateRequest(
                    product = state.selectProductCert!!,
                    request = CertificateRequest(
                        recipient = state.addressed,
                        language = state.selectLanguage,
                        balanceType = state.selectBalanceType,
                        certificateType = state.certType!!,
                    )
                )

                Log.d("requestReferenceLetter", "RequestData: $data")

                val res = _service.getReferenceLetter(data)
                _referenceLetter.value = UiState.Success(res)

                Log.d("requestReferenceLetter", "Res: $res")

                if (res) onDone()
            } catch (e: ServiceError.ResponseError) {
                _referenceLetter.value = UiState.Error(
                    ErrorState(e.code, e.message)
                )
            } catch (e: Exception) {
                Log.d("requestReferenceLetter", "Error: $e")

                _referenceLetter.value = UiState.Error(
                    ErrorState(message = e.message ?: "Unknown error")
                )
            }
        }
    }
}

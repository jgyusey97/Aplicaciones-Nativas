package com.bg.bancoguayaquilcertificates.services

import com.bg.bancoguayaquilcertificates.models.CertificateData
import com.bg.bancoguayaquilcertificates.models.CertificateType
import com.bg.bancoguayaquilcertificates.models.GetCertificateRequest
import com.bg.bancoguayaquilcertificates.repository.CertificatesRepository

class CertificatesService {
    private val repository = CertificatesRepository()

    suspend fun getCertificateTypes(): List<CertificateType> {
        return repository.getCertificateTypes()
    }

    suspend fun getDetailCertificate(extCode: String): CertificateData {
        return repository.getDetailCertificate(extCode)
    }

    suspend fun getReferenceLetter(data: GetCertificateRequest): Boolean {
        return repository.requestReferenceLetter(data)
    }
}

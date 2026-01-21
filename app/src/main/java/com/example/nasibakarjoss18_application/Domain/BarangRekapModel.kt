package com.example.nasibakarjoss18_application.Domain

import java.io.Serializable

data class BarangRekapModel(
    val barangId: String,
    val namaBarang: String,
    val stokAwal: Int,
    val totalMasuk: Int,
    val totalKeluar: Int
) : Serializable

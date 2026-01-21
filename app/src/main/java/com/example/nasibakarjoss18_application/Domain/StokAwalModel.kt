package com.example.nasibakarjoss18_application.Domain

import java.io.Serializable

data class StokAwalModel(
    var barangId : String = "",
    var jumlah : Long = 0,
    var createdAt : String = ""
) : Serializable

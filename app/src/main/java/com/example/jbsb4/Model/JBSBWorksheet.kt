package com.example.jbsb4.Model

data class JBSBWorksheet(
    val totalDuration: Int,
    val totalPay: Double,
    val worksheet: List<WorksheetItem>
)
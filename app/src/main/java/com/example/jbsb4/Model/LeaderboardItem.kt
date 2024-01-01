package com.example.jbsb4.Model

data class LeaderboardItem(
    val commitment: Double,
    val duration: Int,
    val name: String,
    val overtimeCount: Int,
    val punctuality: Double,
    val rating: Double,
    val reliability: Double,
    val score: Double
)
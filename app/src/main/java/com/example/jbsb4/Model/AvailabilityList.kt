package com.example.jbsb4.Model

data class AvailabilityList(
    val dateAvail: String,
    val hourOfDay: String,
    val isAvailable: Boolean,
    val studentID: Int
)
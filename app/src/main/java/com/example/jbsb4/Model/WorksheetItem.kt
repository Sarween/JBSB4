package com.example.jbsb4.Model

data class WorksheetItem(
    val isLate: Boolean,
    val isOvertime: Boolean,
    val jobLocation: String,
    val jobPIC: String,
    val jobVerification: Boolean,
    val rating: Int,
    val shiftCheckInTime: String,
    val shiftCheckOutTime: String,
    val shiftDuration: Int,
    val shiftdates: String,
    val shiftsWage: Double,
    val staffReview: String
)
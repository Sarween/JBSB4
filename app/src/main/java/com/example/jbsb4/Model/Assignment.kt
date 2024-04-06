package com.example.jbsb4.Model

data class Assignment (
    val recruitmentID: Int,
    val studentID: Int,
    val cancelRequest: Boolean,
    val isCancelled: Boolean
)
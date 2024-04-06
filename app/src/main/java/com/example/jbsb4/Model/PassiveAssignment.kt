package com.example.jbsb4.Model

data class PassiveAssignment(
    val endTime: String,
    val isFCFS: Int,
    val jobDescription: String,
    val jobLocation: String,
    val jobShiftDate: String,
    val recruitmentID: Int,
    val startTime: String,
    val stuNumReqRemain: Int,
    val studentID: Int
)
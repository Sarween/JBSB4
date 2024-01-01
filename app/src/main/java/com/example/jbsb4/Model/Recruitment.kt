package com.example.jbsb4.Model

data class Recruitment(
    val recruitmentID: Int,
    val staffID: Int,
    val stuNumReq: Int,
    val jobShiftDate: String,
    val startTime: String,
    val endTime: String,
    val jobLocation: String,
    val jobDescription: String,
    val isFCFS: Int,
    val stuNumReqRemain: Int
)

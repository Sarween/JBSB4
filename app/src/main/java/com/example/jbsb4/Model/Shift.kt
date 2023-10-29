package com.example.jbsb4.Model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.time.LocalDateTime

data class Shift(
    val recruitmentID: Int,
    val studentID: Int,
    val isCancelled: Boolean,
    val checkInTime: String,
    val checkOutTime: String,
    val isLate: Boolean,
    val isOvertime: Boolean,
    val studentComment: String,
    val staffReview: String,
    val rating: Int,
    val isAuthorized: Boolean
)


//class Shift {
//
//    /*
//        public int RecruitmentID { get; set; }
//        public int StudentID { get; set; }
//        public bool IsCancelled { get; set; }
//        public DateTime CheckInTime { get; set; }
//        public DateTime CheckOutTime { get; set; }
//        public bool IsLate { get; set; }
//        public bool IsOvertime { get; set; }
//        public string StudentComment { get; set; }
//        public string StaffReview { get; set; }
//        public int Rating { get; set; }
//        public bool IsAuthorized { get; set; }
//    */
//
//    var RecruitmentID: Int? = null
//    var StudentID:Int?=null
//    var IsCancelled:Boolean?=null
//    var CheckInTime: LocalDateTime? = null
//    var CheckOutTime:LocalDateTime?=null
//    var IsLate:Boolean?=null
//    var IsOvertime:Boolean?=null
//    var StudentComment:String?=null
//    var StaffReview:String?=null
//    var Rating:Int?=null
//    var IsAuthorized:Boolean?=null
//
//    constructor(
//        RecruitmentID: Int?,
//        StudentID: Int?,
//        IsCancelled: Boolean?,
//        CheckInTime: LocalDateTime?,
//        CheckOutTime: LocalDateTime?,
//        IsLate: Boolean?,
//        IsOvertime: Boolean?,
//        StudentComment: String?,
//        StaffReview: String?,
//        Rating: Int?,
//        IsAuthorized: Boolean?
//    ) {
//        this.RecruitmentID = RecruitmentID
//        this.StudentID = StudentID
//        this.IsCancelled = IsCancelled
//        this.CheckInTime = CheckInTime
//        this.CheckOutTime = CheckOutTime
//        this.IsLate = IsLate
//        this.IsOvertime = IsOvertime
//        this.StudentComment = StudentComment
//        this.StaffReview = StaffReview
//        this.Rating = Rating
//        this.IsAuthorized = IsAuthorized
//    }
//
//    constructor()
////    constructor(RecruitmentID: Int?, StudentID: Int?) {
////        this.RecruitmentID = RecruitmentID
////        this.StudentID = StudentID
////        this.IsCancelled = false
////        this.CheckInTime = LocalDateTime.of(2000, 1, 1, 0, 0)
////        this.CheckOutTime = LocalDateTime.of(2000, 1, 1, 0, 0)
////        this.IsLate = false
////        this.IsOvertime = false
////        this.StudentComment = ""
////        this.StaffReview = ""
////        this.Rating = 0
////        this.IsAuthorized = false
////
////    }
//
//}
package com.example.jbsb4.remote

import com.example.jbsb4.Model.Application
import com.example.jbsb4.Model.Assignment
import com.example.jbsb4.Model.AvailabilityList
import com.example.jbsb4.Model.JBSBWorksheet
import com.example.jbsb4.Model.LeaderboardItem
import com.example.jbsb4.Model.PassiveAssignment
import com.example.jbsb4.Model.Recruitment
import com.example.jbsb4.Model.Shift
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {
//    @GET("api/Shift")
//    fun getShift() : Call<List<Shift>>
    @GET("api/Shift")
    suspend fun getShift() : Response<List<Shift>>

    @GET("api/Shift/StudentShift")
    fun getStudentShift(@Query("studentId") studentId: Int) : Call<List<Recruitment>>

    @GET("getAssignments/{StudentID}")
    fun getAssignmentsByStudentID(@Path("StudentID") studentID: Int): Call<List<Recruitment>>

    @PUT("api/Shift/CheckIn")
    fun checkIn(@Query("recruitmentId") recruitmentId: Int, @Query("studentId") studentId: Int, @Query("latitude") latitude: Double, @Query("longitude") longitude: Double) : Call<Shift>

    @PUT("api/Shift/CheckOut")
    fun checkOut(@Query("recruitmentId") recruitmentId: Int, @Query("studentId") studentId: Int) : Call<Shift>

    @GET("api/Worksheet/GetWorksheet")
    fun getJSBSworksheet(@Query("studentId") studentId: Int, @Query("month") month: Int, @Query("year") year: Int) : Call<JBSBWorksheet>

    @GET("api/Worksheet/generatepdf")
    fun sendWorksheet(@Query("studentId") studentId: Int, @Query("month") month: Int, @Query("year") year: Int) : Call<ResponseBody>
    @GET("api/Worksheet/Leaderboard")
    fun getLeaderboard(@Query("month") month: Int, @Query("year") year: Int) : Call<List<LeaderboardItem>>

    @GET("api/Worksheet/HoursWorked")
    fun getHoursWorked(@Query("studentId") studentId: Int) : Call<List<Double>>

    @GET("api/Worksheet/OverallHours")
    fun getOverallHours(@Query("studentId") studentId: Int) : Call<Map<String, Int>>

    @GET("recruitment/Recruitment/student/{StudentID}")
    fun getRecruitmentDataByStudentID(@Path("StudentID") studentID: Int): Call<List<Recruitment>>

    @POST("applyJob/{RecruitmentID}/{StudentID}")
    fun applyJob(@Path("RecruitmentID") recruitmentID: Int, @Path("StudentID") studentID: Int): Call <Application>

    @POST("cancelRequest/{StudentID}/{RecruitmentID}")
    fun cancelRequest(@Path("StudentID") studentID: Int, @Path("RecruitmentID") recruitmentID: Int): Call <Assignment>

    @GET("getPassiveAssignments/{StudentID}")
    fun getPassiveAssignmentsByStudent(@Path("StudentID") studentID: Int): Call<List<PassiveAssignment>>

    @POST("acceptJobAssignment/{RecruitmentID}/{StudentID}")
    fun acceptJobAssignment(@Path("RecruitmentID") recruitmentID: Int, @Path("StudentID") studentID: Int): Call <Assignment>

    @DELETE("rejectAssignment/{RecruitmentID}/{StudentID}")
    fun rejectJobAssignment(@Path("RecruitmentID") recruitmentID: Int, @Path("StudentID") studentID: Int):Call<ResponseBody>

    @POST("updateUnavailability/{StudentID}/{DateAvail}/{HourOfDay}")
    fun updateUnavailability(@Path("StudentID") studentID: Int, @Path("DateAvail") dateAvail: String, @Path("HourOfDay") hourOfDay: String): Call <ResponseBody>

    @GET("getUpcomingUnavailability/{StudentID}")
    fun getUpcomingUnavailabilityByStudentID(@Path("StudentID") studentID: Int): Call<List<AvailabilityList>>

    @DELETE("deleteUnavailability/{StudentID}/{DateAvail}/{HourOfDay}")
    fun deleteUnavailability(@Path("StudentID") studentID: Int, @Path("DateAvail") dateAvail: String, @Path("HourOfDay") hourOfDay: String): Call <ResponseBody>
}

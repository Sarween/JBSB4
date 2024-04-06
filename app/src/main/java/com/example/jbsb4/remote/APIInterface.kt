package com.example.jbsb4.remote

import android.location.Location
import com.example.jbsb4.Model.JBSBWorksheet
import com.example.jbsb4.Model.LeaderboardItem
import com.example.jbsb4.Model.Recruitment
import com.example.jbsb4.Model.Shift
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import retrofit2.http.Streaming

interface APIInterface {
//    @GET("api/Shift")
//    fun getShift() : Call<List<Shift>>
    @POST("api/Student/login")
    fun login(@Query("stuID") stuID: Int, @Query("password") password: String) : Call<ResponseBody>

    @GET("api/Shift")
    suspend fun getShift() : Response<List<Shift>>

    @GET("api/Shift/StudentShift")
    fun getStudentShift(@Query("studentId") studentId: Int) : Call<List<Recruitment>>
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
    @PUT("api/Shift/Comment")
    fun comment(@Query("recruitmentId") recruitmentId: Int, @Query("studentId") studentId: Int, @Query("comment") comment: String) : Call<Shift>

}

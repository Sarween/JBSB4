package com.example.jbsb4.remote

import com.example.jbsb4.Model.Shift
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface APIInterface {
//    @GET("api/Shift")
//    fun getShift() : Call<List<Shift>>
    @GET("api/Shift")
    suspend fun getShift() : Response<List<Shift>>
}

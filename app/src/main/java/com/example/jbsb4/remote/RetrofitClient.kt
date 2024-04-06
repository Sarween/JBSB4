package com.example.jbsb4.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    val baseUrl = "http://10.0.2.2:5045/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
//        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

//    val baseUrl = "http://localhost:5012/"
//    val baseUrl = "http://10.0.2.2:5012/"
//    val baseUrl = "http://10.107.10.195:5012/"

    fun getInstance(): Retrofit {
        return retrofit
    }

//    fun getInstance(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            // we need to add converter factory to
//            // convert JSON object to Java object
//            .build()
//    }

//    object ApiClient {
//        val apiService: IMyAPI by lazy {
//            RetrofitClient.retrofit.create(IMyAPI::class.java)
//        }
//    }

//    private var instance: Retrofit?=null
//
//    fun getInstance():Retrofit{
//        if(instance == null)
//            instance = Retrofit.Builder().baseUrl("http://localhost:5012/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                .build()
//        return instance!!
//    }
}
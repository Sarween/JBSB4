package com.example.jbsb4.adapter

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Model.Recruitment
import com.example.jbsb4.Model.Shift
import com.example.jbsb4.R
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class RecruitmentAdapter(val context: Context, val recruitmentList: List<Recruitment>, val activity: Activity) : RecyclerView.Adapter<RecruitmentAdapter.RecruitmentViewHolder>() {
    // Override getView() to bind data to the list item view
    // Update the constructor to accept Response<List<Shift>> type

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    class RecruitmentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var shiftDate: TextView
        var startTime: TextView
        var endTime: TextView
        //        private val endTime: TextView = itemView.findViewById(R.id.timeTextView)
        var location: TextView
        var checkInbtn: Button
        var checkOutbtn: Button



        init {
            shiftDate = itemView.findViewById(R.id.date)
            startTime = itemView.findViewById(R.id.checkIn)
            endTime = itemView.findViewById(R.id.checkOut)
            location = itemView.findViewById(R.id.location)
            checkInbtn = itemView.findViewById(R.id.checkInBtn)
            checkOutbtn = itemView.findViewById(R.id.checkOutBtn)

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_shift, parent, false)
        return RecruitmentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecruitmentViewHolder, position: Int) {

        holder.shiftDate.text = formatDate(recruitmentList[position].jobShiftDate.toString())
        holder.startTime.text = getTimeFromString(recruitmentList[position].startTime.toString())
        holder.endTime.text = getTimeFromString(recruitmentList[position].endTime.toString())
        holder.location.text = recruitmentList[position].jobLocation.toString()
        val id = recruitmentList[position].recruitmentID.toString()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        // Check in button
        holder.checkInbtn.setOnClickListener {
            println("a")
            // Check if the user has granted the app permission to access their location.
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                println("d")
                // Request the permission from the user.
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    100
                )
            } else {
                println("b")
                // Get the user's location.
                println(fusedLocationClient.lastLocation.toString())
                fusedLocationClient.getLastLocation()
                    .addOnSuccessListener { location ->
                        // If the location is not null, display it in a text view.
                        if (location != null) {
                            println(location.latitude)
                            println(location.longitude)

                            // Check in function
                            val shiftApi =
                                RetrofitClient.getInstance().create(APIInterface::class.java)

                            // Calling check in api
                            val checkInResponse = shiftApi.checkIn(
                                recruitmentList[position].recruitmentID,
                                PreferenceHelper.ID_KEY,
                                location.latitude,
                                location.longitude
                            )

                            checkInResponse.enqueue(object : Callback<Shift> {
                                override fun onResponse(
                                    call: Call<Shift>,
                                    response: Response<Shift>
                                ) {
                                    if (response.isSuccessful) {
                                        val checkInResponse = response.body()

                                        if (checkInResponse != null) {
                                            val x = placeTimeFromString(checkInResponse.checkInTime)
                                            Toast.makeText(activity, "Checked in at $x", Toast.LENGTH_LONG).show()

                                        }
                                    }
                                    else {
                                        val errorBody = response.errorBody()?.string()
                                        // Parse the error body to get the error message
                                        val errorMessage = errorBody ?: "Unknown error"
                                        // Display or log the error message
                                        println(errorMessage)

                                        Toast.makeText(activity, "$errorMessage", Toast.LENGTH_SHORT).show()
                                    }


                                }

                                override fun onFailure(call: Call<Shift>, t: Throwable) {
                                    Log.d("MainActivity", "onFailure " + t.message)
                                }
                            })

                        }
                        else {
                            Toast.makeText(activity, "Check internet connection", Toast.LENGTH_SHORT).show()
                            println("no location")
                        }


                    } // Listener
            }
        }

        holder.checkOutbtn.setOnClickListener {
            val shiftApi =
                RetrofitClient.getInstance().create(APIInterface::class.java)

            // Calling check in api
            val checkOutResponse = shiftApi.checkOut(
                recruitmentList[position].recruitmentID,
                PreferenceHelper.ID_KEY,
            )

            checkOutResponse.enqueue(object : Callback<Shift> {
                override fun onResponse(
                    call: Call<Shift>,
                    response: Response<Shift>
                ) {
                    if (response.isSuccessful) {
                        val checkOutResponse = response.body()


                        if (checkOutResponse != null) {
                            val x = placeTimeFromString(checkOutResponse.checkOutTime)
                            Toast.makeText(activity, "Checked out at $x", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        val errorBody = response.errorBody()?.string()
                        // Parse the error body to get the error message
                        val errorMessage = errorBody ?: "Unknown error"
                        // Display or log the error message
                        println(errorMessage)

                        Toast.makeText(activity, "$errorMessage", Toast.LENGTH_SHORT).show()
                    }


                }

                override fun onFailure(call: Call<Shift>, t: Throwable) {
                    Log.d("MainActivity", "onFailure " + t.message)
                }
            })


        }


    }


    override fun getItemCount(): Int {
        return recruitmentList.size
    }

    fun placeTimeFromString(dateTimeString: String): String {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME // Using ISO-8601 format for parsing
        val offsetDateTime = OffsetDateTime.parse(dateTimeString, formatter)

        val time = offsetDateTime.toLocalTime() // Extract the time part
        val str = time.toString()

        val parts = str.split(".")
        val result = parts.firstOrNull() ?: str

        return result // Return the time as a string
    }

    fun getTimeFromString(dateTimeString: String): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val dateTime = LocalTime.parse(dateTimeString, formatter)

        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        val formattedTime = dateTime.format(timeFormatter)

        return formattedTime // Return the time as a string
    }

    fun formatDate(inputDate: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val date = LocalDate.parse(inputDate, inputFormatter)
        val formattedDate = date.format(outputFormatter)

        return formattedDate
    }


}

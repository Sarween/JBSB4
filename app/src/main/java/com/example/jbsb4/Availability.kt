package com.example.jbsb4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.DatePickerDialog
import android.content.Intent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class Availability : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_availability)
//    }

    private lateinit var btnDatePicker: Button
    private lateinit var tvSelectedDate: TextView
    private lateinit var submitBtn: Button
    private val calendar = Calendar.getInstance()
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_availability)

        // Initialize views
        btnDatePicker = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        spinner = findViewById(R.id.timeSpinner)
        submitBtn = findViewById(R.id.submitBtn)

        val backBtn: ImageView = findViewById(R.id.back)

        backBtn.setOnClickListener(){
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        val listTimes = listOf("08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00",
            "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00")

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listTimes)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

//        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
//            over
//        }

        // Set a click listener on the "Select Date" button
        btnDatePicker.setOnClickListener {
            // Show the DatePicker dialog
            showDatePicker()
        }

        submitBtn.setOnClickListener {
            performUpdateUnavailability()
        }
    }
    private fun showDatePicker() {
        // Create a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Create a new Calendar instance to hold the selected date
                val selectedDate = Calendar.getInstance()
                // Set the selected date using the values received from the DatePicker dialog
                selectedDate.set(year, monthOfYear, dayOfMonth)
                // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                // Format the selected date into a string
                val formattedDate = dateFormat.format(selectedDate.time)
                // Update the TextView to display the selected date with the "Selected Date: " prefix
                tvSelectedDate.text = "Selected Date: $formattedDate"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the DatePicker dialog
        datePickerDialog.show()
    }

    private fun performUpdateUnavailability() {
        val selectedDate = tvSelectedDate.text.toString().replace("Selected Date: ", "")
        val selectedTime = spinner.selectedItem.toString()

        // Assuming you have an instance of your API service here, e.g., apiService

        val api = RetrofitClient.getInstance().create(APIInterface::class.java)
//        val call = apiService.updateUnavailability(PreferenceHelper.ID_KEY, selectedDate, selectedTime)

        val submitResponse = api.updateUnavailability(
            PreferenceHelper.ID_KEY,
            selectedDate,
            selectedTime
        )

        submitResponse.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    println("Unavailability updated")
                } else {
                    // Handle failure
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle API call failure
            }
        })
    }

}
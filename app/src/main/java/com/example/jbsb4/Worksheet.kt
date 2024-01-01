package com.example.jbsb4

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Model.JBSBWorksheet
import com.example.jbsb4.Model.LeaderboardItem
import com.example.jbsb4.Model.Recruitment
import com.example.jbsb4.Model.WorksheetItem
import com.example.jbsb4.adapter.LeaderboardAdapter
import com.example.jbsb4.adapter.RecruitmentAdapter
import com.example.jbsb4.adapter.WorksheetAdapter
import com.example.jbsb4.fragments.MonthYearPickerDialog
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import com.google.android.gms.location.FusedLocationProviderClient
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.github.barteksc.pdfviewer.PDFView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate

class Worksheet : AppCompatActivity(){

    private lateinit var tvDatePicker: TextView
    private lateinit var btnDatePicker: Button
    private lateinit var btnDownload: Button
    private lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: WorksheetAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var iMyAPI: APIInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worksheet)

        tvDatePicker = findViewById(R.id.tvDate)
        btnDatePicker = findViewById(R.id.btnDatePicker)
        btnDownload = findViewById(R.id.btnDownload)



        val backBtn: ImageView = findViewById(R.id.back_worksheet)

        backBtn.setOnClickListener() {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        // Access recycler view using synthetic binding
        val worksheetView :RecyclerView  = findViewById(R.id.recyclerView_worksheet)
        worksheetView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        worksheetView.layoutManager = linearLayoutManager

        // Declaring retrofit instance
        iMyAPI = RetrofitClient.getInstance().create(APIInterface::class.java)
        // Creating API
        val worksheetApi = RetrofitClient.getInstance().create(APIInterface::class.java)
        // intialize month and year
        val calendar = Calendar.getInstance()
        val mon = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)
        updateLable(calendar)
        // Replace this with your actual API response
        val worksheetData = worksheetApi.getJSBSworksheet(PreferenceHelper.ID_KEY, mon, currentYear)

        worksheetData.enqueue(object : Callback<JBSBWorksheet> {
            override fun onResponse(
                call: Call<JBSBWorksheet>,
                response: Response<JBSBWorksheet>
            ) {
                val responseBody = response.body()!!

                // Calling Adapter
                myAdapter = WorksheetAdapter(baseContext, responseBody.worksheet, this@Worksheet)
                myAdapter.notifyDataSetChanged()
                worksheetView.adapter = myAdapter

            }
            override fun onFailure(call: Call<JBSBWorksheet>, t: Throwable) {
                Log.d("MainActivity", "onFailure "+t.message)
            }
        })



        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
            val test = month + 1
            fetchDataForRecyclerView(test, myCalendar.get(Calendar.YEAR), worksheetView)
        }

        btnDatePicker.setOnClickListener {
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),  myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnDownload.setOnClickListener() {
            val test = myCalendar.get(Calendar.MONTH) + 1
            val sendpdf = worksheetApi.sendWorksheet(PreferenceHelper.ID_KEY, test, myCalendar.get(Calendar.YEAR))
            sendpdf.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Toast.makeText(this@Worksheet, "Check email", Toast.LENGTH_SHORT).show()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("MainActivity", "onFailure "+t.message)
                    Toast.makeText(this@Worksheet, "Fail to send email", Toast.LENGTH_SHORT).show()
                }
            })
        }

        iMyAPI = RetrofitClient.getInstance().create(APIInterface::class.java)
        val api = RetrofitClient.getInstance().create(APIInterface::class.java)


    }


    private fun fetchDataForRecyclerView(month: Int, year: Int, worksheetView :RecyclerView) {
        val worksheetApi = RetrofitClient.getInstance().create(APIInterface::class.java)
        val worksheetData = worksheetApi.getJSBSworksheet(PreferenceHelper.ID_KEY, month, year)

        worksheetData.enqueue(object : Callback<JBSBWorksheet> {
            override fun onResponse(
                call: Call<JBSBWorksheet>,
                response: Response<JBSBWorksheet>
            ) {
                val responseBody = response.body()!!
                // Calling Adapter
                myAdapter = WorksheetAdapter(baseContext, responseBody.worksheet, this@Worksheet)
                myAdapter.notifyDataSetChanged()
                worksheetView.adapter = myAdapter

            }

            override fun onFailure(call: Call<JBSBWorksheet>, t: Throwable) {
                Log.d("MainActivity", "onFailure " + t.message)
            }
        })
    }

        // Perform data fetching based on the selected date
        // For example, fetch data from your database or API

//        // Simulated data loading delay (Replace this with your data retrieval mechanism)
//        Handler(Looper.getMainLooper()).postDelayed({
//            val dataForDate = /* Your fetched data based on the selected date */
//                updateRecyclerView(dataForDate)
//        }, 1000) // Simulated delay of 1 second
//    }

//    private fun updateRecyclerView(data: List<WorksheetItem>) {
//        // Update RecyclerView with the fetched data
//        adapter.updateData(data)
//    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "MMM yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))

    }


}
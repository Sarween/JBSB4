package com.example.jbsb4

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Model.JBSBWorksheet
import com.example.jbsb4.Model.LeaderboardItem
import com.example.jbsb4.adapter.LeaderboardAdapter
import com.example.jbsb4.adapter.WorksheetAdapter
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Leaderboard : AppCompatActivity() {

    private lateinit var tvDatePicker: TextView
    private lateinit var btnDatePicker: Button
    private lateinit var leaderView: RecyclerView
    lateinit var myAdapter: LeaderboardAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var iMyAPI: APIInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        tvDatePicker = findViewById(R.id.tvDateLeader)
        btnDatePicker = findViewById(R.id.btnDatePickerLeader)

        val backBtn: ImageView = findViewById(R.id.back_leaderboard)

        backBtn.setOnClickListener() {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        // Access recycler view using synthetic binding
//        val leaderView :RecyclerView  = findViewById(R.id.recyclerView_leaderboard)
        leaderView = findViewById(R.id.recyclerView_leaderboard)
        leaderView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        leaderView.layoutManager = linearLayoutManager

        // Declaring retrofit instance
        iMyAPI = RetrofitClient.getInstance().create(APIInterface::class.java)
        // Creating API
        val leaderboardApi = RetrofitClient.getInstance().create(APIInterface::class.java)
        // intialize month and year
        val calendar = Calendar.getInstance()
        val mon = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)
        updateLable(calendar)
        // Replace this with your actual API response
        val leaderboardData = leaderboardApi.getLeaderboard(mon, currentYear)

        leaderboardData.enqueue(object : Callback<List<LeaderboardItem>> {
            override fun onResponse(
                call: Call<List<LeaderboardItem>>,
                response: Response<List<LeaderboardItem>>
            ) {
                val responseBody = response.body()!!

                if (responseBody != null) {
                    // Calling Adapter
                    myAdapter = LeaderboardAdapter(baseContext, responseBody, this@Leaderboard)
                    myAdapter.notifyDataSetChanged()
                    leaderView.adapter = myAdapter
                }
                else {
                    Toast.makeText(this@Leaderboard, "No student participated", Toast.LENGTH_LONG).show()
                }


            }
            override fun onFailure(call: Call<List<LeaderboardItem>>, t: Throwable) {
                Toast.makeText(this@Leaderboard, "No student participated", Toast.LENGTH_LONG).show()
            }
        })

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
            val test = month + 1
            fetchDataForRecyclerView(test, myCalendar.get(Calendar.YEAR), leaderView)
        }

        btnDatePicker.setOnClickListener {
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),  myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        iMyAPI = RetrofitClient.getInstance().create(APIInterface::class.java)
        val api = RetrofitClient.getInstance().create(APIInterface::class.java)

    }

    private fun fetchDataForRecyclerView(month: Int, year: Int, leaderboardView :RecyclerView) {
        val api = RetrofitClient.getInstance().create(APIInterface::class.java)
        val leaderboardData = api.getLeaderboard(month, year)

        leaderboardData.enqueue(object : Callback<List<LeaderboardItem>> {
            override fun onResponse(
                call: Call<List<LeaderboardItem>>,
                response: Response<List<LeaderboardItem>>
            ) {
                val responseBody = response.body()!!

                if (responseBody != null) {
                    leaderboardView.visibility = View.VISIBLE
                    // Calling Adapter
                    myAdapter = LeaderboardAdapter(baseContext, responseBody, this@Leaderboard)
                    myAdapter.notifyDataSetChanged()
                    leaderboardView.adapter = myAdapter
                } else {

                    showNoDataDialog(this@Leaderboard)
//                    Toast.makeText(this@Leaderboard, "No student participated", Toast.LENGTH_LONG).show()
                }


            }

            override fun onFailure(call: Call<List<LeaderboardItem>>, t: Throwable) {
                showNoDataDialog(this@Leaderboard)
//                Toast.makeText(this@Leaderboard, "No student participated", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "MMM yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDatePicker.setText(sdf.format(myCalendar.time))

    }

    private fun showNoDataDialog(context: Context) {

        val dialog = Dialog(context) // Use 'this' for Activity or 'requireContext()' for Fragment
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_empty) // Create a layout for your custom dialog
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnOk: Button = dialog.findViewById(R.id.btnOk)
        btnOk.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, Leaderboard::class.java)
            startActivity(intent)
        }

        dialog.show()
    }

//    private fun fetchDataForRecyclerView(month: Int, year: Int, worksheetView :RecyclerView) {
//        val worksheetApi = RetrofitClient.getInstance().create(APIInterface::class.java)
//        val worksheetData = worksheetApi.getJSBSworksheet(PreferenceHelper.ID_KEY, month, year)
//
//        worksheetData.enqueue(object : Callback<JBSBWorksheet> {
//            override fun onResponse(
//                call: Call<JBSBWorksheet>,
//                response: Response<JBSBWorksheet>
//            ) {
//                val responseBody = response.body()!!
//                // Calling Adapter
//                myAdapter = WorksheetAdapter(baseContext, responseBody.worksheet, this@Leaderboard)
//                myAdapter.notifyDataSetChanged()
//                worksheetView.adapter = myAdapter
//
//            }
//
//            override fun onFailure(call: Call<JBSBWorksheet>, t: Throwable) {
//                Log.d("MainActivity", "onFailure " + t.message)
//            }
//        })
//    }
}
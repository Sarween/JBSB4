package com.example.jbsb4

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.jbsb4.Model.JBSBWorksheet
import com.example.jbsb4.adapter.WorksheetAdapter
import com.example.jbsb4.databinding.ActivityDashboardBinding
import com.example.jbsb4.fragments.HomeFragment
import com.example.jbsb4.fragments.LeaderboardFragment
import com.example.jbsb4.fragments.PerformanceFragment
import com.example.jbsb4.fragments.WorksheetFragment
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.ceil

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_dashboard)

        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val timeLeft: TextView = findViewById(R.id.timeLeft)
        val progressPercent: TextView = findViewById(R.id.progressPercent)
        val aveRating: TextView = findViewById(R.id.aveRating)
        val totalEarning: TextView = findViewById(R.id.earnings)

        progressBar.max = 10000
        val max: Int = progressBar.max
        // Creating API
        val api = RetrofitClient.getInstance().create(APIInterface::class.java)

//        val data = api.getHoursWorked(PreferenceHelper.ID_KEY)
//        var currentProgress = 0;
//        data.enqueue(object : Callback<List<Double>> {
//            override fun onResponse(
//                call: Call<List<Double>>,
//                response: Response<List<Double>>
//            ) {
//                val responseBody = response.body()!!
//                currentProgress = responseBody.get(0).toInt()
//                ObjectAnimator.ofInt(progressBar,"progress", currentProgress).setDuration(1000).start()
//                val rTime = (max-currentProgress).toDouble()/60
//                val remainingTime: Int = ceil(rTime).toInt()
//                timeLeft.text = remainingTime.toString() + " hours left to go!"
//                val progressPercentage: Int = (currentProgress.toFloat() / max.toFloat() * 100).toInt()
//                progressPercent.text = progressPercentage.toString() + "%"
//
//                aveRating.text = responseBody.get(1).toString()
//                totalEarning.text = "RM " + responseBody.get(2).toString()
//
//            }
//            override fun onFailure(call: Call<List<Double>>, t: Throwable) {
//                println("shit")
//                Log.d("MainActivity", "onFailure "+t.message)
//            }
//        })



        val attendanceBtn: CardView = findViewById(R.id.jobCard)

        attendanceBtn.setOnClickListener() {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        val worksheetBtn: CardView = findViewById(R.id.jobCard2)

        worksheetBtn.setOnClickListener() {
            val intent = Intent(this, AvailabilityList::class.java)
            startActivity(intent)
        }

        val recruitmentBtn: CardView = findViewById(R.id.jobCard4)

        recruitmentBtn.setOnClickListener() {
            val intent = Intent(this, activity_recruitments::class.java)
            startActivity(intent)
        }

        val leaderboardBtn: CardView = findViewById(R.id.jobCard3)

        leaderboardBtn.setOnClickListener() {
            val intent = Intent(this, assignments::class.java)
            startActivity(intent)
        }


//        replaceFragment(HomeFragment())
//
//        binding.bottomNavbar.setOnItemSelectedListener {
//
//            when(it.itemId) {
//                R.id.home -> replaceFragment(HomeFragment())
//                R.id.leaderboard -> replaceFragment(LeaderboardFragment())
//                R.id.worksheet -> replaceFragment(WorksheetFragment())
//                R.id.performance -> replaceFragment(PerformanceFragment())
//
//                else -> {
//
//                }
//
//            }
//
//            true
//        }

    }

//    private fun replaceFragment(fragment: Fragment) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragment_container, fragment)
//        transaction.commit()
//
//    }

}
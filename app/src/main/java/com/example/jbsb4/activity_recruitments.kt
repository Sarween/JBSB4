package com.example.jbsb4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Model.LeaderboardItem
import com.example.jbsb4.Model.Recruitment
import com.example.jbsb4.adapter.LeaderboardAdapter
import com.example.jbsb4.adapter.RecruitmentAdapter
import com.example.jbsb4.adapter.RecruitmentListAdapter
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class activity_recruitments : AppCompatActivity() {

    lateinit var iMyAPI: APIInterface
    lateinit var myAdapter: RecruitmentListAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruitments)

        val recruitmentListView: RecyclerView = findViewById((R.id.recyclerView_recruitmentList))
        val backBtn: ImageView = findViewById(R.id.back)

        backBtn.setOnClickListener(){
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
//            finish()
        }

        myAdapter = RecruitmentListAdapter(baseContext, emptyList(), this)
        recruitmentListView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@activity_recruitments)
            adapter = myAdapter
        }

//        recruitmentListView.setHasFixedSize(true)
//        linearLayoutManager = LinearLayoutManager(this)
//        recruitmentListView.layoutManager = linearLayoutManager




        iMyAPI = RetrofitClient.getInstance().create(APIInterface::class.java)
        // Creating API
        val api = RetrofitClient.getInstance().create(APIInterface::class.java)

        // Replace this with your actual API response
        val recruitmentListData = api.getRecruitmentDataByStudentID(PreferenceHelper.ID_KEY)

        recruitmentListData.enqueue(object : Callback<List<Recruitment>> {
            override fun onResponse(
                call: Call<List<Recruitment>>,
                response: Response<List<Recruitment>>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null){
                    myAdapter.RecruitmentList = responseBody
                    myAdapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this@activity_recruitments, "Please update your availability to be eligible for job application.", Toast.LENGTH_LONG).show()
                }

//                if (response.isSuccessful && response.body() != null) {
//                    myAdapter.RecruitmentList = response.body()!!
//                    myAdapter.notifyDataSetChanged()
//                } else {
//                    Toast.makeText(this@activity_recruitments, "Please update your availability to be eligible for applying for a job or getting an assigned job", Toast.LENGTH_LONG).show()
//                }


//                for (recruitment in responseBody) {
//                    println("ID: ${recruitment.recruitmentID}, Job: ${recruitment.jobDescription}")
//                }
//
//                myAdapter = RecruitmentListAdapter(baseContext, responseBody, this@activity_recruitments)
//                myAdapter.notifyDataSetChanged()
//                recruitmentListView.adapter = myAdapter

//                val numShift = myAdapter.itemCount
//                shiftNumText.text = "You have " + numShift + " shifts left"
            }
            override fun onFailure(call: Call<List<Recruitment>>, t: Throwable) {
                Log.d("MainActivity", "onFailure "+t.message)
            }
        })
    }
}
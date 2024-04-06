package com.example.jbsb4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Model.PassiveAssignment
import com.example.jbsb4.adapter.PassiveAssignmentAdapter
import com.example.jbsb4.adapter.RecruitmentListAdapter
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class assignments : AppCompatActivity() {

    lateinit var iMyAPI: APIInterface
    lateinit var myAdapter: PassiveAssignmentAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assignments)

        val assignmentListView: RecyclerView = findViewById((R.id.recyclerView_assignmentList))
        val backBtn: ImageView = findViewById(R.id.back)

        backBtn.setOnClickListener(){
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        myAdapter = PassiveAssignmentAdapter(baseContext, emptyList(), this)
        assignmentListView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@assignments)
            adapter = myAdapter
        }

        iMyAPI = RetrofitClient.getInstance().create(APIInterface::class.java)
        val api = RetrofitClient.getInstance().create(APIInterface::class.java)

        val passiveAssignmentsData = api.getPassiveAssignmentsByStudent(PreferenceHelper.ID_KEY)

        passiveAssignmentsData.enqueue(object: Callback<List<PassiveAssignment>> {
            override fun onResponse(
                call: Call<List<PassiveAssignment>>,
                response: Response<List<PassiveAssignment>>
            ) {
                val responseBody = response.body()!!

                myAdapter = PassiveAssignmentAdapter(baseContext, responseBody, this@assignments)
                myAdapter.notifyDataSetChanged()
                assignmentListView.adapter = myAdapter
            }

            override fun onFailure(call: Call<List<PassiveAssignment>>, t: Throwable) {
                println("Failed")
            }
        })
    }
}
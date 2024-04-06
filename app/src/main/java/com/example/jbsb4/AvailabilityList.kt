package com.example.jbsb4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Model.AvailabilityList
import com.example.jbsb4.adapter.AvailabilityAdapter
import com.example.jbsb4.adapter.RecruitmentListAdapter
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AvailabilityList : AppCompatActivity() {

    lateinit var iMyAPI: APIInterface
    lateinit var myAdapter: AvailabilityAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_availability_list)

        val unavailabilityListView: RecyclerView = findViewById((R.id.recyclerView_unavailabilityList))
        val backBtn: ImageView = findViewById(R.id.back)

        backBtn.setOnClickListener(){
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        myAdapter = AvailabilityAdapter(baseContext, emptyList(), this)
        unavailabilityListView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AvailabilityList)
            adapter = myAdapter
        }

        iMyAPI = RetrofitClient.getInstance().create(APIInterface::class.java)
        // Creating API
        val api = RetrofitClient.getInstance().create(APIInterface::class.java)

        val addUnavailabilityButton: Button = findViewById(R.id.addUnavailabilityBtn)
        addUnavailabilityButton.setOnClickListener {
            val intent = Intent(this, Availability::class.java)
            startActivity(intent)
        }

        val unavailabilityListData = api.getUpcomingUnavailabilityByStudentID(PreferenceHelper.ID_KEY)

        unavailabilityListData.enqueue(object: Callback<List<com.example.jbsb4.Model.AvailabilityList>> {
            override fun onResponse(
                call: Call<List<AvailabilityList>>,
                response: Response<List<AvailabilityList>>
            ) {
                val responseBody = response.body()!!

                println("Here")


                myAdapter = AvailabilityAdapter(baseContext, responseBody, this@AvailabilityList)
                myAdapter.notifyDataSetChanged()
                unavailabilityListView.adapter = myAdapter
            }

            override fun onFailure(call: Call<List<AvailabilityList>>, t: Throwable) {
                println("Failed")
            }
        })
    }
}
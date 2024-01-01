package com.example.jbsb4

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Model.Recruitment
import com.example.jbsb4.Model.Shift
import com.example.jbsb4.adapter.RecruitmentAdapter
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
//import com.google.android.gms.location.Priority
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class Home : AppCompatActivity() {

    private val TAG: String = "CHECK_RESPONSE"

    lateinit var iMyAPI: APIInterface
    var compositeDisposable = CompositeDisposable()
    lateinit var myAdapter: RecruitmentAdapter
    lateinit var linearLayoutManager: LinearLayoutManager


    private lateinit var fusedLocationClient: FusedLocationProviderClient
    // Get the button that you want to use to trigger the location request.


    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Access recycler view using synthetic binding
        val recruitmentView: RecyclerView = findViewById(R.id.recyclerView_recruitment)
        var shiftNumText: TextView = findViewById(R.id.no_shifts_message)

        val backBtn: ImageView = findViewById(R.id.back)

        backBtn.setOnClickListener() {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        recruitmentView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recruitmentView.layoutManager = linearLayoutManager



//        // Adding ListView
//        val listView = findViewById<ListView>(R.id.listView)
//        val names = arrayOf("A", "b", "c")
//
//        val arrayAdapter:ArrayAdapter<String> = ArrayAdapter(
//            this, android.R.layout.simple_list_item_1, names
//        )
//
//        listView.adapter = arrayAdapter
//
//        listView.setOnItemClickListener { adapterView, view, i, l -> Toast.makeText(this, "Item Selected" + names[i], Toast.LENGTH_LONG)}

        // Declaring retrofit instance
        iMyAPI = RetrofitClient.getInstance().create(APIInterface::class.java)
        // Creating API
        val shiftApi = RetrofitClient.getInstance().create(APIInterface::class.java)
//            val response = shiftApi.getShift()

        // Stopped here
        // Replace this with your actual API response
        val recruitmentData = shiftApi.getStudentShift(PreferenceHelper.ID_KEY)

        recruitmentData.enqueue(object : Callback<List<Recruitment>?> {
            override fun onResponse(
                call: Call<List<Recruitment>?>,
                response: Response<List<Recruitment>?>
            ) {
                val responseBody = response.body()!!

                // Calling Adapter
                myAdapter = RecruitmentAdapter(baseContext, responseBody, this@Home)
                myAdapter.notifyDataSetChanged()
                recruitmentView.adapter = myAdapter
                val numShift = myAdapter.itemCount
                shiftNumText.text = "You have " + numShift + " shifts left"

                // Part 1
//                val myStringBuilder = StringBuilder()
//                for (recruit in responseBody) {
//                    myStringBuilder.append(recruit.jobShiftDate)
//                    myStringBuilder.append("\n")
//                }
//                val lat: TextView = findViewById(R.id.latitude)
//                lat.text = myStringBuilder

            }

            override fun onFailure(call: Call<List<Recruitment>?>, t: Throwable) {
                recruitmentView.visibility = View.GONE
            }
        })

//        val checkInButton: Button = findViewById(R.id.checkInBtn45)

//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

//        val client = OkHttpClient()
//        val request = Request.Builder().url("https://localhost:5012/WeatherForecast").build()

//        // Check in button listener
//        checkInButton.setOnClickListener {
//            // Check if the user has granted the app permission to access their location.
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    android.Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // Request the permission from the user.
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                    100
//                )
//            } else {
//                // Get the user's location.
//                fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener { location ->
//                        // If the location is not null, display it in a text view.
//                        if (location != null) {
//                            println(location.latitude)
//                            println(location.longitude)
//                            val textView: TextView = findViewById(R.id.test_message)
//                            textView.text =
//                                "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
//                        }
//                    }
//            }
//        }





    }


}



//1
//        val shiftBtn: Button = findViewById(R.id.getShift)
//
//        shiftBtn.setOnClickListener {
//            GlobalScope.launch(Dispatchers.IO) {
//                val response = shiftApi.getShift()
//                if (response.isSuccessful) {
//                    for(shift in response.body()!!){
//                        Log.i(TAG, "getShift: ${shift.recruitmentID}")
//                    }
//                }
//            }
//        }
        // launching a new coroutine

//3
//        val checkInButton: Button = findViewById(R.id.checkInBtn45)
//        val lat: TextView = findViewById(R.id.latitude)
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

//        val client = OkHttpClient()
//        val request = Request.Builder().url("https://localhost:5012/WeatherForecast").build()
//2
//        var recruitID=1;
//        var stuID=1;
//
//        // Check in button listener
//        checkInButton.setOnClickListener {
//                        // Check if the user has granted the app permission to access their location.
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // Request the permission from the user.
//                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100)
//            } else {
//                // Get the user's location.
//                fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener { location ->
//                        // If the location is not null, display it in a text view.
//                        if (location != null) {
//                            println(location.latitude)
//                            println(location.longitude)
//                            val textView: TextView = findViewById(R.id.longitude)
//                            textView.text = "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
//                        }
//                    }
//            }




//           Solution
//            shiftApi.getShift().enqueue(object : Callback<List<Shift>>{
//                override fun onResponse(call: Call<List<Shift>>, response: Response<List<Shift>>) {
//                    if(response.isSuccessful){
//                        response.body()
//                        response.body()?.let {
//                            for (shift in it){
//                                Log.i(TAG,"onResponse: ${shift.toString()}")
//                            }
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Shift>>, t: Throwable) {
//                    Log.i(TAG, "onFailure: ${t.message}")
//                }
//
//            })

//            response.subscribe({ posts ->
//                // Handle the response successfully
//            }, { error ->
//                // Handle the error
//            })
//
//            // launching a new coroutine
//            GlobalScope.launch {
//                val response = shiftApi.getShift()
//                if (response != null)
//                // Checking the results
//                    Log.d("ayush: ", response.toString())
//            }

            // Attempt 1
//            val dialog = SpotsDialog.Builder().setContent(this@Home).build()
//
//            dialog.show();
//            compositeDisposable.addAll(iMyAPI.getShift()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                    {s ->
//                        Toast.makeText(this@Home,s, Toast.LENGTH_SHORT).show()
//
//                    },
//                    {t: Throwable? ->
//                        Toast.makeText(this@Home,t!!.message, Toast.LENGTH_SHORT).show()
//
//                    }))

//            // Start the AsyncTask.
//            MyAsyncTask().execute()

//            // Execute the Request object and get the response.
//            val response = client.newCall(request).execute()
//
//            // Handle the response and update the UI accordingly.
//            val body = response.body.toString()
//
//            // Update the UI with the response body.
//            println(body)








//    private class MyAsyncTask : AsyncTask<Void, Void, String>() {
//
//        override fun doInBackground(vararg params: Void?): String {
//            // Perform the network operation here.
//            val client = OkHttpClient()
//            val request = Request.Builder().url("http://localhost:5012/swagger/index.html/WeatherForecast").build()
//            val response = client.newCall(request).execute()
//            val body = response.body.toString()
//            return body
//        }
//
//        override fun onPostExecute(result: String?) {
//            // Update the UI with the results of the network operation here.
//            println(result)
//        }
//    }



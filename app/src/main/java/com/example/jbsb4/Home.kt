package com.example.jbsb4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.jbsb4.Model.Shift
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : AppCompatActivity() {

    private val TAG: String = "CHECK_RESPONSE"

    lateinit var iMyAPI: APIInterface
    var compositeDisposable = CompositeDisposable()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    // Get the button that you want to use to trigger the location request.


    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        iMyAPI = RetrofitClient.getInstance().create(APIInterface::class.java)

        val checkInButton: Button = findViewById(R.id.checkInBtn45)
        val lat: TextView = findViewById(R.id.latitude)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

//        val client = OkHttpClient()
//        val request = Request.Builder().url("https://localhost:5012/WeatherForecast").build()

        var recruitID=1;
        var stuID=1;
        checkInButton.setOnClickListener {
            val shiftApi = RetrofitClient.getInstance().create(APIInterface::class.java)
//            val response = shiftApi.getShift()

            // launching a new coroutine
            GlobalScope.launch(Dispatchers.IO) {
                val response = shiftApi.getShift()
                if (response.isSuccessful) {
                    for(shift in response.body()!!){
                        Log.i(TAG, "getShift: ${shift.recruitmentID}")
                    }
                }
            }



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

//            // Check if the user has granted the app permission to access their location.
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


        }


    }

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

}

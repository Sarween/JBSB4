package com.example.jbsb4.fragments


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
//import androidx.room.jarjarred.org.stringtemplate.v4.misc.Coordinate
import com.example.jbsb4.ConnectSQL
import com.example.jbsb4.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.withTimeoutOrNull
import java.sql.PreparedStatement
import java.sql.SQLException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var connctSQL = ConnectSQL()
    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding  get() = _homeBinding!!
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private var lat = 0.0
    private var long = 0.0
    private var checkInTime: Long = 0
    private var checkOutTime: Long = 0
//    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        println("onCreate")
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//        _homeBinding = FragmentHomeBinding.inflate(layoutInflater)
//        homeBinding.checkInBtn.setOnClickListener {
//            println("Hello")
//            // Check if the user has granted the app permission to access their location.
//            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // Request the permission from the user.
//                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100)
//            } else {
//                // Get the user's location.
//                fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener { location ->
//                        // If the location is not null, display it in a text view.
//                        if (location != null) {
////                            android:id="@+id/latitude"
//                            println(location.latitude)
//                            println(location.longitude)
//                            val textView: TextView = homeBinding.latitude
//                            textView.text = "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
//                        }
//                    }
//            }
//        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        println("onCreateView1")
        val view = homeBinding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("onViewCreated2")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

//        requestLocationPermission()

        // This part was commented
        homeBinding.checkInBtn.setOnClickListener {
            println("checkInBtn1")

            println("Q start")
            locationChecker(getLocation())
            println("Q end")
//            requestLocationUpdates()
            println("checkInBtn2")

        }

        homeBinding.checkOutBtn.setOnClickListener {
            checkOutTime = System.currentTimeMillis()
            val duration = checkOutTime - checkInTime
            println("duration : $duration")
            val totalSeconds = duration / 1000
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60

            println("Hours $hours and Minutes $minutes Seconds $seconds" )
            val durationToast = "Hours $hours and Minutes $minutes Seconds $seconds"
            // Display the duration to the user if needed
//            insertWorkRecord(WorkRecord(checkInTime, checkOutTime, duration))
            context?.let {
                Toast.makeText(it, durationToast, Toast.LENGTH_LONG).show()
            }
        }

        println("onViewCreated3")
    }

    private fun getLocation(): Location? {
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            println("Permission not granted")
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        println("Location Listener")
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
//        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null)
        // Retrieve the last known location
        val lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        if (lastKnownLocation != null) {
            println("Good")
            // Extract latitude and longitude from the location
            val latitude = lastKnownLocation.latitude
            val longitude = lastKnownLocation.longitude
            println(latitude)
            println(longitude)
            println("Good")

            // Create a LatLng object and return it
            LatLng(latitude, longitude)
        } else {
            println("Null location")
            // Return null if no location is available
            null
        }

        return lastKnownLocation
    }

    private fun locationChecker(location: Location?) {
        try {
            if (location != null) {
                lat = location.latitude.toString().toDouble()
                long = location.longitude.toString().toDouble()

                val bottomMost = 5.29614
                val upperMost = 5.29702
                val leftMost = 100.29065
                val rightMost = 100.29161
                println("Lat: $lat" )
                println("Long: $long" )
                //Latitude top to bottom               //Longitude left to right
                if ((lat in bottomMost..upperMost) && (long in leftMost..rightMost)) {
                    println("You're in")
//                    registerLocation(location)
                }
                else {
                    Toast.makeText(context, "Not at workplace!", Toast.LENGTH_LONG).show()
                }

            } else {
                println("Error: Location Empty")
            }

        }catch (ex: SQLException) {
            //KIV
            Toast.makeText(context, "Fail insert", Toast.LENGTH_LONG).show()
        }
    }

    private fun registerLocation(location: Location?) {
        val query: PreparedStatement = connctSQL.dbConn()?.prepareStatement("insert into Shift values (?,?,?,?,?,?,?,?,?)")!!
        // Set userId as an integer
        query.setInt(1, 123)
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val time = currentTime.format(formatter)
        println(time)
        query.setString(2, time)
        query.setString(3, "null")
        query.setString(4, "Document a report")
        query.setString(5, "He did a good job")
        query.setInt(6, 5)
        query.setInt(7, 1)
        val today = LocalDate.now().toString()
        query.setString(8, today)
        query.setString(9, "Data Scientist")
        query.executeUpdate();
        println("done")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }

    private fun showCustomToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_LONG).show()
        }
    }
//
//    private fun getLocation() {
//        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
//            != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100,)
//            return
//        }
//
//        val locationRequest = LocationRequest.create().apply {
//            intervalMillis.seconds.div(100)
//            Priority.PRIORITY_HIGH_ACCURACY
//        }
//        val location = fusedLocationProviderClient.lastLocation
//        location.addOnSuccessListener {
//            println("cat")
//            if (it!=null){
//                println("dog")
//
//                val textLatitude = "Latitude: " + it.latitude.toString()
//                val textLongitude = "Longitude: " + it.longitude.toString()
//                homeBinding.latitude.text = textLatitude
//                homeBinding.longitude.text = textLongitude
//
//            }
//        }




//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == locationPermissionCode) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
//                println("Permission Allowed")
//            }
//            else {
////                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
//                println("Permission Denied")
//            }
//        }
//    }


//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                println("requestPermissionLauncher")
//                requestLocationUpdates()
//            } else {
////                binding.latitude.text = "Location permission denied"
//            }
//        }
//
//    private fun requestLocationUpdates() {
//        println("requestLocationUpdates")
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            println("PERMISSION_GRANTED")
//            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
//                if (location != null) {
//                    println("location != null")
//                    val latitude = location.latitude.toString()
//                    val longitude = location.longitude.toString()
//                    binding.latitude.text = "Latitude: $latitude Hello"
//                    binding.longitude.text = "Longitude: $longitude"
//                } else {
//                    println("shit")
//
//                    binding.latitude.text = "Location not available"
//                    binding.longitude.text = "Location not available"
//                }
//            }
//        } else {
//            println("PERMISSION_!GRANTED")
//            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//    }
//
//    private fun requestLocationPermission() {
//        println("requestLocationPermission")
//        val permission = Manifest.permission.ACCESS_FINE_LOCATION
//        if (ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED) {
//            println("requestLocationUpdates1")
//            requestLocationUpdates()
//        } else {
//            requestPermissionLauncher.launch(permission)
//        }
//    }

//    fun test() {
//        print("go")
//        try {
//            val query: PreparedStatement = connctSQL.dbConn()?.prepareStatement("insert into Location values (?,?,?)")!!
//            print("oh yeah")
//            if (connctSQL == null){
//                print("shit")
//            }
//            query.setString(1, "shit")
////            query.setString(2, homeBinding.word.text.toString())
//            query.setString(3, "gang")
//            query.executeUpdate();
//            print("done")
//        }catch (ex: SQLException) {
//            //KIV
//            Toast.makeText(context, "Fail insert", Toast.LENGTH_LONG).show()
//        }
//    }

}
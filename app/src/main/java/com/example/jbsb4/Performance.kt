package com.example.jbsb4

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Performance : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding

    private lateinit var chart: BarChart
    lateinit var iMyAPI: APIInterface

    var profitValues = ArrayList<BarEntry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        val backBtn: ImageView = findViewById(R.id.back_performance)
        val totalHrs: TextView = findViewById(R.id.totalHrs)

        backBtn.setOnClickListener() {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        // Declaring retrofit instance
        iMyAPI = RetrofitClient.getInstance().create(APIInterface::class.java)
        // Creating API
        val api = RetrofitClient.getInstance().create(APIInterface::class.java)

        // Replace this with your actual API response
        val barData = api.getOverallHours(PreferenceHelper.ID_KEY)

        barData.enqueue(object : Callback<Map<String, Int>> {
            override fun onResponse(
                call: Call<Map<String, Int>>,
                response: Response<Map<String, Int>>
            ) {
                val responseBody = response.body()!!
                responseBody?.let { data ->
                    var tValue: Int = 0
                    for ((key, value) in data) {
                        tValue = value + tValue
                        println("Department: $key, Duration: $value")
                    }
                    totalHrs.text = convertMinutesToHoursMinutesString(tValue) + " worked"
                }
                println(responseBody)
                dataListing(responseBody)
            }
            override fun onFailure(call: Call<Map<String, Int>>, t: Throwable) {
                Log.d("MainActivity", "onFailure "+t.message)
            }
        })

        chart = findViewById(R.id.barchart)

//        dataListing(responseBody)

    }

    private fun dataListing(data:Map<String, Int>) {
        var index = 0f
        profitValues.clear() // Clear previous values
        val departmentNames = ArrayList<String>()

        for ((key, value) in data) {
            println("Department: $key, Duration: $value")
            println(index.toFloat())
            println(convertMinutesToHours(value).toFloat())
            profitValues.add(BarEntry(index.toFloat(), convertMinutesToHours(value).toFloat()))
            departmentNames.add(key) // Add department name to the list
            index++
        }

//        //Must transform data to float
//        profitValues.add(BarEntry(0.toFloat(), 13.toFloat())) // x column position, y row position
//        profitValues.add(BarEntry(1.toFloat(), 27.toFloat()))
//        profitValues.add(BarEntry(2.toFloat(), 4.toFloat()))
//        profitValues.add(BarEntry(3.toFloat(), 29.toFloat()))
//        profitValues.add(BarEntry(4.toFloat(), 41.toFloat()))
//
//        profitValues.add(BarEntry(5.toFloat(), 13.toFloat())) // x column position, y row position
//        profitValues.add(BarEntry(6.toFloat(), 27.toFloat()))
//        profitValues.add(BarEntry(7.toFloat(), 4.toFloat()))
//        profitValues.add(BarEntry(8.toFloat(), 29.toFloat()))
//        profitValues.add(BarEntry(9.toFloat(), 41.toFloat()))
//
//        profitValues.add(BarEntry(10.toFloat(), 13.toFloat())) // x column position, y row position
//        profitValues.add(BarEntry(11.toFloat(), 27.toFloat()))

        setChart(departmentNames)
    }

//    private fun getHoursValueForIndex(index: Int): Any {
//        // Replace with your actual data structure for hours
//        val hoursData = profitValues  // Example hours data
//
//        return if (index in hoursData.indices) {
//            hoursData[index]
//        } else {
//            0f  // Return a default value if the index is out of bounds
//        }
//    }
    private fun setChart(departmentNames: ArrayList<String>) {
        chart.description.isEnabled = false
        chart.setMaxVisibleValueCount(25)
        chart.setPinchZoom(false)
        chart.setDrawBarShadow(false)
        chart.setDrawBarShadow(false)

        val yAxis = chart.axisLeft
        yAxis.axisLineColor = Color.BLACK
        yAxis.labelCount = 10


        val xAxis = chart.xAxis
//arrayListOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec")
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.valueFormatter = IndexAxisValueFormatter(departmentNames)
        xAxis.isGranularityEnabled;


        chart.axisLeft.setDrawGridLines(false)
        chart.legend.isEnabled = false
        chart.setDrawValueAboveBar(true)

        val valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toString()
            }
        }

        val barDataSetter: BarDataSet

        if(chart.data != null && chart.data.dataSetCount > 0) {
            barDataSetter = chart.data.getDataSetByIndex(0) as BarDataSet
            barDataSetter.values = profitValues

            barDataSetter.valueFormatter = valueFormatter
//            barDataSetter.valueTextSize = 12f
//            barDataSetter.valueTextColor = Color.BLACK
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        }
        else {
            barDataSetter = BarDataSet(profitValues, "Data Set")
            barDataSetter.setColors(*ColorTemplate.VORDIPLOM_COLORS)
            barDataSetter.setDrawValues(false)

            val dataSet = ArrayList<IBarDataSet>()

            dataSet.add(barDataSetter)

            val data = BarData(dataSet)
            chart.data = data
            chart.setFitBars(true)

        }

        chart.invalidate()

    }

    fun convertMinutesToHours(totalMinutes: Int): Double {

        return totalMinutes.toDouble() / 60.0
    }
    fun convertMinutesToHoursMinutesString(totalMinutes: Int): String {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60

        return String.format("%02d hr %02d mins", hours, minutes)
    }


}
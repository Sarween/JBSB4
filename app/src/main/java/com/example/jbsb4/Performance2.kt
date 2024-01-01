package com.example.jbsb4

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BubbleDataSet
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate

class Performance2 : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding

    private lateinit var chart: RadarChart

    var profitValues = ArrayList<RadarEntry>()
    var entries = ArrayList<RadarEntry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance2)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        chart = findViewById(R.id.radarchart)

        dataListing()

    }

    private fun dataListing() {

        //Must transform data to float
        profitValues.add(RadarEntry(150f))
        profitValues.add(RadarEntry(163f))
        profitValues.add(RadarEntry(120f))
        profitValues.add(RadarEntry(150f))
//        profitValues.add(RadarEntry(165f))
//        profitValues.add(RadarEntry(255f))

        entries.add(RadarEntry(120f))
        entries.add(RadarEntry(263f))
        entries.add(RadarEntry(220f))
        entries.add(RadarEntry(100f))
//        entries.add(RadarEntry(195f))
//        entries.add(RadarEntry(225f))

        setChart()
    }

    private fun setChart() {
        val dataset = RadarDataSet(profitValues, "Radar Chart Legend")
        dataset.valueTextColor = Color.BLACK
        dataset.valueTextSize = 12f
        dataset.fillColor = Color.BLUE
        dataset.color = Color.BLUE
        dataset.lineWidth = 4f
        dataset.setDrawHighlightIndicators(true)
        dataset.setDrawFilled(true)
        dataset.setDrawValues(true)

        val dataset1 = RadarDataSet(entries, "Radar Chart Legend 2")
        dataset1.valueTextColor = Color.DKGRAY
        dataset1.valueTextSize = 12f
        dataset1.fillColor = Color.RED
        dataset1.color = Color.RED
        dataset1.lineWidth = 4f
        dataset1.setDrawHighlightIndicators(true)
        dataset1.setDrawFilled(true)
        dataset1.setDrawValues(true)

        val data = RadarData(dataset, dataset1)
        chart.data = data
        chart.invalidate()

        val xAxis: XAxis = chart.xAxis
        val categories = listOf("Category 1", "Category 2", "Category 3", "Category 4")
        xAxis.valueFormatter = IndexAxisValueFormatter(categories)

        chart.legend.isEnabled = false
        chart.description.text = "Performance Evaluation"
        chart.description.textSize = 12f
        chart.setNoDataText("No data available")
        chart.animateXY(1400, 1400)
        chart.setTouchEnabled(true)
        chart.setBackgroundColor(Color.LTGRAY)
        chart.webColor = Color.YELLOW


        val legend = chart.legend
        legend.isEnabled = true
        legend.form = Legend.LegendForm.LINE
        legend.textSize = 12f
        legend.textColor = Color.BLUE

    }
}
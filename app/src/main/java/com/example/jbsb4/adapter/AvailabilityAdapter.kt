package com.example.jbsb4.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Availability
import com.example.jbsb4.Model.AvailabilityList
import com.example.jbsb4.R
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AvailabilityAdapter (val context: Context, val Availability: List<AvailabilityList>, activity: Activity): RecyclerView.Adapter<AvailabilityAdapter.AvailabilityListViewHolder>(){

    class AvailabilityListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var dateAvail: TextView
        var hourOfDay: TextView
        var endTime: TextView
        var deleteBtn: Button
//        var addUnavailabilityBtn: Button

        init{
            dateAvail = itemView.findViewById(R.id.dateAvail)
            hourOfDay = itemView.findViewById(R.id.hourOfDay)
            endTime = itemView.findViewById(R.id.endTime)
//            addUnavailabilityBtn = itemView.findViewById(R.id.addUnavailabilityBtn)
            deleteBtn = itemView.findViewById(R.id.deleteBtn)

//            val timeStr = hourOfDay.text.toString()
//
//            val time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm:ss"))
//            val timePlusOneHour = time.plusHours(1)
//            val newTimeStr = timePlusOneHour.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
//
//            endTime.text = newTimeStr

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailabilityListViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_availability, parent, false)
        return AvailabilityListViewHolder(itemView)
    }

//    override fun onBindViewHolder(holder: AvailabilityListViewHolder, position: Int) {
//        val availability = Availability[position]
//
//        holder.dateAvail.text = availability.dateAvail
//        holder.hourOfDay.text = availability.hourOfDay
//    }

    override fun onBindViewHolder(holder: AvailabilityListViewHolder, position: Int) {
        val availability = Availability[position]

        holder.dateAvail.text = availability.dateAvail
        holder.hourOfDay.text = availability.hourOfDay

        println("HEre you go")
        // Parse and set end time
        try {
            val time = LocalTime.parse(holder.hourOfDay.text, DateTimeFormatter.ofPattern("HH:mm:ss"))
            val timePlusOneHour = time.plusHours(1)
            holder.endTime.text = timePlusOneHour.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        } catch (e: Exception) {
            // Handle exception or set default value
            holder.endTime.text = "Invalid Time"
        }

        holder.deleteBtn.setOnClickListener {
            val api = RetrofitClient.getInstance().create(APIInterface::class.java)

            val deleteResponse = api.deleteUnavailability(
                PreferenceHelper.ID_KEY,
                Availability[position].dateAvail,
                Availability[position].hourOfDay
            )

            deleteResponse.enqueue(object: Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    println("Successful deleted")
                    Toast.makeText(context, "Unavailability is deleted.", Toast.LENGTH_LONG).show()
                }
                // Make it responsive

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    println("Failed deleted")
                }
            })
        }

//        holder.addUnavailabilityBtn.setOnClickListener(){
//            val intent = Intent(context, com.example.jbsb4.Availability::class.java)
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return Availability.size
    }
}
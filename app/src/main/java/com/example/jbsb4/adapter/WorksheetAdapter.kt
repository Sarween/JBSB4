package com.example.jbsb4.adapter

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Model.JBSBWorksheet
import com.example.jbsb4.Model.Recruitment
import com.example.jbsb4.Model.Shift
import com.example.jbsb4.Model.WorksheetItem
import com.example.jbsb4.R
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class WorksheetAdapter(val context: Context, val worksheetList: List<WorksheetItem>, val activity: Activity) : RecyclerView.Adapter<WorksheetAdapter.WorksheetViewHolder>() {
    class WorksheetViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var shiftDate: TextView
        var checkIn: TextView
        var checkOut: TextView
        var duration: TextView
        var wage: TextView
        var location: TextView
        var rating: RatingBar
        var comment: TextView
        var late: TextView
        var ontime: TextView
        var overtime: TextView
        var supervisor: TextView
        var pending: ImageView
        var approve: ImageView
        var supervisorRating: RatingBar

        init {
            shiftDate = itemView.findViewById(R.id.text_shift_title)
            checkIn = itemView.findViewById(R.id.shift_checkin)
            checkOut = itemView.findViewById(R.id.shift_checkout)
            duration = itemView.findViewById((R.id.text_duration))
            wage = itemView.findViewById(R.id.text_wage)
            location = itemView.findViewById(R.id.worksheet_location)
            rating = itemView.findViewById(R.id.rating_bar)
            comment = itemView.findViewById(R.id.commentText)
            late = itemView.findViewById(R.id.chip_late)
            ontime = itemView.findViewById(R.id.chip_ontime)
            overtime = itemView.findViewById(R.id.chip_overtime)
            supervisor = itemView.findViewById(R.id.picText)
            pending = itemView.findViewById(R.id.pending)
            approve = itemView.findViewById(R.id.approve)
            supervisorRating = itemView.findViewById(R.id.rating_bar)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorksheetViewHolder {
        //Reset layout
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_completedshift, parent, false)
        return WorksheetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorksheetViewHolder, position: Int) {


        holder.shiftDate.text = formatDate(worksheetList[position].shiftdates)
        holder.checkIn.text = getTimeFromString(worksheetList[position].shiftCheckInTime)
        holder.checkOut.text = getTimeFromString(worksheetList[position].shiftCheckOutTime)
        holder.duration.text = convertMinutesToHoursMinutesString(worksheetList[position].shiftDuration)
        holder.wage.text = "RM " + worksheetList[position].shiftsWage.toString()
        holder.location.text = worksheetList[position].jobLocation
        holder.rating.rating = worksheetList[position].rating.toFloat()
        if (worksheetList[position].jobVerification) {
            holder.comment.text =  "Student: " + worksheetList[position].studentComment + "\n" + "Supervisor: " + worksheetList[position].staffReview
        }
        else {
            holder.comment.text =  "Student: " + worksheetList[position].studentComment
        }
        holder.supervisor.text = worksheetList[position].jobPIC

        if (worksheetList[position].isLate) {
            holder.late.visibility = View.VISIBLE
            holder.ontime.visibility = View.GONE
        } else {
            holder.ontime.visibility = View.VISIBLE
            holder.late.visibility = View.GONE
        }

        if (worksheetList[position].isOvertime) {
            holder.overtime.visibility = View.VISIBLE
        } else {
            holder.overtime.visibility = View.GONE
        }

        if (worksheetList[position].jobVerification) {
            holder.approve.visibility = View.VISIBLE
            holder.pending.visibility = View.GONE
            holder.supervisorRating.visibility = View.VISIBLE
        }
        else {
            holder.pending.visibility = View.VISIBLE
            holder.approve.visibility = View.GONE
            holder.supervisorRating.visibility = View.GONE
        }

    }


    override fun getItemCount(): Int {
        return worksheetList.size
    }

    fun getTimeFromString(dateTimeString: String): String {

        val hasMilliseconds = dateTimeString.contains(".")
        val formatter = DateTimeFormatter.ofPattern(if (hasMilliseconds) "yyyy-MM-dd'T'HH:mm:ss.SS" else "yyyy-MM-dd'T'HH:mm:ss")
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)

        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        val formattedTime = dateTime.format(timeFormatter)

        return formattedTime // Return the time as a string
    }

    fun formatDate(inputDate: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        val date = LocalDate.parse(inputDate, inputFormatter)
        val formattedDate = date.format(outputFormatter)

        return formattedDate
    }

    fun convertMinutesToHoursMinutesString(totalMinutes: Int): String {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60

        return String.format("%02d hr %02d mins", hours, minutes)
    }

}
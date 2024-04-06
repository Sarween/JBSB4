package com.example.jbsb4.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Model.Assignment
import com.example.jbsb4.Model.PassiveAssignment
import com.example.jbsb4.R
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PassiveAssignmentAdapter (val context: Context, val PassiveAssignment: List<PassiveAssignment>, activity: Activity): RecyclerView.Adapter<PassiveAssignmentAdapter.AssignmentListViewHolder>(){

  class AssignmentListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var jobShiftDate: TextView
    var startTime: TextView
    var endTime: TextView
    var jobLocation: TextView
    var jobDescription: TextView
    var acceptBtn: Button
    var rejectBtn: Button

//    val endTime: String,
//    val isFCFS: Int,
//    val jobDescription: String,
//    val jobLocation: String,
//    val jobShiftDate: String,
//    val recruitmentID: Int,
//    val startTime: String,
//    val stuNumReqRemain: Int,
//    val studentID: Int

    init{
      jobShiftDate = itemView.findViewById(R.id.jobShiftDate)
      startTime = itemView.findViewById(R.id.startTime)
      endTime = itemView.findViewById(R.id.endTime)
      jobLocation = itemView.findViewById(R.id.jobLocation)
      jobDescription = itemView.findViewById(R.id.jobDescription)
      acceptBtn = itemView.findViewById((R.id.acceptBtn))
      rejectBtn = itemView.findViewById((R.id.rejectBtn))
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentListViewHolder {
    val itemView = LayoutInflater.from(context).inflate(R.layout.list_assignments, parent, false)
    return AssignmentListViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: AssignmentListViewHolder, position: Int) {
    val passiveAssignments = PassiveAssignment[position]

    holder.jobShiftDate.text = passiveAssignments.jobShiftDate
    holder.startTime.text = passiveAssignments.startTime
    holder.endTime.text = passiveAssignments.endTime
    holder.jobLocation.text = passiveAssignments.jobLocation
    holder.jobDescription.text = passiveAssignments.jobDescription

    holder.acceptBtn.setOnClickListener{
      val api = RetrofitClient.getInstance().create(APIInterface::class.java)

      val acceptResponse = api.acceptJobAssignment(
        PassiveAssignment[position].recruitmentID,
        PreferenceHelper.ID_KEY,
      )

      acceptResponse.enqueue(object : Callback<Assignment>{
        override fun onResponse(call: Call<Assignment>, response: Response<Assignment>) {
          println("Accepted")
          Toast.makeText(context, "Job assignment is accepted", Toast.LENGTH_LONG).show()
        }

        override fun onFailure(call: Call<Assignment>, t: Throwable) {
          println("Failed to accept")
        }
      })
    }

    holder.rejectBtn.setOnClickListener{
      val api = RetrofitClient.getInstance().create(APIInterface::class.java)

      val rejectResponse = api.rejectJobAssignment(
        PassiveAssignment[position].recruitmentID,
        PreferenceHelper.ID_KEY,
      )

      rejectResponse.enqueue(object : Callback<ResponseBody>{
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
          println("Accepted")
          Toast.makeText(context, "Job assignment is rejected.", Toast.LENGTH_LONG).show()
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
          println("Failed to reject")
        }
      })
    }
  }

  override fun getItemCount(): Int {
    return PassiveAssignment.size
  }
}


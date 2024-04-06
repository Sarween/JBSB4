package com.example.jbsb4.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Model.Application
import com.example.jbsb4.Model.Recruitment
import com.example.jbsb4.R
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class RecruitmentListAdapter (val context: Context, var RecruitmentList: List<Recruitment>, activity: Activity) : RecyclerView.Adapter<RecruitmentListAdapter.RecruitmentListViewHolder>(){

    class RecruitmentListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var jobShiftDate: TextView
        var stuNumReqRemain: TextView
        var startTime: TextView
        var endTime: TextView
        var jobLocation: TextView
        var jobDescription: TextView
        var applyBtn: Button

        init {
            jobShiftDate = itemView.findViewById(R.id.jobShiftDate)
            stuNumReqRemain = itemView.findViewById(R.id.stuNumReqRemain)
            startTime = itemView.findViewById(R.id.startTime)
            endTime = itemView.findViewById(R.id.endTime)
            jobLocation = itemView.findViewById(R.id.jobLocation)
            jobDescription = itemView.findViewById(R.id.jobDescription)
            applyBtn = itemView.findViewById(R.id.applyBtn)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitmentListViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_recruitments, parent, false)
        return RecruitmentListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecruitmentListViewHolder, position: Int) {
//        val jobShiftDate = RecruitmentList[position].jobShiftDate
//        val stuNumReqRemain = RecruitmentList[position].stuNumReqRemain
//        val startTime = RecruitmentList[position].startTime
//        val endTime = RecruitmentList[position].endTime
//        val jobLocation = RecruitmentList[position].jobLocation
//        val jobDescription = RecruitmentList[position].jobDescription


        val recruitment = RecruitmentList[position]

        holder.jobShiftDate.text = recruitment.jobShiftDate
        holder.stuNumReqRemain.text = "${recruitment.stuNumReqRemain} quotas left"
        holder.startTime.text = recruitment.startTime
        holder.endTime.text = recruitment.endTime
        holder.jobLocation.text = recruitment.jobLocation
        holder.jobDescription.text = recruitment.jobDescription

//        holder.applyBtn.setOnClickListener{
//            val api = RetrofitClient.getInstance().create(APIInterface::class.java)
//
//            val applyResponse = api.applyJob(
//                RecruitmentList[position].recruitmentID,
//                PreferenceHelper.ID_KEY,
//            )
//
//            applyResponse.enqueue(object : Callback<Application>{
//                override fun onResponse(call: Call<Application>, response: Response<Application>) {
//                    println("Applied")
//                    Toast.makeText(context, "You have successfully applied for the job.}", Toast.LENGTH_LONG).show()
//
//                    // Example: Update your data set here
//                    // This could be removing the applied job from the list, updating its status, etc.
//                    // For example, if you want to remove the applied item:
//                    RecruitmentList = RecruitmentList.filterNot { it.recruitmentID == RecruitmentList[position].recruitmentID }
//
//                    // Notify the adapter of the change
//                    notifyDataSetChanged()
//                }
//
//                override fun onFailure(call: Call<Application>, t: Throwable) {
//                    println("Failed")
//                }
//            })
//        }
        holder.applyBtn.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition == RecyclerView.NO_POSITION) {
                return@setOnClickListener
            }

            val currentRecruitment = RecruitmentList[currentPosition]

            // Now use currentRecruitment for your operations
            val api = RetrofitClient.getInstance().create(APIInterface::class.java)
            val applyResponse = api.applyJob(currentRecruitment.recruitmentID, PreferenceHelper.ID_KEY)

            applyResponse.enqueue(object : Callback<Application>{
                override fun onResponse(call: Call<Application>, response: Response<Application>) {
                    println("Applied")
                    Toast.makeText(context, "You have successfully applied for the job.", Toast.LENGTH_LONG).show()

                    // Example: Update your data set here
                    // This could be removing the applied job from the list, updating its status, etc.
                    // For example, if you want to remove the applied item:
                    RecruitmentList = RecruitmentList.filterNot { it.recruitmentID == currentRecruitment.recruitmentID }

                    // Notify the adapter of the change
                    notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Application>, t: Throwable) {
                    println("Failed")
                }
            })
        }


    }

    override fun getItemCount(): Int {
        return RecruitmentList.size
    }
}
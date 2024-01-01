package com.example.jbsb4.adapter

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jbsb4.Model.LeaderboardItem
import com.example.jbsb4.Model.Recruitment
import com.example.jbsb4.R
import com.google.android.material.progressindicator.LinearProgressIndicator
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class LeaderboardAdapter(val context: Context, val LeaderboardList: List<LeaderboardItem>, val activity: Activity) : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    class LeaderboardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name: TextView
//        var rating: LinearProgressIndicator
//        var punctuality: LinearProgressIndicator
//        var reliability: LinearProgressIndicator
//        var commitment: LinearProgressIndicator
        var rating: ProgressBar
        var punctuality: ProgressBar
        var reliability: ProgressBar
        var commitment: ProgressBar
        var duration: TextView
        var score: TextView



        init {
            name = itemView.findViewById(R.id.leadername)
            rating = itemView.findViewById(R.id.rating_bar_leaderboard)
            punctuality = itemView.findViewById(R.id.punctuality_bar)
            reliability = itemView.findViewById(R.id.reliability_bar)
            commitment = itemView.findViewById(R.id.commitment_bar)
            duration = itemView.findViewById(R.id.text_hrsWorked)
            score = itemView.findViewById(R.id.score)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardAdapter.LeaderboardViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_leaderboard, parent, false)
        return LeaderboardAdapter.LeaderboardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LeaderboardAdapter.LeaderboardViewHolder, position: Int) {

//        val ratingValue = LeaderboardList[position].rating * 5
//        val punctualityValue = LeaderboardList[position].punctuality * 5
//        val reliabilityValue = LeaderboardList[position].reliability * 5
//        val commitmentValue = LeaderboardList[position].commitment * 5

        val ratingValue = LeaderboardList[position].rating * 1000
        val punctualityValue = LeaderboardList[position].punctuality * 1000
        val reliabilityValue = LeaderboardList[position].reliability * 1000
        val commitmentValue = LeaderboardList[position].commitment * 1000

        val scoreValue = (LeaderboardList[position].score * 100).toInt()



        holder.rating.max = 1000
        holder.punctuality.max = 1000
        holder.reliability.max = 1000
        holder.commitment.max = 1000


        holder.name.text = LeaderboardList[position].name
        ObjectAnimator.ofInt(holder.rating,"progress", ratingValue.toInt()).setDuration(1000).start()
        ObjectAnimator.ofInt(holder.punctuality,"progress", punctualityValue.toInt()).setDuration(1000).start()
        ObjectAnimator.ofInt(holder.reliability,"progress", reliabilityValue.toInt()).setDuration(1000).start()
        ObjectAnimator.ofInt(holder.commitment,"progress", commitmentValue.toInt()).setDuration(1000).start()


//        holder.rating.rating = ratingValue.toFloat()
//        holder.punctuality.rating = punctualityValue.toFloat()
//        holder.reliability.rating = reliabilityValue.toFloat()
//        holder.commitment.rating = commitmentValue.toFloat()

//        holder.rating.progress = ratingValue
//        holder.punctuality.progress = punctualityValue
//        holder.reliability.progress = reliabilityValue
//        holder.commitment.progress = commitmentValue

        holder.duration.text = convertMin(LeaderboardList[position].duration)
        holder.score.text = scoreValue.toString() + " pts"
    }

    override fun getItemCount(): Int {
        return LeaderboardList.size
    }

    fun convertMin(duration: Int): String {

        val hours = duration / 60 // Get the whole number of hours
        val minutes = duration % 60 // Get the remaining minutes

        val durationText = "$hours Hr $minutes mins"
        return durationText
    }


    fun getTimeFromString(dateTimeString: String): String {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME // Using ISO-8601 format for parsing
        val offsetDateTime = OffsetDateTime.parse(dateTimeString, formatter)

        val time = offsetDateTime.toLocalTime() // Extract the time part
        val str = time.toString()

        val parts = str.split(".")
        val result = parts.firstOrNull() ?: str

        return result // Return the time as a string
    }
}
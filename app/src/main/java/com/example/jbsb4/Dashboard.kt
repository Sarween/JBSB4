package com.example.jbsb4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jbsb4.databinding.ActivityDashboardBinding
import com.example.jbsb4.fragments.HomeFragment
import com.example.jbsb4.fragments.LeaderboardFragment
import com.example.jbsb4.fragments.PerformanceFragment
import com.example.jbsb4.fragments.WorksheetFragment

class Dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavbar.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.leaderboard -> replaceFragment(LeaderboardFragment())
                R.id.worksheet -> replaceFragment(WorksheetFragment())
                R.id.performance -> replaceFragment(PerformanceFragment())

                else -> {

                }

            }

            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()

    }

}
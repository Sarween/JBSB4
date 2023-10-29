package com.example.jbsb4.fragments


import android.content.pm.PackageManager
import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.jbsb4.ConnectSQL
import com.example.jbsb4.R
import com.example.jbsb4.databinding.FragmentLeaderboardBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.sql.PreparedStatement
import java.sql.SQLException
import androidx.core.app.ActivityCompat



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LeaderboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaderboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var connctSQL = ConnectSQL()

    private var _leaderboardBinding: FragmentLeaderboardBinding? = null
    private val leaderboardBinding  get() = _leaderboardBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _leaderboardBinding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        println("onCreateView45")
        val view = leaderboardBinding.root

        return view
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("onViewCreated1")
        super.onViewCreated(view, savedInstanceState)
        println("onViewCreated2")

        leaderboardBinding.test2.setOnClickListener {
            println("link")
            test()
        }

        println("onViewCreated3")
    }

    fun test() {
        print("go")
        try {
            val query: PreparedStatement = connctSQL.dbConn()?.prepareStatement("insert into Location values (?,?,?)")!!
            print("oh yeah")
            if (connctSQL == null){
                print("shit")
            }
            query.setString(1, "shit")
            query.setString(2, "hel")
            query.setString(3, "gang")
            query.executeUpdate();
            print("done")
        }catch (ex: SQLException) {
            //KIV
            Toast.makeText(context, "Fail insert", Toast.LENGTH_LONG).show()
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeaderboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeaderboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
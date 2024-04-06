package com.example.jbsb4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jbsb4.helpers.PreferenceHelper
import com.example.jbsb4.remote.APIInterface
import com.example.jbsb4.remote.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emaillogin: EditText = findViewById(R.id.emailText);
        val passwordlogin: EditText = findViewById(R.id.passwordText);


        val loginBtn: Button = findViewById(R.id.loginBtn)

        loginBtn.setOnClickListener() {
            val enteredId = emaillogin.text.toString().trim().toIntOrNull()

            if (enteredId != null) {

                val api = RetrofitClient.getInstance().create(APIInterface::class.java)

                // Calling check in api
                val checkInResponse = api.login(
                    enteredId,
                    passwordlogin.text.toString()
                )

                checkInResponse.enqueue(object: Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            PreferenceHelper.saveID(this@MainActivity, enteredId)
                            val intent = Intent(this@MainActivity, Dashboard::class.java)
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(this@MainActivity, "Invalid ID or Password", Toast.LENGTH_LONG).show()
                        }

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Invalid ID or Password", Toast.LENGTH_LONG).show()
                    }


                })

//                PreferenceHelper.saveID(this, enteredId)
//
//                val intent = Intent(this, Dashboard::class.java)
//                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid ID or Password", Toast.LENGTH_LONG).show()
            }

//            val intent = Intent(this, Dashboard::class.java)
//            startActivity(intent)
        }
    }
}


//    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var binding: ActivityMainBinding

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setSupportActionBar(binding.toolbar)
//
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }

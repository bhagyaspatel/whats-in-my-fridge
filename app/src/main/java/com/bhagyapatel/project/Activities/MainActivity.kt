package com.bhagyapatel.project.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import com.bhagyapatel.project.Authentication.SignupActivity
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val TAG = "Main_activity"
    lateinit var binding : ActivityMainBinding
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        if(user == null){
            Log.d(TAG, "onCreate: user null")
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            Log.d(TAG, "onCreate: user not null")
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}
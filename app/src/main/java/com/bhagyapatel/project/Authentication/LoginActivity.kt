package com.bhagyapatel.project.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.widget.addTextChangedListener
import com.bhagyapatel.project.Activities.HomeActivity
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.ActivityLoginBinding
import com.bhagyapatel.project.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private val TAG = "Login_page"
    lateinit var binding : ActivityLoginBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation : Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.from_bottom)
        binding.loginLL.startAnimation(animation)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        auth = FirebaseAuth.getInstance()

        binding.btnGoSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.etEmail.addTextChangedListener {
            binding.etEmail.error = null
        }

        binding.etPassword.addTextChangedListener {
            binding.etPassword.error = null
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if(isValid(email, password)){
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Log in failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "onCreate: ${it.message}",)
                        Toast.makeText(this, "Please check your email or password correctly", Toast.LENGTH_SHORT).show()
                    }
            }

        }

    }

    private fun isValid(email: String, password: String): Boolean {
        var valid = true

        if (!isValidEmail(email)){
            binding.etEmail.error = "Please enter a valid email address"
            valid = false
        }
        if (password.isBlank()){
            binding.etPassword.error = "Please enter the password"
            valid = false
        }
        if(password.length < 6){
            binding.etPassword.error = "Password length should be minimum of 6 characters"
            valid = false
        }

        return valid
    }

    private fun isValidEmail(email: CharSequence): Boolean {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}
package com.bhagyapatel.project.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.bhagyapatel.project.Activities.MainActivity
import com.bhagyapatel.project.Activities.uuid
import com.bhagyapatel.project.BackendRequests.Interfaces.NodeInterface
import com.bhagyapatel.project.Interface.RetrofitHelpers.NodeRetrofitHelper
import com.bhagyapatel.project.MVVM.Repository.NodeRepositories.NodeRepository
import com.bhagyapatel.project.MVVM.ViewModal.NodeViewModals.NodeViewModal
import com.bhagyapatel.project.MVVM.ViewModal.ViewModalFactories.NodeViewModalFactory
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private val TAG = "Signup_page"
    lateinit var binding : ActivitySigninBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var nodeViewModal: NodeViewModal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate: ")

        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation : Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.from_bottom)
        binding.signupLL.startAnimation(animation)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        binding.btnGoLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.etEmail.addTextChangedListener {
            binding.etEmail.error = null
        }

        binding.etPassword.addTextChangedListener {
            binding.etPassword.error = null
        }

        binding.etConfirmPassword.addTextChangedListener {
            binding.etConfirmPassword.error = null
        }

        binding.etUsername.addTextChangedListener {
            binding.etUsername.error = null
        }

        auth = FirebaseAuth.getInstance()

        binding.signupBtn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPwd = binding.etConfirmPassword.text.toString()
            val username = binding.etUsername.text.toString()

            if(isValid(email, password, confirmPwd, username)){
                val nodeInterface = NodeRetrofitHelper.getInstance().create(NodeInterface::class.java)
                val reopsitory = NodeRepository(nodeInterface)
                nodeViewModal = ViewModelProvider(this, NodeViewModalFactory(reopsitory))
                    .get(NodeViewModal::class.java)

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val uuid = FirebaseAuth.getInstance().currentUser?.uid
                        val map = HashMap<String, String>()
                        map.put("uuid", uuid!!)
                        map.put("username", username)
                        map.put("email", email)
                        nodeViewModal.signup(map)
                        nodeViewModal.responseSignup()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener{
                        Log.d(TAG, "error while signin : ${it.message}")
                        Toast.makeText(this, "Signup Error", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun isValid(email: String, password: String, confirmPassword: String, username : String): Boolean {
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
        if (confirmPassword.isBlank()){
            binding.etPassword.error = "Please enter the password again to confirm"
            valid = false
        }
        if (confirmPassword != password){
            binding.etPassword.error = "Password does not matched with the above password"
            valid = false
        }
        if(username.isNullOrBlank()){
            binding.etUsername.error = "Please provide the username"
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
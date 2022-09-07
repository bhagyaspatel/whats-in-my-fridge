package com.bhagyapatel.project.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bhagyapatel.project.Fragments.HomeFragment
import com.bhagyapatel.project.Fragments.ProfileFragment
import com.bhagyapatel.project.Fragments.RecipeFragment
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.ActivityHomeBinding
import com.bhagyapatel.project.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    private val TAG = "Home_activity"
    lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        Log.d(TAG, "onCreate: Home activity")
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        val navController = navHostFragment.navController

        replaceFragment(HomeFragment())
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.homeFragment -> {
                    Log.d(TAG, "setupBottomNavigation: clicked")
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.recipeFragment -> {
                    Log.d(TAG, "setupBottomNavigation: clicked")
                    replaceFragment(RecipeFragment())
                    true
                }
                R.id.profileFragment -> {
                    Log.d(TAG, "setupBottomNavigation: clicked")
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager : FragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}
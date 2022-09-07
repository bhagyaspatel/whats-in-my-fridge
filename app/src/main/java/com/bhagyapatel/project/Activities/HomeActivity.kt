package com.bhagyapatel.project.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import androidx.navigation.findNavController
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
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

//        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.homeFragment -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.recipeFragment -> {
                    replaceFragment(RecipeFragment())
                    true
                }
                R.id.profileFragment -> {
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
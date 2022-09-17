package com.bhagyapatel.project.OnboardingSplash.Screens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.bhagyapatel.project.Authentication.SignupActivity
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.FragmentSecondScreenBinding
import com.bhagyapatel.project.databinding.FragmentThirdScreenBinding

class ThirdScreenFragment : Fragment() {

    private lateinit var binding : FragmentThirdScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdScreenBinding.inflate(inflater, container, false)

        binding.finishBtn.setOnClickListener {
            val intent = Intent(activity, SignupActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return binding.root
    }

}
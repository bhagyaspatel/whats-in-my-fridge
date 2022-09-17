package com.bhagyapatel.project.OnboardingSplash.Screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.FragmentFirstScreenBinding
import com.bhagyapatel.project.databinding.FragmentSplashBinding

class FirstScreenFragment : Fragment() {

    private lateinit var binding : FragmentFirstScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstScreenBinding.inflate(inflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.nextBtn.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return binding.root
    }

}
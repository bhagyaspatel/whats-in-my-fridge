package com.bhagyapatel.project.OnboardingSplash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.FragmentRecipeBinding
import com.bhagyapatel.project.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private val TAG = "splash_fragment"
    private lateinit var binding : FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        Handler().postDelayed({
            findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToViewPagerFragment()
            )
        }, 3000)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: splash frag")

//        Handler().postDelayed({
//                findNavController().navigate(
//                    SplashFragmentDirections.actionSplashFragmentToViewPagerFragment()
//                )
//        }, 3000)
    }
}
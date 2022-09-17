package com.bhagyapatel.project.OnboardingSplash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bhagyapatel.project.OnboardingSplash.Screens.FirstScreenFragment
import com.bhagyapatel.project.OnboardingSplash.Screens.SecondScreenFragment
import com.bhagyapatel.project.OnboardingSplash.Screens.ThirdScreenFragment
import com.bhagyapatel.project.R
import com.bhagyapatel.project.databinding.FragmentSplashBinding
import com.bhagyapatel.project.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {

    private val TAG = "view_pager_fragment"
    private lateinit var binding : FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment()
        )

        val adapter = ViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter
    }

}
package com.toms.applications.marveltomasvazquez.ui.screen.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.databinding.FragmentScreenTwoBinding

class ScreenTwoFragment : Fragment() {

    private lateinit var binding: FragmentScreenTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_screen_two, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onBoardingViewPager)

        binding.btnNext.setOnClickListener {
            viewPager?.currentItem = 2
        }

        return binding.root
    }

    companion object {
        private const val TAG = "ScreenTwoFragment"
    }
}
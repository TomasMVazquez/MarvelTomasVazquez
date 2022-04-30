package com.toms.applications.marveltomasvazquez.ui.screen.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.databinding.FragmentScreenOneBinding

class ScreenOneFragment : Fragment() {

    private lateinit var binding: FragmentScreenOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_screen_one, container,false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onBoardingViewPager)

        binding.btnNext.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return binding.root
    }

    companion object {
        private const val TAG = "ScreenOneFragment"
    }
}
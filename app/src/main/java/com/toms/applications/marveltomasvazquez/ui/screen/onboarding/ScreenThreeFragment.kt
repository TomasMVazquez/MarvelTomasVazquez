package com.toms.applications.marveltomasvazquez.ui.screen.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.databinding.FragmentScreenThreeBinding
import com.toms.applications.marveltomasvazquez.util.onFinishOnBoarding

class ScreenThreeFragment : Fragment() {

    private lateinit var binding: FragmentScreenThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_screen_three, container, false)

        binding.btnFinish.setOnClickListener {
            onBoardingFinish()
            goToHome()
        }

        return binding.root
    }

    private fun goToHome() {
        findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
    }

    private fun onBoardingFinish() {
        onFinishOnBoarding(requireContext())
    }

    companion object {
        private const val TAG = "ScreenThreeFragment"
    }
}
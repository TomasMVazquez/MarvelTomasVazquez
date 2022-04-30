package com.toms.applications.marveltomasvazquez.ui.screen.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.toms.applications.marveltomasvazquez.R
import com.toms.applications.marveltomasvazquez.databinding.FragmentSplashBinding
import com.toms.applications.marveltomasvazquez.util.onBoardingFinished

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_splash,container,false)

        // Shows splash for a period of time and then continue navigations
        Handler(Looper.getMainLooper()).postDelayed({
            if (onBoardingFinished(requireContext())) {
                goToHome()
            }else{
                loadSplashScreen()
            }
        }, TIME_OUT)


        return binding.root
    }

    private fun loadSplashScreen() {
        findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment)
    }

    private fun goToHome() {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }

    companion object {
        private const val TAG = "SplashFragment"
        const val TIME_OUT:Long = 3000
    }
}
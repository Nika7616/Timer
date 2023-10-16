package com.example.timer.presentation.Screens.Splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.timer.R

class SplashFragment : Fragment() {
    private val viewModel: SplashViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.readyToMove.observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }

    }
}
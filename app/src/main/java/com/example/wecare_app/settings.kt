package com.example.wecare_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.wecare_app.databinding.FragmentSettingsBinding

class settings : Fragment() {

    private lateinit var binding : FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        )

        binding.aboutBtn.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, aboutUs())
                .addToBackStack(null).commit()

        }

        binding.myd.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, myDetail())
                .addToBackStack(null).commit()
        }

        binding.up.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, updatePassword())
                .addToBackStack(null).commit()
        }

        binding.LogIn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, LogIn())
                .addToBackStack(null).commit()
        }

        binding.Signup.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, SignUpPage1())
                .addToBackStack(null).commit()

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as MainActivity).setActionBarTitle("Settings")
    }

}
package com.example.wecare_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.wecare_app.databinding.FragmentHomepageBinding

class HomepageFragment : Fragment() {

    private lateinit var binding : FragmentHomepageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_homepage,
            container, false
        )

        binding.NewsButton.setOnClickListener {
            // Go to news page (TzeXuan)
        }

        binding.homeButton.setOnClickListener {
            // Home Page
        }

        binding.settingButton.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, settings())
                .addToBackStack(null).commit()
        }

        binding.FoodAndDrinks.setOnClickListener{
            val intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }

        binding.Shopping.setOnClickListener{
            val intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }

        binding.NewsButton.setOnClickListener {
            val intent = Intent(requireContext(), NewsActivityMain::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}
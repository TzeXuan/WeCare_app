package com.example.wecare_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.wecare_app.databinding.FragmentHomepageBinding
import kotlinx.android.synthetic.main.fragment_homepage.*

class HomepageFragment : Fragment() {

    private lateinit var binding : FragmentHomepageBinding
    private val sharedViewModel: SignUpViewModel by activityViewModels()
    var x : Int? = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_homepage,
            container, false
        )

        binding.News.setOnClickListener {
            val intent = Intent(requireContext(), NewsActivityMain::class.java)
            startActivity(intent)
        }

        binding.Home.setOnClickListener {
            // Home Page
        }

        binding.Settings.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, settings())
                .addToBackStack(null).commit()
        }

        binding.FoodAndDrinks.setOnClickListener{
            val authorities : String? = ""
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                    putExtra("restaurant", authorities)
            }
            startActivity(intent)
        }

        binding.Shopping.setOnClickListener{
            val shopping : String? = ""
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("shopping", shopping)
            }
            startActivity(intent)
        }

        binding.Transport.setOnClickListener{
            val transport : String? = ""
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("transport", transport)
            }
            startActivity(intent)
        }

        binding.Education.setOnClickListener{
            val education : String? = ""
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("education", education)
            }
            startActivity(intent)
        }

        binding.Health.setOnClickListener{
            val health : String? = ""
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("health", health)
            }
            startActivity(intent)
        }

        binding.Hotels.setOnClickListener{
            val hotels : String? = ""
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("health", hotels)
            }
            startActivity(intent)
        }


        return binding.root
    }

}
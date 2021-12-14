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

        binding.NewsButton.setOnClickListener {
            val intent = Intent(requireContext(), NewsActivityMain::class.java)
            startActivity(intent)
        }

        binding.homeButton.setOnClickListener {
            // Home Page
        }

        binding.settingButton.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, settings())
                .addToBackStack(null).commit()
        }

        binding.FoodAndDrinks.setOnClickListener{
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                    putExtra("category", "restaurant")
            }
            startActivity(intent)
        }

        binding.Shopping.setOnClickListener{
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("category", "shopping")
            }
            startActivity(intent)
        }

        binding.Transport.setOnClickListener{
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("category", "transport")
            }
            startActivity(intent)
        }

        binding.Education.setOnClickListener{
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("category", "education")
            }
            startActivity(intent)
        }

        binding.Health.setOnClickListener{
            val health = "health"
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("category", "health")
            }
            startActivity(intent)
        }

        binding.Hotels.setOnClickListener{
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("category", "hotels")
            }
            startActivity(intent)
        }

        binding.Finance.setOnClickListener{
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("category", "finance")
            }
            startActivity(intent)
        }

        binding.Authorties.setOnClickListener{
            val intent = Intent(requireContext(), MapsActivity::class.java).apply {
                putExtra("category", "authorities")
            }
            startActivity(intent)
        }

        return binding.root
    }

}
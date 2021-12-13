package com.example.wecare_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.wecare_app.databinding.FragmentMyDetailBinding

class myDetail : Fragment() {

    private lateinit var binding: FragmentMyDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_my_detail,
            container,
            false
        )
        return binding.root

    }

    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as MainActivity).setActionBarTitle("My Details")
    }
}
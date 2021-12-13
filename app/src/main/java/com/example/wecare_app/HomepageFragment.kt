package com.example.wecare_app

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


        return binding.root
    }

}
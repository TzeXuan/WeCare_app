package com.example.wecare_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.wecare_app.databinding.FragmentAboutUsBinding


class aboutUs : Fragment() {

    private lateinit var binding : FragmentAboutUsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_about_us,
            container,
            false
        )

        binding.emailemail.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.setType("text/plain")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, "wecare@gmail.com")
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "WeCare Apps")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "We are here for you. ")

            startActivity(Intent.createChooser(emailIntent, "Send Email"))

            if (getActivity()?.let { it1 -> emailIntent.resolveActivity(it1.getPackageManager()) } != null) {
                startActivity(emailIntent)
            } else {
                Toast.makeText(activity, "There is no application that support this action",
                    Toast.LENGTH_SHORT).show();
            }
        }

        binding.termBtn.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.termsofusegenerator.net/live.php?token=56SWDt8rPJZrv6KP8OEfFwDQQsMdw9d3"))
            startActivity(browserIntent)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as MainActivity).setActionBarTitle("About Us")
    }

}
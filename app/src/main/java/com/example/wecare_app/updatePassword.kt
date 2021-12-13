package com.example.wecare_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.wecare_app.databinding.FragmentSettingsBinding
import com.example.wecare_app.databinding.FragmentUpdatePasswordBinding


class updatePassword : Fragment() {

    private lateinit var binding: FragmentUpdatePasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_update_password,
            container,
            false
        )

        binding.upBtn.setOnClickListener{
            uppassuppass()
        }
        return binding.root
    }

    private fun uppassuppass(){
        if (binding.currentpass.text.isNotEmpty() && binding.newpass.text.isNotEmpty() && binding.confirmpass.text.isNotEmpty()){
            if (binding.newpass.text.toString()== binding.currentpass.text.toString()){
                //            val user =auth.currentUser
            }
            else{
                Toast.makeText(requireActivity(),"Password Does Not Match", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(requireActivity(),"Please fill out all the fields", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as MainActivity).setActionBarTitle("Update Password")
    }


}
package com.example.wecare_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.wecare_app.databinding.FragmentSignUpPage1Binding

class SignUpPage1 : Fragment() {

    private val TAG = "SignUpPage1"
    private lateinit var binding: FragmentSignUpPage1Binding
    private val sharedViewModel: SignUpViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up_page1,
            container,
            false
        )

        // Get the data from the sharedViewModel and set back to the editText
        sharedViewModel.email.observe(viewLifecycleOwner, {
            binding.email.setText(it)
        })
        sharedViewModel.password.observe(viewLifecycleOwner, {
            binding.password.setText(it)
        })
        sharedViewModel.confirmPassword.observe(viewLifecycleOwner,{
            binding.confirmPassword.setText(it)
        })

        binding.buttonNextPage1.setOnClickListener {
            var password: String = binding.password.text.toString()
            var confirmPassword: String = binding.confirmPassword.text.toString()

            // save the value into the sharedViewModel
            sharedViewModel.saveEmail(binding.email.text.toString())
            if (password == confirmPassword) {
                sharedViewModel.savePassword(password)
                sharedViewModel.saveConfirmPassword(confirmPassword)

                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, SignUpPage2())
                    .addToBackStack(null).commit()
            }
            else{
                Toast.makeText(activity, "Password not same", Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }
    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as MainActivity).setActionBarTitle("Sign Up")
    }

}
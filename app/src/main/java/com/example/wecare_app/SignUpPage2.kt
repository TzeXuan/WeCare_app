package com.example.wecare_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.wecare_app.databinding.FragmentSignUpPage2Binding

class SignUpPage2 : Fragment() {

    private lateinit var binding : FragmentSignUpPage2Binding
    private val sharedViewModel: SignUpViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up_page2,
            container, false
        )

        val genderSpinner : Spinner = binding.gender
        genderSpinner.onItemSelectedListener

        ArrayAdapter.createFromResource(requireContext(), R.array.gender, android.R.layout.simple_spinner_item)
            .also {
                //specify the layout to use when the list of choices appears
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // apply the adapter to the spinner
                genderSpinner.adapter = it
            }

        sharedViewModel.name.observe(viewLifecycleOwner, {
            binding.name.setText(it)
        })
        sharedViewModel.phoneNumber.observe(viewLifecycleOwner, {
            binding.phoneNumber.setText(it)
        })

        binding.buttonNextPage2.setOnClickListener{

            var name: String = binding.name.text.toString()
            var phoneNumber : String = binding.phoneNumber.text.toString()

            sharedViewModel.saveName(name)
            sharedViewModel.savePhoneNumber(phoneNumber)
            sharedViewModel.saveGender(genderSpinner.selectedItem.toString())

            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, SignUpPage3())
                .addToBackStack(null).commit()

        }

        binding.buttonBackPage2.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }


}
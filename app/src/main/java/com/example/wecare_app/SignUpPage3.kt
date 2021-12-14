package com.example.wecare_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.wecare_app.databinding.FragmentSignUpPage3Binding
import com.google.firebase.firestore.FirebaseFirestore

class SignUpPage3 : Fragment() {

    private val sharedViewModel : SignUpViewModel by activityViewModels()
    private lateinit var binding: FragmentSignUpPage3Binding
    val database = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up_page3,
            container,false
        )

        val caregiverSpinner : Spinner = binding.caregiver
        caregiverSpinner.onItemClickListener
        ArrayAdapter.createFromResource(requireContext(), R.array.caregiver, android.R.layout.simple_spinner_item)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                caregiverSpinner.adapter = it
            }

        sharedViewModel.saveCaregiver(caregiverSpinner.selectedItem.toString())

        binding.donePage3.setOnClickListener{
            savaToDatabase()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, LogIn())
                .addToBackStack(null).commit()
        }

        binding.buttonBackPage3.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }

        var spannableString = SpannableString(binding.agreeLicense.text.toString())
        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.termsofusegenerator.net/live.php?token=56SWDt8rPJZrv6KP8OEfFwDQQsMdw9d3"))
                startActivity(browserIntent)
            }
        }
        spannableString.setSpan(clickableSpan1, 8,29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.agreeLicense.setText(spannableString)
        binding.agreeLicense.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }

    private fun savaToDatabase(){

        val userRegisterData = hashMapOf<String,String>(
            "email" to sharedViewModel.email.value.toString(),
            "password" to sharedViewModel.password.value.toString(),
            "name" to sharedViewModel.name.value.toString(),
            "phoneNumber" to sharedViewModel.phoneNumber.value.toString(),
            "gender" to sharedViewModel.gender.value.toString(),
            "caregiver" to sharedViewModel.caregiver.value.toString()
        )

        database.collection("userRegisterData").document(sharedViewModel.dataPhoneNumber())
            .set(userRegisterData)
            .addOnSuccessListener {
                Toast.makeText(activity, "User Profile Created", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.w("Error adding document", it)
            }

    }
    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as MainActivity).setActionBarTitle("Sign Up")
    }

}
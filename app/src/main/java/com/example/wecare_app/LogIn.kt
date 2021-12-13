package com.example.wecare_app

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.wecare_app.databinding.FragmentLogInBinding
import com.google.firebase.firestore.FirebaseFirestore

class LogIn : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    val database = FirebaseFirestore.getInstance()

    var value: String? = ""
    var password2 : String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_log_in,
            container,
            false
        )

        binding.loginButton.setOnClickListener {
            password2 = binding.passwordLogin.text.toString()

            readDatabase(object : FirestoreCallback {
                override fun onCallback() {
                    if(value == password2){
                        Toast.makeText(activity, "Login Successfully", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(activity, "Please create an account ! ", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }

        binding.showPassword.setOnClickListener{
            if(binding.showPassword.text.toString().equals("Show Password")){
                binding.passwordLogin.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.showPassword.setText("Hide Password")
            } else{
                binding.passwordLogin.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.showPassword.setText("Show Password")
            }
        }

        var spannableString = SpannableString(binding.forgotYourPassword.text.toString())
        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.myNavHostFragment, updatePassword())
                    .addToBackStack(null).commit()
            }
        }
        spannableString.setSpan(clickableSpan1, 0,21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.forgotYourPassword.setText(spannableString)
        binding.forgotYourPassword.movementMethod = LinkMovementMethod.getInstance()

        return binding.root
    }

    private interface FirestoreCallback {
        fun onCallback()
    }

    private fun readDatabase(firestoreCallback: FirestoreCallback) {
        val userRegisterRef = database.collection("userRegisterData")
        val testing = binding.loginPhoneNumber.text.toString()

        userRegisterRef.document(testing).get()
            .addOnSuccessListener { document ->
                value = document.getString("password").toString()
                Log.d("TzeXuan", "DocumentSnapshot data: ${document.data}")
                firestoreCallback.onCallback()
            }
            .addOnFailureListener {
                Log.d("TzeXuan", "get failed with ")
            }

    }

}
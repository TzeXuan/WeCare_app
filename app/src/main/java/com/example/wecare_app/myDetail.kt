package com.example.wecare_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.wecare_app.databinding.FragmentMyDetailBinding
import com.example.wecare_app.databinding.FragmentUpdatePasswordBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class myDetail : Fragment() {

    val database = FirebaseFirestore.getInstance()
    var value: String? = ""
    private lateinit var binding: FragmentMyDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_my_detail,
            container,
            false
        )

        binding.save.setOnClickListener{
            if (binding.namebtn.text.isNotEmpty() && binding.emailbtn.text.isNotEmpty()){
                val userRegisterData = hashMapOf<String, String>(
                    "name" to binding.namebtn.toString(),
                    "email" to binding.namebtn.toString()
                )

                val query = database.collection("userRegisterData")
                    .whereEqualTo("phone",binding.phoneD.toString())
                    .get()
                query.addOnSuccessListener {
                    for (document in it )
                        database.collection("userRegisterData"). document(document.id).set(SetOptions.merge())
                }
                Toast.makeText(requireActivity(), "Success!", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(requireActivity(), "failed!", Toast.LENGTH_LONG).show()
            }
        }


        binding.save.setOnClickListener {
            //  if (binding.currentpass.text.isNotEmpty() && binding.newpass.text.isNotEmpty() && binding.confirmpass.text.isNotEmpty()){
            saveData(object : myDetail.FirestoreCallback {
                override fun onCallback()  {
                    Log.d("wq","testing1")
                }
            } )
        }

        return binding.root

    }

    private interface FirestoreCallback {
        fun onCallback()
    }

    private fun saveData(firestoreCallback: myDetail.FirestoreCallback){
        Log.d("wq","testing2")
        database.collection("userRegisterData")
            .document(binding.phoneD.text.toString())
            .update("name",binding.namebtn.text.toString(),"email",binding.emailbtn.text.toString())

            .addOnSuccessListener { Log.d("qq", "Successfully !") }
            .addOnFailureListener { e -> Log.w("ww", "Failed !") }
    }

    override fun onResume() {
        super.onResume()

        // Set title bar
        (activity as MainActivity).setActionBarTitle("My Details")
    }
}
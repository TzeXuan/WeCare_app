package com.example.wecare_app

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class readEstablishment{
    val database = FirebaseFirestore.getInstance()
    var locationID : String? = ""


     interface FirestoreCallback {
        fun onCallback()
    }

     fun readDatabaseES(firestoreCallback: FirestoreCallback) {
        val establishments = database.collection("establishments")
        val mapID = establishments


         establishments.document("FJw4W0yULAEYhlBhtaNO").get()
            .addOnSuccessListener { document ->
                locationID = document.getString("location_ID").toString()
                Log.d("Success", "DocumentSnapshot data: ${document.data}")
                firestoreCallback.onCallback()
            }
            .addOnFailureListener {
                Log.d("fail", "get failed with ")
            }

    }

}

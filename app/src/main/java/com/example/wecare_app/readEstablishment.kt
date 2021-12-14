/*
package com.example.wecare_app

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import java.util.*

class readEstablishment{
    val database = FirebaseFirestore.getInstance()
    var locationID : String? = ""
    var place_Name : String? = ""
    var number : String? = ""
    lateinit var location : GeoPoint



     interface FirestoreCallback {
        fun onCallback()
    }

     fun readDatabaseES() {
        val establishments = database.collection("establishments")
        val mapID = establishments

         database.collection("establishments")
             .get()
             .addOnSuccessListener { result ->
                 for (document in result) {
                     location = document.getGeoPoint("position")!!
                     place_Name = document.getString("title")

                      fun displayMarker(map: GoogleMap){
                         val testingLatLong = LatLng(location.latitude, location.longitude)

                         val snippet = String.format(
                             Locale.getDefault(), "Lat: %1$.5f, Long: %2$.5f", testingLatLong.latitude, testingLatLong.longitude
                         )

                         map.addMarker(
                             MarkerOptions().position(testingLatLong).title(place_Name)
                                 .snippet(snippet).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))

                     }
                     //displayMarker()
                 }
             }
             .addOnFailureListener { exception ->
                 Log.d(TAG, "Error getting documents: ", exception)
             }

         */
/*establishments.document("FJw4W0yULAEYhlBhtaNO").get()
            .addOnSuccessListener { document ->
                locationID = document.getString("location_ID").toString()
                Log.d("Success", "DocumentSnapshot data: ${document.data}")
                firestoreCallback.onCallback()
            }
            .addOnFailureListener {
                Log.d("fail", "get failed with ")
            }*//*


    }



}
*/

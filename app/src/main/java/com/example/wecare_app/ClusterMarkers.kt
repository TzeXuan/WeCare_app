package com.example.wecare_app

import android.icu.text.Transliterator
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.maps.android.clustering.ClusterItem

class ClusterMarkers(
    private var position: GeoPoint,//Tile of place
    private var Title: String,
    private var Snippet: String,//Drawable img
    private var iconPic: Int,//String
    private var placeType: String
) : ClusterItem {

    class ClusterMakers{
    }

    override fun getPosition(): LatLng {
        TODO("Not yet implemented")
    }

    override fun getTitle(): String? {
        TODO("Not yet implemented")
    }

    override fun getSnippet(): String? {
        TODO("Not yet implemented")
    }


}

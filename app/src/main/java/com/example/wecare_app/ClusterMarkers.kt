package com.example.wecare_app

import android.icu.text.Transliterator
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.maps.android.clustering.ClusterItem

class ClusterMarkers(
    private var position: GeoPoint,//Tile of place
    private var title: String,
    private var snippet: String,//Drawable img
    var iconPic: Int,//String
    private var establishment: String
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

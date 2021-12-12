package com.example.wecare_app

import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.clustering.Cluster

import com.google.android.gms.maps.model.BitmapDescriptorFactory

import android.graphics.Bitmap

import com.google.android.gms.maps.model.MarkerOptions

import android.content.Context

import android.view.ViewGroup
import android.widget.ImageView

import com.google.maps.android.ui.IconGenerator

import com.google.maps.android.clustering.ClusterManager

import com.google.android.gms.maps.GoogleMap


class MyClusterManagerRenderer(
    context: Context, googleMap: GoogleMap?,
    clusterManager: ClusterManager<ClusterMarkers>
) :
    DefaultClusterRenderer<ClusterMarkers>(context, googleMap, clusterManager) {
    private val iconGenerator: IconGenerator
    private val imageView: ImageView
    private val markerWidth: Int
    private val markerHeight: Int

    /**
     * Rendering of the individual ClusterItems
     * @param item
     * @param markerOptions
     */

    init {

        // initialize cluster item icon generator
        iconGenerator = IconGenerator(context.applicationContext)
        imageView = ImageView(context.applicationContext)
        markerWidth = context.resources.getDimension( R.dimen.custom_marker_image).toInt()
        markerHeight = context.resources.getDimension(R.dimen.custom_marker_image).toInt()
        imageView.layoutParams = ViewGroup.LayoutParams(markerWidth, markerHeight)
        val padding = context.resources.getDimension(R.dimen.custom_marker_padding).toInt()
        imageView.setPadding(padding, padding, padding, padding)
        iconGenerator.setContentView(imageView)
    }

    override fun onBeforeClusterItemRendered(item: ClusterMarkers, markerOptions: MarkerOptions) {
        imageView.setImageResource(item.iconPic)
        val icon = iconGenerator.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.title)
    }
}



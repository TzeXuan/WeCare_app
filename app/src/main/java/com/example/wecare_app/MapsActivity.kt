package com.example.wecare_app

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.wecare_app.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.*
import java.util.*
import android.content.Intent

import android.widget.Toast

import com.google.android.gms.common.GoogleApiAvailability

import com.google.android.gms.common.ConnectionResult

import android.location.LocationManager

import android.content.DialogInterface
import android.location.Address
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import com.example.wecare_app.Constants.ERROR_DIALOG_REQUEST
import com.example.wecare_app.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
import com.example.wecare_app.Constants.PERMISSIONS_REQUEST_ENABLE_GPS
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient

import com.google.firebase.firestore.GeoPoint
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.location.Geocoder
import com.example.wecare_app.Constants.DEFAULT_ZOOM
import java.io.IOException
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val TAG = MapsActivity::class.java.simpleName
    private val REQUEST_LOCATION_PERMISSION = 1
    private var mLocationPermissionGranted = false
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        mFusedLocationClient = getFusedLocationProviderClient(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // coordinate in Malaysia
        val latitude = 4.1205
        val longitude = 101.9758

        val homeLatLng = LatLng(latitude, longitude)


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, DEFAULT_ZOOM))
        map.addMarker(MarkerOptions()
            .position(homeLatLng)
            .draggable(false)
            .title("")
            .snippet("")) // add a marker

        setMapLongClick(map)

        setPoiClick(map)

        map.setInfoWindowAdapter(CustomInfoWindowForGoogleMap(this))

        //setMapStyle(map)

        checkMapServices()
        enableMyLocation()
        intit()
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */



private fun intit(){

        binding.mapSearch.setOnEditorActionListener(OnEditorActionListener { editText, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || //ID PRESS SEARCH, ENTER, NEXT LINE OR OTHER WISE
                actionId == EditorInfo.IME_ACTION_DONE   ||
                keyEvent.action == KeyEvent.ACTION_DOWN ||
                keyEvent.action == KeyEvent.KEYCODE_ENTER) {

                //execute our method for searching
                geoLocate()

            }

            false
        })

        binding.icGps.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onClick: clicked gps icon")
            getUserLocation()
        })
    }


    private fun geoLocate() {
        Log.d(TAG, "geoLocate: geolocating")

        val geocoder = Geocoder(this@MapsActivity)
        var list: List<Address> = ArrayList()
        try {
            list = geocoder.getFromLocationName(binding.mapSearch.text.toString(), 1)
        } catch (e: IOException) {
            Log.e(TAG, "geoLocate: IOException: " + e.message)
        }
        if (list.size > 0) {
            val address: Address = list[0]
            Log.d(TAG, "geoLocate: found a location: " + address)
            moveCamera(
                LatLng(address.latitude, address.longitude), DEFAULT_ZOOM,
                address.getAddressLine(0)
            )
            Toast.makeText(this, "Location successful found!", Toast.LENGTH_SHORT).show();
        }



    }

    private fun getUserLocation(){ // gets and store user's location
        Log.d(TAG, "getLastKnownLocation: called.")
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationClient!!.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val location = task.result
                val geoPoint = GeoPoint(location.latitude, location.longitude)
                Log.d(TAG, "onComplete: latitude: " + geoPoint.latitude)
                Log.d(TAG, "onComplete: longitude: " + geoPoint.longitude)

                moveCamera(
                    LatLng(geoPoint.latitude, geoPoint.longitude),
                    DEFAULT_ZOOM,
                    "My Location"
                )
            }
        }

    }

    private fun checkMapServices(): Boolean { // check if user has google map services
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true
            }
        }
        return false
    }

    private fun buildAlertMessageNoGps() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                val enableGpsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS) // To know if user accept or decline
            })
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun isMapsEnabled(): Boolean {
        val manager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
            return false
        }
        return true
    }

    private fun getLocationPermission() { // ask for user current location
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mLocationPermissionGranted = true
            //go to main prob
            getUserLocation()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    private fun isServicesOK(): Boolean { // check if services are Installed
        Log.d(TAG, "isServicesOK: checking google services version")
        val available =
            GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this@MapsActivity)
        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working")
            return true
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occurred but we can resolve it
            Log.d(TAG, "isServicesOK: an error occurred but we can fix it")
            val dialog: Dialog = GoogleApiAvailability.getInstance()
                .getErrorDialog(this@MapsActivity, available, ERROR_DIALOG_REQUEST)
            dialog.show()
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    override fun onRequestPermissionsResult( // DISPLAYS RESULT OF PERMISSIONS
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        mLocationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    mLocationPermissionGranted = true
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: called.")
        when (requestCode) {
            PERMISSIONS_REQUEST_ENABLE_GPS -> {
                if (mLocationPermissionGranted) {
                    //getChatrooms() THIS NEED ADD ACTIVITY TO CONTINUE LIKE WHAT WILL HAPPEN IF TEHY SAY YES PROB MOVE TO MAIN MENU
                    getUserLocation()
                } else {
                    getLocationPermission() // ask for user location
                }
            }
        }
    }


    // Initializes contents of Activity's standard options menu. Only called the first time options
    // menu is displayed.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    // Called whenever an item in your options menu is selected.
    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        // Change the map type based on the user's selection.
        R.id.normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun moveCamera(latLng: LatLng, zoom: Float, title: String) {
        Log.d(
            TAG,
            "moveCamera: moving the camera to: lat: " + latLng.latitude.toString() + ", lng: " + latLng.longitude
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
        if (title != "My Location") {
            val options = MarkerOptions()
                .position(latLng)
                .title(title)
            map.addMarker(options)
        }
    }

    // allow user to add a marker using a long click
    private fun setMapLongClick(map: GoogleMap) {

        map.setOnMapLongClickListener {
            // A snippet is additional text that's displayed after the title.
            val snippet = String.format(
                Locale.getDefault(), "Lat: %1$.5f, Long: %2$.5f", it.latitude, it.longitude
            )

            // add an InfoWindow that displays the coordinates of the marker when the marker is tapped.
            map.setOnMapLongClickListener {
                map.addMarker(
                    MarkerOptions()
                        .position(it)
                        .title(getString(R.string.marked_location))
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)) // color of the pin
                )
            }
        }
    }

    // Places a marker on the map and displays an info window that contains POI name.
    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener {
            val poiMarker = map.addMarker(MarkerOptions().position(it.latLng).title(it.name))
            poiMarker?.showInfoWindow()
        }
    }

/*    // Allows map styling and theming to be customized.
    private fun setMapStyle(map: GoogleMap) {
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        }
        // handle the situation of a missing style file
        // if the file can't be loaded, then throw a Resources.NotFoundException
        catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
    }*/

    // Checks that users have given permission
    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // Checks if users have given their location and sets location enabled if so.
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            map.isMyLocationEnabled = true
        }
        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION
            )
        }
    }

}


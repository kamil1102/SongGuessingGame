package com.example.songguessinggame

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class SongsMap : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    lateinit var mGoogleMap: GoogleMap
    var mapFrag: SupportMapFragment? = null
    lateinit var mLocationRequest: LocationRequest
    var mLastLocation: Location? = null
    internal var mCurrLocationMarker: Marker? = null
    internal var mFusedLocationClient: FusedLocationProviderClient? = null
    var currentSong : Song = Song()





    //val location = Location()

     var mLocationCallback: LocationCallback = object : LocationCallback() {


        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                //The last location in the list is the newest
                val location = locationList.last()
                Log.i(
                    "MapsActivity",
                    "Location: " + location.getLatitude() + " " + location.getLongitude()
                )
                mLastLocation = location

                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker?.remove()
                }

                //Place current location marker
                 val latLng = LatLng(location.latitude, location.longitude)
                // val markerOptions = MarkerOptions()
                //  markerOptions.position(latLng)
                //markerOptions.title("Current Position")
                // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                //mCurrLocationMarker = mGoogleMap.addMarker(markerOptions)

                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0F))


            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        assert(
            supportActionBar != null //null check
        )
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        supportActionBar?.title = "Map Location Activity"

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mapFrag = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFrag?.getMapAsync(this)


    }

    public override fun onPause() {
        super.onPause()

        //stop location updates when Activity is no longer active
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        mGoogleMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 120000 // two minute interval
        mLocationRequest.fastestInterval = 120000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        dbHandler = DBHandler(this, null, null, 1)
        val songlist = dbHandler.getSongs(this)





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //Location Permission already granted
                mFusedLocationClient?.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback,
                    Looper.myLooper()
                )
                mGoogleMap.isMyLocationEnabled = true


                    mFusedLocationClient?.lastLocation?.addOnCompleteListener(this) { task ->
                        var location: Location? = task.result

                            var lat = location!!.latitude
                            var long = location!!.longitude



                            val lastLoc = LatLng(lat, long)



                        for (s in songlist){

                            currentSong = s

                            val sydney = LatLng(s.mapLatitude , s.mapLongitude)

                            



                                mGoogleMap.addMarker(MarkerOptions().position(sydney).title("Collect"))
                            mGoogleMap.setOnInfoWindowClickListener(this)



                        }

                        //mGoogleMap.addMarker(MarkerOptions().position(lastLoc).title(results.get(0).toString()))

                        }


            } else {
                //Request Location Permission
                checkLocationPermission()
            }
        } else {
            mFusedLocationClient?.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.myLooper()
            )
            mGoogleMap.isMyLocationEnabled = true
        }


    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(
                            this@SongsMap,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    }
                    .create()
                    .show()


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        mFusedLocationClient?.requestLocationUpdates(
                            mLocationRequest,
                            mLocationCallback,
                            Looper.myLooper()
                        )
                        mGoogleMap.setMyLocationEnabled(true)
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }
    companion object {
        val MY_PERMISSIONS_REQUEST_LOCATION = 1
        lateinit var dbHandler: DBHandler
    }

    override fun onInfoWindowClick(p0: Marker?) {

        var results = FloatArray(1)

       Location.distanceBetween(mGoogleMap.myLocation.latitude, mGoogleMap.myLocation.longitude,p0!!.position.latitude,p0!!.position.longitude,results)

        if(results.get(0)<300) {
            val myIntent = Intent(baseContext, MusicLibraryActivity::class.java)
            startActivity(myIntent)
            dbHandler.updadteSong(currentSong.songID.toString(), 1)
            p0?.remove()
        }


}
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    fun onButtonGoClick(v: View?) {
        val myIntent = Intent(baseContext, MusicLibraryActivity::class.java)
        startActivity(myIntent)
    }




}
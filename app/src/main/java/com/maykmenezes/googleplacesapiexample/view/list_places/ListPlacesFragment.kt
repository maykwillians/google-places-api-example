package com.maykmenezes.googleplacesapiexample.view.list_places

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maykmenezes.googleplacesapiexample.R
import com.maykmenezes.googleplacesapiexample.di.PlacesModule.placesModule
import com.maykmenezes.googleplacesapiexample.model.PlacesVO
import com.maykmenezes.googleplacesapiexample.model.ResultsItem
import kotlinx.android.synthetic.main.fragment_list_places.*
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.parameter.parametersOf

private const val GPS_ACTIVATION_PERMISSION_ID = 200
private const val LOCALIZATION_PERMISSION_ID = 201
private const val MAP_ZOOM = 15f
private const val MAP_PLACE_ZOOM = 18f

class ListPlacesFragment : Fragment(), ListPlacesContract.View, OnMapReadyCallback {

    private lateinit var bottomSheetDialogPlaceDetails: BottomSheetDialog
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    private var placesList = mutableListOf<ResultsItem>()
    private lateinit var rvPlaceList: RecyclerView

    private val presenter by inject<ListPlacesContract.Presenter> {
        parametersOf(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(placesModule)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_list_places, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configMap()
        requestGpsActivation()
    }

    private fun configMap() {
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    private fun requestGpsActivation() {
        val locationSettingsRequest = LocationSettingsRequest.Builder()
                .addLocationRequest(presenter.getLocationRequest())
                .build()
        val client = LocationServices.getSettingsClient(requireActivity())
        val task = client.checkLocationSettings(locationSettingsRequest)

        task.addOnSuccessListener {
            if(haveLocalizationPermissionAccess()) {
                createLocationCallback()
            } else {
                requestLocalizationPermission()
            }
        }

        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    startIntentSenderForResult(e.resolution.intentSender,
                            GPS_ACTIVATION_PERMISSION_ID,
                            null,
                            0,
                            0,
                            0,
                            null)
                } catch (ignored: IntentSender.SendIntentException) {

                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun createLocationCallback() {
        presenter.createLocationCallback()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.requestLocationUpdates(presenter.getLocationRequest(), presenter.getLocationCallback(), null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GPS_ACTIVATION_PERMISSION_ID) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    if(haveLocalizationPermissionAccess()) {
                        createLocationCallback()
                    } else {
                        requestLocalizationPermission()
                    }
                }

                Activity.RESULT_CANCELED -> requestGpsActivation()
            }
        }
    }

    private fun haveLocalizationPermissionAccess() = checkSelfPermission(requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun requestLocalizationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCALIZATION_PERMISSION_ID)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCALIZATION_PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                createLocationCallback()
            } else {
                if(notAskAgain()) {
                    // TODO impeditivo total, só pode utilizar depois que aceitar manualmente
                    requireActivity().finish()
                } else {
                    requestLocalizationPermission()
                }
            }
        }
    }

    private fun notAskAgain() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                !shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                TODO("VERSION.SDK_INT < M")
            }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(e: Throwable) {

    }

    private fun  bitmapDescriptorFromVector(context: Context, vectorResId:Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable?.let {
            vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
            val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas =  Canvas(bitmap)
            vectorDrawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
        return null
    }

    override fun showPlaces(places: PlacesVO) {

        showPlacesList(places)

        map.also {
            for(place in places.results) {
                it.addMarker(MarkerOptions()
                        .position(LatLng(
                                place.geometry?.location?.lat!!,
                                place.geometry.location.lng!!))
                        .title(place.name)
                        .icon(bitmapDescriptorFromVector(requireActivity(), setPlaceIcon(place))))
            }
        }
    }

    private fun setPlaceIcon(place: ResultsItem): Int {
        return when(place.types?.first()) {
            "restaurant" -> R.drawable.ic_restaurant_pin
            "bar" -> R.drawable.ic_bar_pin
            "cafe" -> R.drawable.ic_cafe_pin
            else -> R.drawable.ic_undefined_place_pin
        }
    }

    override fun showInitialMap(position: LatLng) {
        map.also {
            map.clear()
            it.clear()

            it.addMarker(MarkerOptions()
                    .position(position)
                    .title("Me")
                    .icon(bitmapDescriptorFromVector(requireActivity(), R.drawable.ic_my_location_pin)))

            val yourLocation = CameraUpdateFactory.newLatLngZoom(position, MAP_ZOOM)
            it.moveCamera(yourLocation)
        }

        setupBottomSheetDialog()
    }

    override fun stopLocationCallback() {
        fusedLocationClient.removeLocationUpdates(presenter.getLocationCallback())
    }

    private fun setupBottomSheetDialog() {
        val layout = LayoutInflater.from(requireActivity()).inflate(R.layout.list_places_layout, null)

        rvPlaceList = layout.findViewById(R.id.rv_places_list)

        rvPlaceList.also {
            it.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }

        bottomSheetDialogPlaceDetails = BottomSheetDialog(requireActivity())
        bottomSheetDialogPlaceDetails.setContentView(layout)
    }

    private fun showPlacesList(places: PlacesVO) {
        for(place in places.results) {
            placesList.add(place)
        }

        rvPlaceList.adapter = ListPlacesAdapter(placesList) { place ->
            place.geometry?.let { geometry ->
                val location = LatLng(geometry.location?.lat!!, geometry.location.lng!!)
                val position = CameraUpdateFactory.newLatLngZoom(location, MAP_PLACE_ZOOM)
                map.moveCamera(position)
            }
        }

        if(!bottomSheetDialogPlaceDetails.isShowing) {
            bottomSheetDialogPlaceDetails.show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(requireActivity())
        map = googleMap
    }

    private fun isGPSEnabled(): Boolean {
        val locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onDetach() {
        presenter.detachView()
        unloadKoinModules(placesModule)
        super.onDetach()
    }
}
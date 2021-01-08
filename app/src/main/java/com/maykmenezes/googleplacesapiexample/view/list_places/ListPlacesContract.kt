package com.maykmenezes.googleplacesapiexample.view.list_places

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.maykmenezes.googleplacesapiexample.model.PlacesVO

interface ListPlacesContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showError()
        fun showPlaces(places: PlacesVO)
        fun showMeMapPosition(position: LatLng)
        fun stopLocationCallback()
    }

    interface Presenter {
        fun detachView()
        fun getLocationRequest(): LocationRequest
        fun createLocationCallback()
        fun getLocationCallback(): LocationCallback
    }
}
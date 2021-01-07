package com.maykmenezes.googleplacesapiexample.view.list_places

import com.google.android.gms.location.LocationRequest

class LocationProviderImpl: LocationProvider {
    override fun getLocationRequest(): LocationRequest = LocationRequest.create()
            .setInterval(2000)
            .setFastestInterval(1000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
}
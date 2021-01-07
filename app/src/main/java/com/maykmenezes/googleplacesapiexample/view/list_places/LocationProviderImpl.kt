package com.maykmenezes.googleplacesapiexample.view.list_places

import com.google.android.gms.location.LocationRequest

private const val INTERVAL = 2000L
private const val FASTEST_INTERVAL = 1000L

class LocationProviderImpl: LocationProvider {
    override fun getLocationRequest(): LocationRequest = LocationRequest.create()
            .setInterval(INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
}
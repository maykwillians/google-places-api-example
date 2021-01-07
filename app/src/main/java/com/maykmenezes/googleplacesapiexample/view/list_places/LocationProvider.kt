package com.maykmenezes.googleplacesapiexample.view.list_places

import com.google.android.gms.location.LocationRequest

interface LocationProvider {
    fun getLocationRequest(): LocationRequest
}
package com.maykmenezes.googleplacesapiexample.repository

import com.maykmenezes.googleplacesapiexample.model.PlacesVO
import io.reactivex.Single

interface PlacesRepository {
    fun fetchPlaces(
        location: String,
        radius: String,
        type: String,
        keyword: String,
        key: String
    ): Single<PlacesVO>
}
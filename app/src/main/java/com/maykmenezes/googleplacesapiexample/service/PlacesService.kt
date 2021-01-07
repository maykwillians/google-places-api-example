package com.maykmenezes.googleplacesapiexample.service

import com.maykmenezes.googleplacesapiexample.model.PlacesVO
import io.reactivex.Single
import retrofit2.http.*

interface PlacesService {
    @GET("place/nearbysearch/json")
    fun fetchPlaces(
            @Query(value = "location", encoded = true) location: String,
            @Query(value = "radius") radius: String,
            @Query(value = "type") type: String,
            @Query(value = "key") key: String
    ): Single<PlacesVO>
}
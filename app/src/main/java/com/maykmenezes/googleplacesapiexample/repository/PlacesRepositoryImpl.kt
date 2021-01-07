package com.maykmenezes.googleplacesapiexample.repository

import com.maykmenezes.googleplacesapiexample.service.PlacesService

class PlacesRepositoryImpl(private val service: PlacesService): PlacesRepository {
    override fun fetchPlaces(
        location: String,
        radius: String,
        type: String,
        key: String) = service.fetchPlaces(
        location,
        radius,
        type,
        key)
}
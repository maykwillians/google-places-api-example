package com.maykmenezes.googleplacesapiexample.view.list_places

import com.maykmenezes.googleplacesapiexample.model.PlacesVO

interface ListPlacesContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showError(e: Throwable)
        fun showPlaces(places: PlacesVO)
    }

    interface Presenter {
        fun fetchPlaces(
                location: String,
                radius: String,
                type: String,
                keyword: String,
                key: String)
        fun detachView()
    }
}
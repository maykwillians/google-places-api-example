package com.maykmenezes.googleplacesapiexample.view

import com.maykmenezes.googleplacesapiexample.repository.PlacesRepository

class PlacesPresenter(
    private var view: PlacesContract.View?,
    private val repository: PlacesRepository
): PlacesContract.Presenter {

}
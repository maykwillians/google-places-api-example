package com.maykmenezes.googleplacesapiexample.view.list_places

import com.maykmenezes.googleplacesapiexample.repository.PlacesRepository

class ListPlacesPresenter(
    private var view: ListPlacesContract.View?,
    private val repository: PlacesRepository
): ListPlacesContract.Presenter {

}
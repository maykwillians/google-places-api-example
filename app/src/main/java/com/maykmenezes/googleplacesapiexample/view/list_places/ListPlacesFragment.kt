package com.maykmenezes.googleplacesapiexample.view.list_places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maykmenezes.googleplacesapiexample.R
import com.maykmenezes.googleplacesapiexample.di.PlacesModule.placesModule
import com.maykmenezes.googleplacesapiexample.model.PlacesVO
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.parameter.parametersOf

class ListPlacesFragment : Fragment(), ListPlacesContract.View {

    private val presenter by inject<ListPlacesContract.Presenter> {
        parametersOf(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(placesModule)

        presenter.fetchPlaces(
            location = "-33.8670522,151.1957362",
            radius = "1500",
            type = "restaurant",
            keyword = "cruise",
            key = "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_places, container, false)
    }

    override fun onDetach() {
        presenter.detachView()
        unloadKoinModules(placesModule)
        super.onDetach()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(e: Throwable) {

    }

    override fun showPlaces(places: PlacesVO) {
        for(place in places.results!!) {
            println("PLACE: " + place?.geometry?.location?.lat)
        }
    }
}
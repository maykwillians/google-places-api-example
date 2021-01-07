package com.maykmenezes.googleplacesapiexample.di

import com.maykmenezes.googleplacesapiexample.repository.PlacesRepository
import com.maykmenezes.googleplacesapiexample.repository.PlacesRepositoryImpl
import com.maykmenezes.googleplacesapiexample.service.PlacesService
import com.maykmenezes.googleplacesapiexample.service.ServiceConfig.retrofit
import com.maykmenezes.googleplacesapiexample.view.list_places.ListPlacesContract
import com.maykmenezes.googleplacesapiexample.view.list_places.ListPlacesPresenter
import com.maykmenezes.googleplacesapiexample.view.list_places.LocationProvider
import com.maykmenezes.googleplacesapiexample.view.list_places.LocationProviderImpl
import org.koin.dsl.module

object PlacesModule {
    val placesModule = module {
        single<PlacesService>{
            retrofit.create(PlacesService::class.java)
        }
        factory<PlacesRepository> {
            PlacesRepositoryImpl(service = get())
        }
        factory<LocationProvider> {
            LocationProviderImpl()
        }
        factory<ListPlacesContract.Presenter> { (view: ListPlacesContract.View) ->
            ListPlacesPresenter(
                    view = view,
                    repository = get(),
                    locationProvider = get())
        }
    }
}
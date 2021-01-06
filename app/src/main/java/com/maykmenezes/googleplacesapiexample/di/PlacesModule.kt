package com.maykmenezes.googleplacesapiexample.di

import com.maykmenezes.googleplacesapiexample.repository.PlacesRepository
import com.maykmenezes.googleplacesapiexample.repository.PlacesRepositoryImpl
import com.maykmenezes.googleplacesapiexample.service.PlacesService
import com.maykmenezes.googleplacesapiexample.service.ServiceConfig.retrofit
import com.maykmenezes.googleplacesapiexample.view.PlacesContract
import com.maykmenezes.googleplacesapiexample.view.PlacesPresenter
import org.koin.dsl.module

object PlacesModule {
    val placesModule = module {
        single<PlacesService>{
            retrofit.create(PlacesService::class.java)
        }
        factory<PlacesRepository> {
            PlacesRepositoryImpl(service = get())
        }
        factory<PlacesContract.Presenter> { (view: PlacesContract.View) ->
            PlacesPresenter(view = view, repository = get())
        }
    }
}
package com.maykmenezes.googleplacesapiexample.view.list_places

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.maykmenezes.googleplacesapiexample.model.PlacesVO
import com.maykmenezes.googleplacesapiexample.repository.PlacesRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ListPlacesPresenter(
        private var view: ListPlacesContract.View?,
        private val repository: PlacesRepository,
        private val locationProvider: LocationProvider
): ListPlacesContract.Presenter {

    private lateinit var locationCallback: LocationCallback

    private fun fetchPlaces(location: String, radius: String, type: String, keyword: String, key: String) {
        repository.fetchPlaces(location, radius, type, keyword, key)
                .subscribeOn(Schedulers.io())
                .doAfterTerminate { view?.hideLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<PlacesVO> {
                    override fun onSubscribe(d: Disposable) {
                        view?.showLoading()
                    }
                    override fun onSuccess(places: PlacesVO) {
                        view?.showPlaces(places)
                    }
                    override fun onError(throwable: Throwable) {
                        view?.showError(throwable)
                    }
                })
    }

    override fun getLocationRequest() = locationProvider.getLocationRequest()

    override fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                view?.stopLocationCallback()

                for (location in locationResult.locations) {

                    view?.showInitialMap(LatLng(location.latitude, location.longitude))

                    fetchPlaces(
                            location = "${location.latitude},${location.longitude}",
                            radius = "1500",
                            type = "restaurant",
                            keyword = "cruise",
                            key = "")
                }
            }
        }
    }

    override fun getLocationCallback(): LocationCallback = locationCallback

    override fun detachView() {
        view = null
    }
}
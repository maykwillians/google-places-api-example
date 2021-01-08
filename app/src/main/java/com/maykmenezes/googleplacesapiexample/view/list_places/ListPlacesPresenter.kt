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

private const val PLACES_FIND_RADIUS = "3000"
private const val PLACE_RESTAURANT_TYPE = "restaurant"
private const val PLACE_CAFE_TYPE = "bar"
private const val PLACE_BAR_TYPE = "cafe"
private const val API_KEY = ""

class ListPlacesPresenter(
        private var view: ListPlacesContract.View?,
        private val repository: PlacesRepository,
        private val locationProvider: LocationProvider
): ListPlacesContract.Presenter {

    private lateinit var locationCallback: LocationCallback

    override fun getLocationRequest() = locationProvider.getLocationRequest()

    override fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                view?.stopLocationCallback()
                for (location in locationResult.locations) {
                    val myLocation = "${location.latitude},${location.longitude}"
                    view?.showMeMapPosition(LatLng(location.latitude, location.longitude))

                    fetchPlace(myLocation, PLACE_RESTAURANT_TYPE)
                    fetchPlace(myLocation, PLACE_CAFE_TYPE)
                    fetchPlace(myLocation, PLACE_BAR_TYPE)
                }
            }
        }
    }

    private fun fetchPlace(location: String, type: String) {
        fetchPlaces(
                location = location,
                type = type,
                key = API_KEY)
    }

    private fun fetchPlaces(location: String, type: String, key: String) {
        repository.fetchPlaces(location, PLACES_FIND_RADIUS, type, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<PlacesVO> {
                    override fun onSubscribe(d: Disposable) {
                        view?.showLoading()
                    }
                    override fun onSuccess(places: PlacesVO) {
                        if(places.status == "REQUEST_DENIED") {
                            view?.hideLoading()
                            view?.showError()
                        } else {
                            view?.hideLoading()
                            view?.showPlaces(places)
                        }
                    }
                    override fun onError(throwable: Throwable) {
                        view?.hideLoading()
                        view?.showError()
                    }
                })
    }

    override fun getLocationCallback(): LocationCallback = locationCallback

    override fun detachView() {
        view = null
    }
}
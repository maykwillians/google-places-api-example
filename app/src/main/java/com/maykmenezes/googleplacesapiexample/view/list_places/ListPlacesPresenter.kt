package com.maykmenezes.googleplacesapiexample.view.list_places

import com.maykmenezes.googleplacesapiexample.model.PlacesVO
import com.maykmenezes.googleplacesapiexample.repository.PlacesRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ListPlacesPresenter(
    private var view: ListPlacesContract.View?,
    private val repository: PlacesRepository
): ListPlacesContract.Presenter {

    override fun fetchPlaces(location: String, radius: String, type: String, keyword: String, key: String) {
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

    override fun detachView() {
        view = null
    }
}
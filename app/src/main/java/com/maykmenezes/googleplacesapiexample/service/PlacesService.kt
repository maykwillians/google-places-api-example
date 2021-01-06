package com.maykmenezes.googleplacesapiexample.service

import com.maykmenezes.googleplacesapiexample.model.PlacesVO
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PlacesService {
    @POST("place/nearbysearch/json")
    @FormUrlEncoded
    fun fetchPlaces(
        @Field("location") location: String,
        @Field("radius") radius: String,
        @Field("type") type: String,
        @Field("keyword") keyword: String,
        @Field("key") key: String
    ): Single<PlacesVO>
}
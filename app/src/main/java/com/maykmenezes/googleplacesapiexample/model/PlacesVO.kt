package com.maykmenezes.googleplacesapiexample.model

import com.google.gson.annotations.SerializedName

data class PlacesVO(

		@field:SerializedName("html_attributions")
		val htmlAttributions: List<Any?>? = null,

		@field:SerializedName("results")
		val results: List<ResultsItem?>? = null,

		@field:SerializedName("status")
		val status: String? = null
)

data class PhotosItem(

		@field:SerializedName("photo_reference")
		val photoReference: String? = null,

		@field:SerializedName("width")
		val width: Int? = null,

		@field:SerializedName("html_attributions")
		val htmlAttributions: List<Any?>? = null,

		@field:SerializedName("height")
		val height: Int? = null
)

data class Geometry(

		@field:SerializedName("location")
		val location: Location? = null
)

data class Location(

		@field:SerializedName("lng")
		val lng: Double? = null,

		@field:SerializedName("lat")
		val lat: Double? = null
)

data class ResultsItem(

		@field:SerializedName("reference")
		val reference: String? = null,

		@field:SerializedName("types")
		val types: List<String?>? = null,

		@field:SerializedName("icon")
		val icon: String? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("opening_hours")
		val openingHours: OpeningHours? = null,

		@field:SerializedName("geometry")
		val geometry: Geometry? = null,

		@field:SerializedName("vicinity")
		val vicinity: String? = null,

		@field:SerializedName("id")
		val id: String? = null,

		@field:SerializedName("photos")
		val photos: List<PhotosItem?>? = null,

		@field:SerializedName("place_id")
		val placeId: String? = null
)

data class OpeningHours(

		@field:SerializedName("open_now")
		val openNow: Boolean? = null
)

package com.maykmenezes.googleplacesapiexample.model

import com.google.gson.annotations.SerializedName

data class PlacesVO(
		@field:SerializedName("results")
		val results: List<ResultsItem>
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

		@field:SerializedName("rating")
		val rating: String? = null,

		@field:SerializedName("geometry")
		val geometry: Geometry? = null,

		@field:SerializedName("vicinity")
		val vicinity: String? = null,

		@field:SerializedName("id")
		val id: String? = null,

		@field:SerializedName("place_id")
		val placeId: String? = null
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
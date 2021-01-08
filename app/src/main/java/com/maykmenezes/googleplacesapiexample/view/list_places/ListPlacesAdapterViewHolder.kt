package com.maykmenezes.googleplacesapiexample.view.list_places

import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maykmenezes.googleplacesapiexample.R
import com.maykmenezes.googleplacesapiexample.model.ResultsItem

class ListPlacesAdapterViewHolder(
        itemView: View,
        private val callBack: ((place: ResultsItem) -> Unit)
): RecyclerView.ViewHolder(itemView) {
    fun bind(place: ResultsItem) {
        val placeName = itemView.findViewById<TextView>(R.id.tv_place_name)
        val placeRating = itemView.findViewById<TextView>(R.id.place_rating)
        val placeRatingStars = itemView.findViewById<RatingBar>(R.id.place_rating_stars)

        placeName.text = place.name
        placeRating.text = place.rating
        placeRatingStars.rating = place.rating?.toFloat()!!
    }
}
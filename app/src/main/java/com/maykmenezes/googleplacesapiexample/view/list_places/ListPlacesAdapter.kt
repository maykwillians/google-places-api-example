package com.maykmenezes.googleplacesapiexample.view.list_places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maykmenezes.googleplacesapiexample.R
import com.maykmenezes.googleplacesapiexample.model.ResultsItem

class ListPlacesAdapter(
        private val places: List<ResultsItem>,
        private val callBack: ((place: ResultsItem) -> Unit)
): RecyclerView.Adapter<ListPlacesAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPlacesAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.place_item_layout, parent, false)
        return ListPlacesAdapterViewHolder(itemView, callBack)
    }

    override fun onBindViewHolder(holder: ListPlacesAdapterViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount() = places.count()
}
package com.magalhaes.flickrapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

private const val TAG = "FlickrRecyclerViewAdapt"

class FlickrImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.photo_title)
}

class FlickrRecyclerViewAdpter(private var photoList : List<Photo>) : RecyclerView.Adapter<FlickrImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        //Called by the layout manager when it needs a new view
        Log.d(TAG, ".onCreateViewHolder new view requested")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return FlickrImageViewHolder(view)
    }

    fun loadNewData(newPhotos: List<Photo>) {
        photoList = newPhotos
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int) : Photo? {
        return if (photoList.isNotEmpty()) photoList[position] else null
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount called")
        return if (photoList.isEmpty()) photoList.size else 0
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        // called by the layout manager when it wants new data in an existing view

        val photoItem = photoList[position]
        Log.d(TAG, ".onBindingViewHolder: ${photoItem.title} --> $position")
        Picasso.with(holder.thumbnail.context).load(photoItem.image)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(holder.thumbnail)
        holder.title.text = photoItem.title
    }

}
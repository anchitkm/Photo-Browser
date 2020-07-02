package com.anchit.photobrowser.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.anchit.photobrowser.R
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.util.extensions.getProgressDrawable
import com.anchit.photobrowser.util.extensions.loadImage
import kotlinx.android.synthetic.main.layout_grid_item.view.*

class HomeRecyclerViewAdapter(var listFlickrPhoto: ArrayList<FlickrResponse.Photos.Photo>,listener: ItemClickListener) :
    RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    val clickListener=listener

    fun updatePhoto(newPhoto: List<FlickrResponse.Photos.Photo>) {
        listFlickrPhoto.clear()
        listFlickrPhoto.addAll(newPhoto)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.layout_grid_item, parent, false)

    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFlickrPhoto[position])
        holder.itemView.setOnClickListener {
            clickListener.onitemClicked(position)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = listFlickrPhoto.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageView = view.image

        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(flickrPhoto: FlickrResponse.Photos.Photo) {
            imageView.loadImage(flickrPhoto.urlS, progressDrawable)
        }
    }

    interface ItemClickListener{
        fun onitemClicked(pos:Int)
    }
}
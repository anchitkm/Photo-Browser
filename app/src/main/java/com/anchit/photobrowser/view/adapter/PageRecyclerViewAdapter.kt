package com.anchit.photobrowser.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anchit.photobrowser.databinding.LayoutGridItemBinding
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.util.extensions.loadImage

class PageRecyclerViewAdapter<T>():
PagedListAdapter<FlickrResponse.Photos.Photo,PageRecyclerViewAdapter<T>.Holder>(photoListDiff){

    private var mListener:ItemClickListener?=null
    var binding:LayoutGridItemBinding?=null


    companion object{
        val photoListDiff=object : DiffUtil.ItemCallback<FlickrResponse.Photos.Photo>(){
            override fun areItemsTheSame(
                oldItem: FlickrResponse.Photos.Photo,
                newItem: FlickrResponse.Photos.Photo
            ): Boolean {
                return oldItem.urlS==newItem.urlS
            }

            override fun areContentsTheSame(
                oldItem: FlickrResponse.Photos.Photo,
                newItem: FlickrResponse.Photos.Photo
            ): Boolean {
                return oldItem ==newItem
            }
        }
    }


    fun initListener(listener: ItemClickListener){
        mListener=listener
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    interface ItemClickListener{
        fun onitemClicked(pos:Int)
    }

    inner class Holder(var binding: ViewDataBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        binding= LayoutGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding!!)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val dataModel:FlickrResponse.Photos.Photo? = getItem(position)

        binding?.image?.loadImage(dataModel?.urlS)
        binding?.photoInfo=dataModel
        binding?.adapter=this

        holder.itemView.setOnClickListener {
            mListener?.onitemClicked(position)

        }

    }

}
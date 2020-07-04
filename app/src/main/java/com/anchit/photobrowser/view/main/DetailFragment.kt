package com.anchit.photobrowser.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.anchit.photobrowser.databinding.FragmentDetailBinding
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.ui.component.model.CarouselItemModel
import com.anchit.photobrowser.viewmodel.PhotosViewModel
import kotlinx.android.synthetic.main.component_carousel.view.*


private const val ARG_POS = "position"
class DetailFragment : Fragment() {


    private lateinit var binding: FragmentDetailBinding
    private var selectedItemPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedItemPosition = it.getInt(ARG_POS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = activity?.run {
            ViewModelProvider(this).get(PhotosViewModel::class.java)
        }!!

        viewModel.pagedPhotoList.observe(viewLifecycleOwner, Observer { listFlickrPhoto ->
            listFlickrPhoto?.let {
                binding.root.carousal_view_.setItemList(formedCarouselData(it))
                binding.root.carousal_view_.getViewPager().currentItem = selectedItemPosition
            }
        })

    }

    /**
     * This is to create and init the carousel item model needed to show the images in view pager.
     */
    private fun formedCarouselData(pagedList: PagedList<FlickrResponse.Photos.Photo>): MutableList<CarouselItemModel> {

        val mCarouselDataList: MutableList<CarouselItemModel> = mutableListOf()
        for (item in pagedList) {
            val carouselItemModel = CarouselItemModel(item.urlS)
            mCarouselDataList.add(carouselItemModel)
        }

        return mCarouselDataList

    }

    companion object {
        fun newInstance(pos: Int) = DetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POS, pos)
            }
        }
    }


}
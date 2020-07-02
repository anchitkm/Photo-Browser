package com.anchit.photobrowser.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.anchit.photobrowser.R
import com.anchit.photobrowser.databinding.MainFragmentBinding
import com.anchit.photobrowser.view.adapter.HomeRecyclerViewAdapter
import com.anchit.photobrowser.viewmodel.PhotosViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: PhotosViewModel
    private lateinit var binding:MainFragmentBinding
    private lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private val NUM_COLUMNS=2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding=MainFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeRecyclerViewAdapter = HomeRecyclerViewAdapter(arrayListOf())
        staggeredGridLayoutManager = StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayout.VERTICAL)
        binding.root.recycler_view.apply {
            layoutManager = staggeredGridLayoutManager
            adapter = homeRecyclerViewAdapter
        }
        viewModel = ViewModelProvider(this).get(PhotosViewModel::class.java)
        observeViewModel()

        viewModel.getFlickrPhotos()

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }


    private fun observeViewModel() {

        viewModel.listFlickrPhoto.observe(viewLifecycleOwner,Observer { listFlickrPhoto ->
            listFlickrPhoto?.let {

                homeRecyclerViewAdapter.updatePhoto(it)
                binding.root.swipeRefreshLayout.isRefreshing = false
            }
        })

    }

}
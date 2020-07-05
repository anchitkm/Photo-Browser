package com.anchit.photobrowser.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.anchit.photobrowser.R
import com.anchit.photobrowser.databinding.MainFragmentBinding
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.util.AppUtils
import com.anchit.photobrowser.util.extensions.showSnackBar
import com.anchit.photobrowser.view.adapter.PageRecyclerViewAdapter
import com.anchit.photobrowser.viewmodel.PhotosViewModel
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*


class HomeFragment : Fragment(), PageRecyclerViewAdapter.ItemClickListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: PhotosViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var pageRecyclerViewAdapter: PageRecyclerViewAdapter<Any?>
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager

    private lateinit var photosList:PagedList<FlickrResponse.Photos.Photo>

    private val NUM_COLUMNS = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(PhotosViewModel::class.java)
        }!!

        fetchData()

        swipeRefreshLayout.setOnRefreshListener {

            if(::photosList.isInitialized && photosList.size>0) {
                pageRecyclerViewAdapter.submitList(null)
            }
               fetchData()
        }
    }

    /**
     * This is to fetch the data from repository and observing the viewmodels.
     */
    private fun fetchData(){
        if(AppUtils.isNetworkConnected) {
            activity?.errorView?.visibility=View.GONE
            viewModel.initDataSource()
            observeViewModel()
        }else{
            if(swipeRefreshLayout.isRefreshing){
                swipeRefreshLayout.isRefreshing=false
            }
            showSnackBar(binding.root,"Please Check your Network Connection")
            activity?.errorView?.visibility=View.VISIBLE

        }
    }

    private fun initListener() {

        if(this::pageRecyclerViewAdapter.isInitialized){
            pageRecyclerViewAdapter.initListener(this)
        }
    }

    private fun observeViewModel() {


        viewModel.pagedPhotoList.observe(activity!!, Observer {
            photosList=it
            setAdapter()
        })

    }

    private fun setAdapter() {
        pageRecyclerViewAdapter= PageRecyclerViewAdapter()
        staggeredGridLayoutManager = StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayout.VERTICAL)
        binding.root.recycler_view.apply {
            layoutManager = staggeredGridLayoutManager
            adapter = pageRecyclerViewAdapter


        }
        binding.root.recycler_view.setHasFixedSize(false)
        pageRecyclerViewAdapter.submitList(photosList)
        //to dismiss the refresh loader
        if(swipeRefreshLayout.isRefreshing)
        swipeRefreshLayout.isRefreshing=false
        initListener()

    }

    override fun onItemClicked(pos: Int) {
        val detailFrag = DetailFragment.newInstance(pos)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, detailFrag, "detailFragment")
            ?.addToBackStack(tag)
            ?.commit()
    }


}
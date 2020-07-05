package com.anchit.photobrowser.view.main

import android.Manifest
import android.annotation.TargetApi
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.anchit.photobrowser.R
import com.anchit.photobrowser.databinding.FragmentDetailBinding
import com.anchit.photobrowser.service.model.FlickrResponse
import com.anchit.photobrowser.ui.component.model.CarouselItemModel
import com.anchit.photobrowser.ui.component.view.CarouselView
import com.anchit.photobrowser.util.AppUtils
import com.anchit.photobrowser.util.extensions.showSnackBar
import com.anchit.photobrowser.viewmodel.PhotosViewModel
import kotlinx.android.synthetic.main.component_carousel.*
import kotlinx.android.synthetic.main.component_carousel.view.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.main_activity.*
import java.io.File


class DetailFragment : Fragment(), CarouselView.IPageSelected {

    private val ARG_POS = "position"


    private lateinit var binding: FragmentDetailBinding
    private var selectedItemPosition = 0
    private val mCarouselDataList: MutableList<CarouselItemModel> = mutableListOf()
    private var msg: String? = ""
    private var lastMsg = ""
    private var viewModel: PhotosViewModel? = null


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
        viewModel = activity?.run {
            ViewModelProvider(this).get(PhotosViewModel::class.java)
        }!!
        binding.root.carousal_view_.setPageSelectedListener(this)

        binding.btnDownload.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                askImageDownloadPermission()
            } else {
                downloadImage(mCarouselDataList[carousal_view_.getCurrentPosition()].carouselUrl)
            }
        }

        viewModel?.pagedPhotoList?.observe(viewLifecycleOwner, Observer { listFlickrPhoto ->
            listFlickrPhoto?.let {

                Log.e("Test Clicked item URL", it[selectedItemPosition]?.urlS!!)

                binding.root.carousal_view_.setItemList(formedCarouselData(it))
                binding.root.carousal_view_.getViewPager().currentItem = selectedItemPosition
            }
        })

        viewModel?.photoInfo?.observeForever(Observer {
            binding.root.tv_imgTitle.text = it.photo.title.content
            binding.root.tv_imgDesc.text = it.photo.description.content
        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted.
                    // Download the Image.
                    downloadImage(mCarouselDataList[carousal_view_.getCurrentPosition()].carouselUrl)
                } else {
                    // permission denied,
                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                }
                return
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun askImageDownloadPermission() {

        //this is to check if permission is already granted or not.
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.permission_required))
                    .setMessage(getString(R.string.permission_is_required_to))
                    .setPositiveButton(getString(R.string.allow)) { dialog, _ ->
                        requestPermissions(
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                        )

                        dialog.dismiss()
                    }
                    .setNegativeButton(getString(R.string.deny)) { dialog, _ -> dialog.cancel() }
                    .show()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                )

            }
        } else {
            // in case the Permission has already been granted
            downloadImage(mCarouselDataList[carousal_view_.getCurrentPosition()].carouselUrl)
        }

    }

    /**
     * This is to download the image from the provided url.
     */
    private fun downloadImage(imageUrl: String) {

        Log.e(
            "Test Download Url",
            mCarouselDataList[carousal_view_.getCurrentPosition()].carouselUrl
        )

        val directory = File(Environment.DIRECTORY_PICTURES)

        if (!directory.exists()) {
            directory.mkdirs()
        }

        val downloadManager =
            activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(imageUrl.trim())

        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(imageUrl.substring(imageUrl.lastIndexOf("/") + 1))
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    directory.toString(),
                    imageUrl.substring(imageUrl.lastIndexOf("/") + 1)
                )
        }

        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread(Runnable {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                msg = statusMessage(status)
                if (msg != lastMsg) {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                    lastMsg = msg ?: ""
                }
                cursor.close()
            }
        }).start()
    }

    /**
     * This is to update the status in toast for downloading
     */
    private fun statusMessage(status: Int): String? {
        var msg = ""
        msg = when (status) {
            DownloadManager.STATUS_FAILED -> getString(R.string.download_has_failed)
            DownloadManager.STATUS_PAUSED -> getString(R.string.paused)
            DownloadManager.STATUS_PENDING -> getString(R.string.pending)
            DownloadManager.STATUS_RUNNING -> getString(R.string.downloading)
            DownloadManager.STATUS_SUCCESSFUL -> getString(R.string.image_downloaded)
            else -> getString(R.string.nothing_to_show)
        }
        return msg
    }

    /**
     * This is to create and init the carousel item model needed to show the images in view pager.
     */
    private fun formedCarouselData(pagedList: PagedList<FlickrResponse.Photos.Photo>): MutableList<CarouselItemModel> {
        for (item in pagedList) {
            val carouselItemModel = CarouselItemModel(item.urlS, item.id)
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

        private const val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }

    override fun onPageSelected() {


        if(AppUtils.isNetworkConnected) {
            activity?.errorView?.visibility=View.GONE
            fetchPhotoInfo(mCarouselDataList[binding.root.carousal_view_.getCurrentPosition()].photoId)
        }else{
            showSnackBar(binding.root,"Please Check your Network Connection")
        }
    }

    /**
     * This is to make the api call to get the photos info.
     */
    private fun fetchPhotoInfo(photoId: String) {
        viewModel?.getPhotoInfo(photoId)
    }


}
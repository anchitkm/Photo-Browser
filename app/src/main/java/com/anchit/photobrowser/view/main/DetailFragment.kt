package com.anchit.photobrowser.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anchit.photobrowser.databinding.FragmentDetailBinding
import com.anchit.photobrowser.util.extensions.loadImage
import com.anchit.photobrowser.viewmodel.PhotosViewModel
import kotlinx.android.synthetic.main.fragment_detail.view.*


private const val ARG_POS = "position"
class DetailFragment : Fragment() {


    private lateinit var binding:FragmentDetailBinding
    private var itemPosition=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemPosition = it.getInt(ARG_POS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = activity?.run {
            ViewModelProvider(this).get(PhotosViewModel::class.java)
        }!!

        viewModel.pagedPhotoList.observe(viewLifecycleOwner, Observer { listFlickrPhoto ->
            listFlickrPhoto?.let {
                binding.root.iv_fullSize.loadImage(it[itemPosition]?.urlS)
            }
        })

    }

    companion object {
        fun newInstance(pos: Int) = DetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_POS, pos)
            }
        }
    }


}
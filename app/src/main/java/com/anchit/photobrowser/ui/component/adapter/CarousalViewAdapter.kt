package com.anchit.photobrowser.ui.component.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.anchit.photobrowser.R
import com.anchit.photobrowser.ui.component.model.CarouselItemModel
import com.anchit.photobrowser.util.extensions.loadImage


open class CarousalViewAdapter : PagerAdapter() {
    private var carouselImageContainer: ViewGroup? = null
    private val TAG: String = CarousalViewAdapter::class.java.toString()

    private var mCarousalItemList: List<CarouselItemModel>? = null

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return mCarousalItemList?.size ?: 0
    }


    override fun getPageWidth(position: Int): Float {
        return 1.0f
    }

    fun setCarousalItemList(carousalItemList: List<CarouselItemModel>) {
        this.mCarousalItemList = carousalItemList
        notifyDataSetChanged()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.caraousal_viewpager_item, container, false)

        carouselImageContainer = view.findViewById(R.id.carousel_image_container)

        enableImageView(view, position)

        container.addView(view)

        return view
    }

    private fun enableImageView(view: View, position: Int) {
        val imageView = view.findViewById(R.id.ivCarousal_image) as ImageView

        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        imageView.loadImage(mCarousalItemList?.get(position)?.carouselUrl)
        carouselImageContainer?.visibility = View.VISIBLE

    }

}

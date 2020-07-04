package com.anchit.photobrowser.ui.component.view

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater.from
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.PageTransformer
import com.anchit.photobrowser.R
import com.anchit.photobrowser.ui.component.adapter.CarousalViewAdapter
import com.anchit.photobrowser.ui.component.adapter.CarouselViewPager
import com.anchit.photobrowser.ui.component.adapter.InfiniteAdapter
import com.anchit.photobrowser.ui.component.model.CarouselItemModel
import kotlinx.android.synthetic.main.caraousal_view.view.*


/**
 * This is the reusable carousel component for showing images in viewpager.
 */
class CarouselView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ViewPager.OnPageChangeListener {


    private lateinit var mCarouselContainer: View
    private lateinit var mCarousalViewAdapter: CarousalViewAdapter
    private val TAG = CarouselView::javaClass.name
    private lateinit var wrapperAdapter: InfiniteAdapter

    private var mCarouselItemList: List<CarouselItemModel> = ArrayList()


    init {
        initView(context)
    }

    fun setItemList(pageCount: List<CarouselItemModel>) {
        this.mCarouselItemList = pageCount
        setData()
    }

    private fun initView(context: Context) {
        if (!isInEditMode) {
            mCarouselContainer = from(context).inflate(R.layout.caraousal_view, this, true)
            mCarouselContainer.carousel_viewpager.addOnPageChangeListener(this)
            mCarousalViewAdapter = CarousalViewAdapter()

        }

    }

    private fun setData() {

        mCarousalViewAdapter.setCarousalItemList(mCarouselItemList)

        wrapperAdapter = InfiniteAdapter(mCarousalViewAdapter)
        mCarouselContainer.carousel_viewpager.adapter = wrapperAdapter

        mCarouselContainer.carousel_viewpager.setPagingEnabled(mCarouselItemList.size > 1)

            showTextIndicator()
    }

    /**
     * This is to get the instance of Viewpager
     */
    fun getViewPager(): CarouselViewPager {
        return mCarouselContainer.carousel_viewpager
    }

    override fun onPageScrollStateChanged(state: Int) {
        Log.d("CarouselView", "onPageScrollStateChanged: $state")
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.d("CarouselView", "onPageScrolled: $position")
    }

    override fun onPageSelected(position: Int) {
        Log.d("CarouselView", "onPageSelected: $position")

        val realPosition = position % wrapperAdapter.getRealCount()
        val indicatorText = SpannableString(
            (realPosition + 1).toString() + " of "
                    + (mCarouselItemList.size).toString()
        )
        indicatorText.setSpan(
            ForegroundColorSpan(Color.BLACK), 0, 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mCarouselContainer.indicatorText.text = indicatorText

    }

    private fun showTextIndicator() {
        mCarouselContainer.indicatorText.visibility = View.VISIBLE
        val indicatorText = SpannableString(
            1.toString() + " of "
                    + (mCarouselItemList.size).toString()
        )
        indicatorText.setSpan(
            ForegroundColorSpan(Color.BLACK), 0, 1,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mCarouselContainer.indicatorText.text = indicatorText
    }


}

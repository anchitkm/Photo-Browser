package com.anchit.photobrowser.ui.component.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

/**
 * This is our custom view Pager class which handles circular paging with Infinite adapter.
 */
class CarouselViewPager @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null): ViewPager(context,attrs) {

    private var isPagingEnabled = false

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (isPagingEnabled) {
            super.onInterceptTouchEvent(event)
        } else false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (isPagingEnabled) {
            super.onTouchEvent(event)
        } else false
    }

    fun setPagingEnabled(pagingEnabled: Boolean) {
        isPagingEnabled = pagingEnabled
    }

    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)
        // offset first element so that we can scroll to the left
        currentItem = 0
    }

    override fun setCurrentItem(item: Int) {
        // offset the current item to ensure there is space to scroll
        setCurrentItem(item,false)
    }
    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        if (adapter?.count ==0) {
            super.setCurrentItem(item, smoothScroll)
            return
        }

        val newItem = getOffsetAmount() + (item.rem(adapter?.count?:1))
        super.setCurrentItem(newItem, smoothScroll)

    }

    private fun getOffsetAmount(): Int {
        if (adapter?.count == 0) {
            return 0
        }
        return if (adapter is InfiniteAdapter) {
            // Return the actual item position in the data backing InfiniteAdapter
            (adapter as InfiniteAdapter).getRealCount() * 100
        } else {
            0
        }
    }


}

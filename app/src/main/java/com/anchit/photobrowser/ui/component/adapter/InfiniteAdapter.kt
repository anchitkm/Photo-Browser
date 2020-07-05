package com.anchit.photobrowser.ui.component.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter


/**
 * A PagerAdapter that wraps around ViewPagerAdapter to handle paging wrap-around.
 */

class InfiniteAdapter(
    private val adapter: PagerAdapter
): CarousalViewAdapter(
) {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return adapter.isViewFromObject(view,`object`)
    }

    override fun getCount(): Int {
        if(getRealCount() ==0){
            return 0
        }
        return Int.MAX_VALUE
    }

    /**
     * @return the getCount() result of the wrapped adapter
     */
    fun getRealCount():Int{
        return adapter.count
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val virtualPosition = position % getRealCount()
        return adapter.instantiateItem(container,virtualPosition)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val virtualPosition = position % getRealCount()
        adapter.destroyItem(container,virtualPosition,`object`)
    }
}
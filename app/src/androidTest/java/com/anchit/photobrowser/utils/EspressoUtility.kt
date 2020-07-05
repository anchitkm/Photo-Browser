package com.anchit.photobrowser.utils

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId


object EspressoUtility {


    /**
     * This method will search on UI for View matching given resourceId. Once the resource is found
     * it will perform click action on the view.
     *
     * @param resourceId Resource id as defined by R.id.resourceId for the element to be searched on
     * UI
     */
    fun performClick(resourceId: Int) {
            try {
                onView(withId(resourceId))
                    .check(matches(withId(resourceId)))
                    .perform(ViewActions.click())
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
    }



}
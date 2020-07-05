package com.anchit.photobrowser

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.anchit.photobrowser.utils.EspressoUtility
import com.anchit.photobrowser.view.main.HomeActivity
import org.junit.Assert
import org.junit.Test

class PhotoBrowserUiTest {


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.anchit.photobrowser", appContext.packageName)
    }

    /**
     * This is to validate if app is getting launched successfully
     */
    @Test
    fun appLaunchSuccessfully(){
        ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        Thread.sleep(3000)
    }

    /**
     * This is to launch the detail screen with mentioned click position = 1
     */
    @Test
    fun launchDetailScreen(){
        ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()));
        onView(withText("Download")).check(matches(isDisplayed()))

    }

    /**
     * This is to launch the app, open detail screen and download the image.
     */
    @Test
    fun downloadImage(){
        ActivityScenario.launch(HomeActivity::class.java)
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()));
        onView(withText("Download")).check(matches(isDisplayed()))
        EspressoUtility.performClick(R.id.btn_download)
        Thread.sleep(5000)

    }


}
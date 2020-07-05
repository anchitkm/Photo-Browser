package com.anchit.photobrowser.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anchit.photobrowser.R
import com.anchit.photobrowser.util.AppUtils

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        AppUtils.registerNetworkCallBack(this)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow()
        }
    }
}
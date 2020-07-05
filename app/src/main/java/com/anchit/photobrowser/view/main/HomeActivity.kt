package com.anchit.photobrowser.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.anchit.photobrowser.R
import com.anchit.photobrowser.util.AppUtils

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        AppUtils.registerNetworkCallBack(this)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, HomeFragment.newInstance())
                .addToBackStack("Home Fragment")
                .commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(supportFragmentManager.backStackEntryCount==0)
            this.finish()
    }

}
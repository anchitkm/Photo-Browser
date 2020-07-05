package com.anchit.photobrowser.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import java.lang.Exception

object AppUtils {

    var isNetworkConnected: Boolean=false

    fun registerNetworkCallBack(context: Context){
        try {
            val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                connectivityManager.registerDefaultNetworkCallback(object:
                    ConnectivityManager.NetworkCallback() {

                    override fun onAvailable(network: Network) {
                        isNetworkConnected=true
                    }

                    override fun onLost(network: Network) {
                        isNetworkConnected=false
                    }
                })
            }
        }catch(e:Exception){
            isNetworkConnected=false
        }
    }
}
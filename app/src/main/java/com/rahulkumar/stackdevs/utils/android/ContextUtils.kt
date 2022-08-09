package com.rahulkumar.stackdevs.utils.android

import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.activity.ComponentActivity

/**
 * [Context] extension property : **true** if device has access to internet **false** otherwise.
 *
 */
val Context.canConnectToInternet: Boolean
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
        return with(connectivityManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activeNetwork?.let {
                    getNetworkCapabilities(it)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                }
            } else {
                @Suppress("DEPRECATION")
                activeNetworkInfo?.isConnected
            }
        } == true
    }

fun Context.getActivity(): ComponentActivity? {
    var currentContext = this

    while (currentContext is ContextWrapper) {
        if (currentContext is ComponentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}
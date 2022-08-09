package com.rahulkumar.stackdevs.data.remote.interceptors

import android.content.Context
import com.rahulkumar.stackdevs.data.remote.exceptions.ConnectivityException
import com.rahulkumar.stackdevs.data.remote.isCacheable
import com.rahulkumar.stackdevs.utils.android.canConnectToInternet
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException

/**
 * An [Interceptor] that implicitly check if [Context.canConnectToInternet] before making
 * network call.
 *
 * @property context [Context] instance
 */
class NetworkConnectionInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.canConnectToInternet && !chain.request().isCacheable) {
            throw ConnectivityException(
                isTimeout = false,
                message = "No internet access available currently."
            )
        }
        try {
            return chain.proceed(chain.request().newBuilder().build())
        } catch (e: SocketTimeoutException) {
            throw ConnectivityException(
                isTimeout = true,
                message = e.message
            )
        }
    }
}
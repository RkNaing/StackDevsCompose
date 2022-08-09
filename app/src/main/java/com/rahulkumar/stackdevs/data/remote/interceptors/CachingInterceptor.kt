package com.rahulkumar.stackdevs.data.remote.interceptors

import com.rahulkumar.stackdevs.data.remote.HEADER_CACHE
import com.rahulkumar.stackdevs.data.remote.isCacheable
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CachingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Timber.d("intercept: Headers ${request.headers}")

        val response = chain.proceed(request)

        return if (request.isCacheable) {
            val cacheControl = CacheControl.Builder()
                .maxAge(MAX_AGE_IN_DAYS, TimeUnit.DAYS)
                .maxStale(MAX_STALE_IN_DAYS, TimeUnit.DAYS)
                .build()

            response.newBuilder()
                .header(CACHE_CONTROL, cacheControl.toString())
                .build()
        } else {
            // Avoid caching with the request doesn't have Caching Flag in header
            Timber.d("intercept: Avoid caching based on $HEADER_CACHE")
            response
        }
    }

    companion object {
        private const val MAX_AGE_IN_DAYS = 7
        private const val MAX_STALE_IN_DAYS = 7
        private const val CACHE_CONTROL = "Cache-Control"
    }
}
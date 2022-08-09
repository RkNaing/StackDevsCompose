package com.rahulkumar.stackdevs.di.modules

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rahulkumar.stackdevs.BuildConfig
import com.rahulkumar.stackdevs.data.remote.BASE_URL
import com.rahulkumar.stackdevs.data.remote.CACHE_SIZE
import com.rahulkumar.stackdevs.data.remote.endpoints.DevsEndpoint
import com.rahulkumar.stackdevs.data.remote.interceptors.CachingInterceptor
import com.rahulkumar.stackdevs.data.remote.interceptors.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideDevsEndpoint(retrofit: Retrofit): DevsEndpoint =
        retrofit.create(DevsEndpoint::class.java)

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(context))

        val cacheDir = context.run { externalCacheDir ?: cacheDir }
        val networkCacheDir = File(cacheDir, "RetrofitCacheDir")
        okHttpBuilder.cache(Cache(networkCacheDir, CACHE_SIZE))
        okHttpBuilder.addNetworkInterceptor(CachingInterceptor())

        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor.Logger { message ->
                Timber.tag("okhttp").d(message)
            }
            val loggingInterceptor = HttpLoggingInterceptor(logger)
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpBuilder.addInterceptor(loggingInterceptor)
        }
        val json = Json { ignoreUnknownKeys = true }
        val mediaType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpBuilder.build())
            .addConverterFactory(json.asConverterFactory(mediaType))
            .build()
    }
}
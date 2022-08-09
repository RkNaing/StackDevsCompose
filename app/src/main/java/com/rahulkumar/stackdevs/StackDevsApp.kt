package com.rahulkumar.stackdevs

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class StackDevsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(if (BuildConfig.DEBUG) DevTree() else ReleaseTree())
    }
}

/**
 * Application's Timber Debug Tree
 */
private open class DevTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return String.format(
            "Class:%s: Line: %s, Method: %s",
            super.createStackElementTag(element),
            element.lineNumber,
            element.methodName
        )
    }
}

/**
 * Application's Timber Release Tree
 */
private class ReleaseTree : DevTree() {

    private val loggablePriorities = arrayOf(Log.INFO, Log.WARN, Log.ERROR, Log.ASSERT)

    override fun isLoggable(tag: String?, priority: Int) = priority in loggablePriorities

}
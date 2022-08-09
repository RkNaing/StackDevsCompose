package com.rahulkumar.stackdevs.data.remote.exceptions

import java.io.IOException

/**
 * Represents network connection failure due to absent or poor internet connectivity (Sim Data/WIFI).
 *
 * @property isTimeout true if Connectivity Timeout has occurred due to poor network connectivity
 * or laggy server response.
 *
 * @param message exception message
 * @param cause cause [Throwable] of this exception
 */
class ConnectivityException(
    val isTimeout: Boolean,
    message: String? = null,
    cause: Throwable? = null
) : IOException(message, cause)
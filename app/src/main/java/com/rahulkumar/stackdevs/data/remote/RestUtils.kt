package com.rahulkumar.stackdevs.data.remote

import com.rahulkumar.stackdevs.data.remote.exceptions.ConnectivityException
import com.rahulkumar.stackdevs.data.resource.ErrorEntity
import com.rahulkumar.stackdevs.data.resource.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.Request
import retrofit2.HttpException
import timber.log.Timber

/**
 * Wraps REST api call response to [Resource]
 *
 * @param D Type of data retrieved from REST api call
 * @param call Function that executes the actual REST api call and returns **nullable** [D]
 * @return REST api call states and result wrapped inside [Resource]
 */
suspend inline fun <D> restApiCallAsResource(crossinline call: suspend () -> D?): Resource<D> =
    try {
        val response: D? = call()
        if (response != null) {
            Timber.d("restApiCallAsResource: Success")
            Resource.Success(response)
        } else {
            Timber.d("restApiCallAsResource: Response body is null.")
            Resource.Failure(ErrorEntity.Rest.NullResponse)
        }
    } catch (e: Exception) {
        Timber.e(e, "restApiCallAsResource: FAILURE")
        Resource.Failure(e.asRestErrorEntity())
    }

/**
 * Calls [restApiCallAsResource] inside provided [dispatcher]
 *
 * @param dispatcher [CoroutineDispatcher] in which [restApiCallAsResource] will be executed
 * @see [restApiCallAsResource]
 */
suspend inline fun <D> restApiCallAsResource(
    dispatcher: CoroutineDispatcher,
    crossinline call: suspend () -> D?
): Resource<D> = withContext(dispatcher) {
    restApiCallAsResource(call)
}

/**
 * Wraps REST api call response to [Resource]
 *
 * @param R Type of data retrieved from REST api call
 * @param D Type of required data based on [R]
 * @param restCall Function that executes the actual REST api call and returns **nullable** [D]
 * @param mapper [R] to [D] converter function
 * @return REST api call states and result mapped to [D] wrapped inside [Resource]
 */
suspend inline fun <R, D : Any> restApiCallAsMappedResource(
    crossinline restCall: suspend () -> R?,
    crossinline mapper: suspend (R) -> D
): Resource<D> {
    try {
        val response: R? = restCall()
        if (response != null) {
            Timber.d("restApiCallAsResource: Success")
            Resource.Success(mapper(response))
        } else {
            Timber.d("restApiCallAsResource: Response body is null.")
            Resource.Failure(ErrorEntity.Rest.NullResponse)
        }
    } catch (e: Exception) {
        Timber.e(e, "restApiCallAsResource: FAILURE")
        Resource.Failure(e.asRestErrorEntity())
    }
    return Resource.Failure(ErrorEntity.Rest.NullResponse)
}

/**
 * Calls [restApiCallAsMappedResource] inside provided [dispatcher]
 *
 * @param dispatcher [CoroutineDispatcher] in which [restApiCallAsMappedResource] will be executed
 * @see [restApiCallAsMappedResource]
 */
suspend inline fun <R, D : Any> restApiCallAsMappedResource(
    dispatcher: CoroutineDispatcher,
    crossinline restCall: suspend () -> R?,
    crossinline mapper: suspend (R) -> D
): Resource<D> = withContext(dispatcher) {
    restApiCallAsMappedResource(restCall, mapper)
}

/**
 * Maps [Throwable] and its children to [ErrorEntity.Rest]
 */
fun Throwable.asRestErrorEntity() = when (this) {
    is HttpException -> ErrorEntity.Rest.Http(code())
    is ConnectivityException -> ErrorEntity.Rest.Network(isTimeout)
    is NullPointerException -> ErrorEntity.Rest.NullResponse
    else -> ErrorEntity.Rest.Unknown
}

val Request.isCacheable: Boolean
    get() = runCatching { header(HEADER_CACHE).toBoolean() }.getOrDefault(false)
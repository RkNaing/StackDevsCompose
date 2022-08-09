package com.rahulkumar.stackdevs.data.resource

/**
 * A general purpose wrapper around data fetch operation from a particular data-source
 * such as REST Api, local database, file etc.
 *
 * @param D the *non-null* type of data to be fetched from the specific data source.
 */
sealed interface Resource<out D> {
    /**
     * Represents that data has been successfully fetched.
     *
     * @param D the *non-null* type of data to be fetched from the specific data source.
     * @property data the data fetched from the specific data source.
     */
    data class Success<D : Any>(val data: D) : Resource<D>

    /**
     * Represents failure in data fetch operation.
     *
     * @property errorEntity [ErrorEntity]
     */
    data class Failure(val errorEntity: ErrorEntity) : Resource<Nothing>
}

inline fun <D> Resource<D>.onSuccess(action: (data: D) -> Unit): Resource<D> {
    if (this is Resource.Success) {
        action(data)
    }
    return this
}
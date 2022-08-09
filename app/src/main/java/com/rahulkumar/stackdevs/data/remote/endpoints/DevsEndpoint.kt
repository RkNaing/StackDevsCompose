package com.rahulkumar.stackdevs.data.remote.endpoints

import com.rahulkumar.stackdevs.data.models.Dev
import com.rahulkumar.stackdevs.data.remote.DEFAULT_PAGE_SIZE
import com.rahulkumar.stackdevs.data.remote.HEADER_CACHE
import com.rahulkumar.stackdevs.data.remote.PATH_DEVS_LIST_ALL
import com.rahulkumar.stackdevs.data.remote.bodies.ListResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DevsEndpoint {

    @Headers("$HEADER_CACHE: ${true}")
    @GET(PATH_DEVS_LIST_ALL)
    suspend fun getDevsList(
        @Query("page") page: Int,
        @Query("") pageSize: Int = DEFAULT_PAGE_SIZE
    ): ListResponse<Dev>?
}
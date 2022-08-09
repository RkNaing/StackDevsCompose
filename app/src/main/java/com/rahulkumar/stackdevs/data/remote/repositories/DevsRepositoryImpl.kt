package com.rahulkumar.stackdevs.data.remote.repositories

import com.rahulkumar.stackdevs.data.models.Dev
import com.rahulkumar.stackdevs.data.remote.bodies.ListResponse
import com.rahulkumar.stackdevs.data.remote.endpoints.DevsEndpoint
import com.rahulkumar.stackdevs.data.remote.restApiCallAsResource
import com.rahulkumar.stackdevs.data.resource.Resource
import com.rahulkumar.stackdevs.di.DispatcherIO
import com.rahulkumar.stackdevs.domain.repository.DevsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class DevsRepositoryImpl @Inject constructor(
    private val devsEndpoint: DevsEndpoint,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) : DevsRepository {

    override suspend fun getDevsList(page: Int,pageSize: Int): Resource<ListResponse<Dev>> {
        Timber.d("getDevsList() called with: page = [$page], pageSize = [$pageSize]")
        return withContext(dispatcher) {
            restApiCallAsResource { devsEndpoint.getDevsList(page,pageSize) }
        }
    }
}
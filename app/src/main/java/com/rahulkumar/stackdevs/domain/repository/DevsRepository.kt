package com.rahulkumar.stackdevs.domain.repository

import com.rahulkumar.stackdevs.data.models.Dev
import com.rahulkumar.stackdevs.data.remote.bodies.ListResponse
import com.rahulkumar.stackdevs.data.resource.Resource

interface DevsRepository {

    suspend fun getDevsList(page: Int,pageSize: Int): Resource<ListResponse<Dev>>
}
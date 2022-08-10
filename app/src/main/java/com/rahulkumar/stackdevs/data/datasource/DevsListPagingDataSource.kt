package com.rahulkumar.stackdevs.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rahulkumar.stackdevs.data.models.Dev
import com.rahulkumar.stackdevs.data.resource.Resource
import com.rahulkumar.stackdevs.domain.repository.DevsRepository
import timber.log.Timber

class DevsListPagingDataSource(
    private val devsRepository: DevsRepository
) : PagingSource<Int, Dev>() {

    override fun getRefreshKey(state: PagingState<Int, Dev>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dev> {
        val currentPage = params.key?.takeIf { it > 0 } ?: 1
        Timber.d("load: Loading Page : $currentPage")
        return when (val resource = devsRepository.getDevsList(currentPage, params.loadSize)) {
            is Resource.Success -> {
                with(resource.data) {
                    if (items.isNotEmpty()) {
                        LoadResult.Page(
                            data = items,
                            prevKey = if (currentPage > 1) currentPage - 1 else null,
                            nextKey = if (hasMore) currentPage + 1 else null
                        )
                    } else {
                        LoadResult.Page(
                            data = emptyList(),
                            prevKey = null,
                            nextKey = null
                        )
                    }
                }
            }
            is Resource.Failure -> LoadResult.Error(resource.errorEntity)
        }
    }
}
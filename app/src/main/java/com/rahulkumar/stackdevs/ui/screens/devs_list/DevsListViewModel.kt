package com.rahulkumar.stackdevs.ui.screens.devs_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rahulkumar.stackdevs.data.datasource.DevsListPagingDataSource
import com.rahulkumar.stackdevs.data.remote.DEFAULT_PAGE_SIZE
import com.rahulkumar.stackdevs.domain.repository.DevsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DevsListViewModel @Inject constructor(private val devsRepo: DevsRepository) : ViewModel() {

    val devs = Pager(
        config = PagingConfig(
            pageSize = DEFAULT_PAGE_SIZE,
            initialLoadSize = DEFAULT_PAGE_SIZE * 2,
            enablePlaceholders = false,
            maxSize = 70
        ),
        pagingSourceFactory = { DevsListPagingDataSource(devsRepo) }
    ).flow.cachedIn(viewModelScope)

}
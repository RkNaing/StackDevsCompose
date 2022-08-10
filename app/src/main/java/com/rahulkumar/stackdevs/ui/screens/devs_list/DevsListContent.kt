package com.rahulkumar.stackdevs.ui.screens.devs_list

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rahulkumar.stackdevs.R
import com.rahulkumar.stackdevs.data.models.Dev
import com.rahulkumar.stackdevs.data.resource.ErrorEntity
import com.rahulkumar.stackdevs.ui.components.ErrorView
import com.rahulkumar.stackdevs.ui.components.SimpleLoadingView
import com.rahulkumar.stackdevs.utils.android.getActivity
import timber.log.Timber

@Composable
fun DevsListContent(modifier: Modifier = Modifier, devs: LazyPagingItems<Dev>) {

    val context = LocalContext.current
    val isRefreshing = rememberSwipeRefreshState(
        isRefreshing = devs.loadState.refresh is LoadState.Loading
    )

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = isRefreshing,
        onRefresh = devs::refresh
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // Refresh State
            val showContent = when (val refreshState = devs.loadState.refresh) {
                LoadState.Loading -> {
                    Timber.d("DevsListContent: Refresh Loading")
                    item(contentType = ITEM_LOAD_STATE) {
                        SimpleLoadingView(
                            modifier = Modifier.fillParentMaxSize(),
                            orientation = Orientation.Vertical
                        )
                    }
                    false
                }
                is LoadState.Error -> {
                    Timber.d("DevsListContent: Refresh Error : $refreshState")
                    val errorEntity =
                        refreshState.error as? ErrorEntity.Rest ?: ErrorEntity.Rest.Unknown
                    item(contentType = ITEM_LOAD_STATE) {
                        RestErrorEntityView(
                            modifier = if (devs.itemCount == 0) Modifier.fillParentMaxSize() else Modifier.fillMaxWidth(),
                            errorEntity = errorEntity,
                            onRetry = { devs.retry() }
                        )
                    }
                    false
                }
                is LoadState.NotLoading -> true
            }

            if (!showContent) {
                Timber.d("DevsListContent: Skip displaying list contents.")
                return@LazyColumn
            }

            // Handling Prepend State
            when (val prependState = devs.loadState.prepend) {
                is LoadState.Loading -> {
                    Timber.d("DevsListContent: Prepend Loading")
                    item(contentType = ITEM_LOAD_STATE) {
                        SimpleLoadingView(
                            Modifier.fillMaxWidth(),
                            orientation = Orientation.Horizontal,
                            messageRes = R.string.msg_loading_more_devs
                        )
                    }
                }
                is LoadState.Error -> {
                    val prependError = prependState.error
                    val errorEntity = prependError as? ErrorEntity.Rest ?: ErrorEntity.Rest.Unknown
                    Timber.d("DevsListContent: Prepend Error : $prependError")
                    item(contentType = ITEM_LOAD_STATE) {
                        RestErrorEntityView(
                            modifier = Modifier.fillMaxWidth(),
                            errorEntity = errorEntity,
                            onRetry = { devs.retry() }
                        )
                    }
                }
                else -> Timber.d("DevsListContent: Prepend is Idle. Not Loading!")
            }

            items(items = devs, key = { dev -> dev.accountId }) { dev ->
                dev?.let {
                    ItemDeveloper(
                        dev = dev,
                        onClickProfileBtn = { profileLink ->
                            profileLink?.toUri().let { profileUri ->
                                context.getActivity()
                                    ?.startActivity(Intent(Intent.ACTION_VIEW, profileUri))
                            }
                        },
                        onClickPortfolioBtn = { portfolioLink ->
                            portfolioLink?.toUri().let { portfolioUri ->
                                context.getActivity()
                                    ?.startActivity(Intent(Intent.ACTION_VIEW, portfolioUri))
                            }
                        })
                }
            }

            // Handling Append State
            when (val appendState = devs.loadState.append) {
                LoadState.Loading -> {
                    Timber.d("DevsListContent: Append Loading")
                    item(contentType = ITEM_LOAD_STATE) {
                        SimpleLoadingView(
                            Modifier.fillMaxWidth(),
                            orientation = Orientation.Horizontal,
                            messageRes = R.string.msg_loading_more_devs
                        )
                    }
                }
                is LoadState.Error -> {
                    Timber.d("DevsListContent: Append Error $appendState")
                    val errorEntity =
                        appendState.error as? ErrorEntity.Rest ?: ErrorEntity.Rest.Unknown
                    item(contentType = ITEM_LOAD_STATE) {
                        RestErrorEntityView(
                            modifier = Modifier.fillMaxWidth(),
                            errorEntity = errorEntity,
                            onRetry = { devs.retry() }
                        )
                    }

                }
                is LoadState.NotLoading -> Timber.d("DevsListContent: Append state is Idle. Not Loading.")
            }

        }
    }


}

@Composable
private fun RestErrorEntityView(
    modifier: Modifier,
    errorEntity: ErrorEntity.Rest,
    onRetry: () -> Unit
) {
    @StringRes var errorTitle = 0
    @StringRes var errorMessage = R.string.msg_unknown_connection_error
    @DrawableRes var errorIcon = R.drawable.img_err_api_call

    when (errorEntity) {
        is ErrorEntity.Rest.Http -> {
            if (errorEntity.isServerError) {
                errorMessage = R.string.msg_server_error
                errorIcon = R.drawable.img_server_error
            }
        }
        is ErrorEntity.Rest.Network -> {
            errorTitle = R.string.lbl_no_internet
            errorMessage = R.string.msg_no_internet
            errorIcon = R.drawable.img_no_internet
        }
        else -> Unit
    }

    ErrorView(
        modifier = modifier,
        errorTitleRes = errorTitle,
        errorMessageRes = errorMessage,
        errorIcon = errorIcon,
        onRetry = onRetry
    )
}

private const val ITEM_LOAD_STATE = "LoadStateItem"

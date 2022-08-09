package com.rahulkumar.stackdevs.ui.screens.devs_list

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rahulkumar.stackdevs.R
import com.rahulkumar.stackdevs.data.models.Dev
import com.rahulkumar.stackdevs.ui.theme.StackDevsTheme

@Composable
fun DevsListScreen(viewModel: DevsListViewModel = hiltViewModel()) {
    StackDevsTheme {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(stringResource(id = R.string.app_name))
            })
        }) { padding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                color = MaterialTheme.colors.background
            ) {

                val devs: LazyPagingItems<Dev> = viewModel.devs.collectAsLazyPagingItems()
                DevsListContent(devs = devs, modifier = Modifier.fillMaxSize())

            }
        }
    }
}


@Preview("Dark Mode", uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Preview("Light Mode", uiMode = UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
private fun DevListScreenPreview() {
    DevsListScreen()
}
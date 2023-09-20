package com.pouyaheydari.github.userfinder.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pouyaheydari.github.userfinder.R
import com.pouyaheydari.github.userfinder.features.details.ui.components.BottomSheetComponent
import com.pouyaheydari.github.userfinder.features.search.ui.SearchUserScreen
import com.pouyaheydari.github.userfinder.ui.models.UiIntents
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GithubUserFinderScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchUserScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val isAtBottom by remember {
        derivedStateOf {
            // We can improve the user experience by fetching items when there are some more items
            // to the bottom. Skipping it for this sample project.
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }
    val snackBarHostState = remember { SnackbarHostState() }
    val users = state.users

    LaunchedEffect(isAtBottom) {
        if (isAtBottom) sendUserIntent(viewModel = viewModel, UiIntents.OnNextPageRequested)
    }

    @Composable
    fun showSnackBar(message: String = "") {
        LaunchedEffect(key1 = state.isError) {
            if (state.isError) {
                scope.launch {
                    val result = snackBarHostState.showSnackbar(message)
                    result.apply {
                        if (this == SnackbarResult.Dismissed) {
                            sendUserIntent(
                                viewModel = viewModel,
                                userIntent = UiIntents.OnErrorDismissed
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun showBottomSheet() {
        if (state.shouldShowBottomSheet) {
            ModalBottomSheet(onDismissRequest = {
                sendUserIntent(viewModel = viewModel, UiIntents.OnBottomSheetDismissed)
            }) {
                BottomSheetComponent(
                    modifier = Modifier.fillMaxWidth(),
                    selectedUser = state.selectedUser
                )
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { contentPadding ->
        showSnackBar(stringResource(R.string.please_try_again))
        showBottomSheet()
        SearchUserScreen(
            modifier = modifier.padding(contentPadding),
            isLoading = state.isLoading,
            userName = state.phrase,
            usersList = users,
            listState = listState,
            onSearchValueChanged = {
                sendUserIntent(viewModel, UiIntents.OnPhraseChanged(it))
            },
            onUserItemClicked = {
                sendUserIntent(viewModel, UiIntents.OnUserItemSelected(it))
            }
        )
    }

}
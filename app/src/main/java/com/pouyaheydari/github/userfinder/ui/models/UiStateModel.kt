package com.pouyaheydari.github.userfinder.ui.models

import androidx.paging.PagingData
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchUserUiState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = true,
    val isError: Boolean = false,
    val users: Flow<PagingData<UserModel>> = emptyFlow(),
    val phrase: String = "",
    val selectedUser: SelectedUser = SelectedUser.EMPTY,
    val shouldShowBottomSheet: Boolean = false,
) {
    companion object {
        val EMPTY = SearchUserUiState()
    }
}

data class SelectedUser(val repositories: Int = 0, val followers: Int = 0) {
    companion object {
        val EMPTY = SelectedUser()
    }
}

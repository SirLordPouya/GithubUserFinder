package com.pouyaheydari.github.userfinder.ui.models

import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel

data class SearchUserUiState(
    val isLoading: Boolean = false,
    val isEmpty: Boolean = true,
    val isError: Boolean = false,
    val users: List<UserModel> = emptyList(),
    val userName: String = "",
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

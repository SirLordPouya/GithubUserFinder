package com.pouyaheydari.github.userfinder.features.search.data.models

sealed interface SearchUsersDataState {
    data class Success(val users: List<UserModel>) : SearchUsersDataState
    data object Failure : SearchUsersDataState
}
package com.pouyaheydari.github.userfinder.features.details.data.models

sealed interface UserDetailsDataState {
    data class Success(val details: UserDetailsModel) : UserDetailsDataState
    data object Failure : UserDetailsDataState
}
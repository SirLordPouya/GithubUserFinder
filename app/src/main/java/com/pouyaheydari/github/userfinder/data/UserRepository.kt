package com.pouyaheydari.github.userfinder.data

import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsDataState
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersDataState

interface UserRepository {
    suspend fun searchUsers(phrase: String, page: Int, itemsPerPage: Int): SearchUsersDataState
    suspend fun getUser(userName: String): UserDetailsDataState
}

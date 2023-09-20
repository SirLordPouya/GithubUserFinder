package com.pouyaheydari.github.userfinder.data

import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsDataState
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel

interface UserRepository {
    suspend fun searchUsers(phrase: String, page: Int): List<UserModel>
    suspend fun getUser(userName: String): UserDetailsDataState
}

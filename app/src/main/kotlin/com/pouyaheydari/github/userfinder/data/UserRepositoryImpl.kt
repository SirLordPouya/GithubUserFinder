package com.pouyaheydari.github.userfinder.data

import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsDataState
import com.pouyaheydari.github.userfinder.features.search.data.UsersApiService
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersDataState
import javax.inject.Inject
import com.pouyaheydari.github.userfinder.features.details.data.mappers.map as userDetailsResponseMapper
import com.pouyaheydari.github.userfinder.features.search.data.mappers.map as searchUserResponseMapper


class UserRepositoryImpl @Inject constructor(private val usersApiService: UsersApiService) :
    UserRepository {
    override suspend fun searchUsers(
        phrase: String,
        page: Int,
        itemsPerPage: Int
    ): SearchUsersDataState =
        usersApiService.searchUsers(phrase, page, itemsPerPage)
            .run(::searchUserResponseMapper)

    override suspend fun getUser(userName: String): UserDetailsDataState =
        usersApiService.getUser(userName).run(::userDetailsResponseMapper)
}

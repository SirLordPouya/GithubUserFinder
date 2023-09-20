package com.pouyaheydari.github.userfinder.data

import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetails
import com.pouyaheydari.github.userfinder.features.search.data.UsersApiService
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import javax.inject.Inject
import com.pouyaheydari.github.userfinder.features.details.data.map as userDetailsResponseMapper
import com.pouyaheydari.github.userfinder.features.search.data.mapper.map as searchUserResponseMapper


class UserRepositoryImpl @Inject constructor(private val usersApiService: UsersApiService) :
    UserRepository {
    override suspend fun searchUsers(phrase: String, page: Int): List<UserModel> =
        usersApiService.searchUsers(phrase, page)
            .run(::searchUserResponseMapper)

    override suspend fun getUser(userName: String): UserDetails =
        usersApiService.getUser(userName)
            .run(::userDetailsResponseMapper)
}

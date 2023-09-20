package com.pouyaheydari.github.userfinder.features.search.data.mapper

import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersApiResponse
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel

fun map(response: SearchUsersApiResponse): List<UserModel> =
    response.items.map {
        UserModel(imageUrl = it.avatarUrl, title = it.login)
    }

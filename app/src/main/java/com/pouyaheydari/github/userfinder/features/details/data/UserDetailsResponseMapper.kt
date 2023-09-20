package com.pouyaheydari.github.userfinder.features.details.data

import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetails
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsApiResponse

fun map(response: UserDetailsApiResponse): UserDetails =
    UserDetails(repositories = response.publicRepos, followers = response.followers)


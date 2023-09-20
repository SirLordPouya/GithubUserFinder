package com.pouyaheydari.github.userfinder.features.search.data

import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsApiResponse
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApiService {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") phrase: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100,
    ): SearchUsersApiResponse

    @GET("users/{USERNAME}")
    suspend fun getUser(
        @Path("USERNAME") userName: String,
    ): UserDetailsApiResponse
}

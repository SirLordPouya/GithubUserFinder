package com.pouyaheydari.github.userfinder.features.search.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pouyaheydari.github.userfinder.data.SearchUsersPagingSource
import com.pouyaheydari.github.userfinder.data.UserRepository
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(phrase: String): Flow<PagingData<UserModel>> =
        Pager(PagingConfig(pageSize = 100)) {
            SearchUsersPagingSource(userRepository, phrase)
        }.flow
}
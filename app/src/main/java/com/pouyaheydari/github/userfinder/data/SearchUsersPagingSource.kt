package com.pouyaheydari.github.userfinder.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel

class SearchUsersPagingSource(
    private val userRepository: UserRepository,
    private val phrase: String,
) :
    PagingSource<Int, UserModel>() {
    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {
        return try {
            val page = params.key ?: 1
            val response = userRepository.searchUsers(phrase = phrase, page = page)

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

package com.pouyaheydari.github.userfinder.features.search.domain

import com.pouyaheydari.github.userfinder.common.base.usecase.UseCase
import com.pouyaheydari.github.userfinder.data.UserRepository
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersDataState
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private const val ITEMS_PER_PAGE = 100

class SearchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<String, SearchUsersDataState>() {

    private var userName = ""
    private var page = AtomicInteger(1)
    private val userList = mutableListOf<UserModel>()
    private val mutex = Mutex()

    //We can use these 3 variables to skip extra network requests.
    // I'll skip it in this sample project
    private var errorReceived = false
    private var reachedLastItem = false
    private var reachedRequestLimit = false
    override suspend fun run(param: String): SearchUsersDataState {
        prepareDataToFetch(param)
        return when (
            val result = userRepository.searchUsers(userName, page.get(), ITEMS_PER_PAGE)) {
            is SearchUsersDataState.Failure -> {
                page.decrementAndGet()
                SearchUsersDataState.Failure
            }

            is SearchUsersDataState.Success -> {
                userList.addAll(result.users)
                SearchUsersDataState.Success(userList)
            }
        }
    }

    private suspend fun prepareDataToFetch(param: String) {
        mutex.withLock {
            if (param != userName) {
                reset()
                userName = param
            } else {
                page.incrementAndGet()
            }
        }
    }

    private fun reset() {
        userName = ""
        page.set(1)
        userList.clear()
        errorReceived = false
        reachedLastItem = false
        reachedRequestLimit = false
    }
}

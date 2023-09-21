package com.pouyaheydari.github.userfinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsModel
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsDataState
import com.pouyaheydari.github.userfinder.features.details.domain.GetUserUseCase
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersDataState
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import com.pouyaheydari.github.userfinder.features.search.domain.SearchUsersUseCase
import com.pouyaheydari.github.userfinder.ui.models.SearchUserUiState
import com.pouyaheydari.github.userfinder.ui.models.SelectedUser
import com.pouyaheydari.github.userfinder.ui.models.UiIntents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class GithubUserFinderScreenViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val userNameFlow = MutableSharedFlow<String>()
    private val _uiState = MutableStateFlow(SearchUserUiState.EMPTY)
    val uiState = _uiState.asStateFlow()

    init {
        observeUserNameFlow()
    }

    fun onUserIntent(userIntent: UiIntents) =
        when (userIntent) {
            is UiIntents.OnPhraseChanged -> {
                _uiState.update { it.copy(userName = userIntent.userName, isLoading = true) }
                updateUserNameFlow(userIntent.userName)
            }

            is UiIntents.OnUserItemSelected -> {
                _uiState.update { it.copy(isLoading = true) }
                getUser(userIntent.userName)
            }

            UiIntents.OnBottomSheetDismissed -> {
                _uiState.update { it.copy(shouldShowBottomSheet = false) }
            }

            UiIntents.OnErrorDismissed -> {
                _uiState.update { it.copy(isError = false) }
            }

            UiIntents.OnNextPageRequested -> {
                _uiState.update { it.copy(isLoading = true) }
                searchUsers(_uiState.value.userName)
            }
        }

    private fun updateUserNameFlow(userName: String) {
        viewModelScope.launch {
            userNameFlow.emit(userName)
        }
    }

    private fun getUser(userName: String) {
        viewModelScope.launch {
            getUserUseCase(userName).run {
                when (this) {
                    is UserDetailsDataState.Success -> showUserDetails(details)

                    UserDetailsDataState.Failure -> showError()
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeUserNameFlow() {
        viewModelScope.launch {
            userNameFlow
                .debounce(2000)
                .collectLatest { searchUsers(it) }
        }
    }

    private fun searchUsers(userName: String) {
        viewModelScope.launch {
            when (val result = searchUsersUseCase(userName)) {
                is SearchUsersDataState.Failure -> showError()

                is SearchUsersDataState.Success -> showUsersList(result.users)
            }
        }
    }

    private fun showError() {
        _uiState.update {
            it.copy(isError = true, isLoading = false, shouldShowBottomSheet = false)
        }
    }

    private fun showUsersList(users: List<UserModel>) {
        _uiState.update {
            it.copy(users = users, isLoading = false, isError = false)
        }
    }

    private fun showUserDetails(details: UserDetailsModel) {
        val selectedUser = SelectedUser(details.repositories, details.followers)
        _uiState.update {
            it.copy(
                selectedUser = selectedUser,
                shouldShowBottomSheet = true,
                isLoading = false,
                isError = false
            )
        }
    }
}

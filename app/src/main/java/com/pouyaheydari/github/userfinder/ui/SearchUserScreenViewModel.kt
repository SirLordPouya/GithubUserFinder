package com.pouyaheydari.github.userfinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pouyaheydari.github.userfinder.features.details.domain.GetUserUseCase
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
class SearchUserScreenViewModel @Inject constructor(
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
                _uiState.update { it.copy(phrase = userIntent.phrase, isLoading = true) }
                updateUserNameFlow(userIntent.phrase)
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

            UiIntents.OnPagingError -> {
                _uiState.update { it.copy(isError = true, isLoading = false) }
            }

            UiIntents.OnPagingLoading -> {
                _uiState.update { it.copy(isLoading = true) }
            }

            UiIntents.OnPagingLoadingFinished -> {
                _uiState.update { it.copy(isLoading = false) }
            }
        }

    private fun updateUserNameFlow(phrase: String) {
        viewModelScope.launch {
            userNameFlow.emit(phrase)
        }
    }

    private fun getUser(userName: String) {
        viewModelScope.launch {
            runCatching {
                getUserUseCase(userName).apply {
                    _uiState.update {
                        it.copy(
                            selectedUser = SelectedUser(repositories, followers),
                            shouldShowBottomSheet = true,
                            isLoading = false,
                            isError = false
                        )
                    }
                }
            }.onFailure {
                _uiState.update {
                    it.copy(isError = true, isLoading = false, shouldShowBottomSheet = false)
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeUserNameFlow() {
        viewModelScope.launch {
            userNameFlow
                .debounce(2000)
                .collectLatest {
                    searchUsers(it)
                }
        }
    }

    private fun searchUsers(phrase: String) {
        val users = searchUsersUseCase(phrase).cachedIn(viewModelScope)
        _uiState.update { it.copy(users = users, isLoading = false, isError = false) }
    }
}

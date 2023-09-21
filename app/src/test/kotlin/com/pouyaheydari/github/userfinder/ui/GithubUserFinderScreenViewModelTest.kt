import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsDataState
import com.pouyaheydari.github.userfinder.features.details.data.models.UserDetailsModel
import com.pouyaheydari.github.userfinder.features.details.domain.GetUserUseCase
import com.pouyaheydari.github.userfinder.features.search.data.models.SearchUsersDataState
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import com.pouyaheydari.github.userfinder.features.search.domain.SearchUsersUseCase
import com.pouyaheydari.github.userfinder.ui.GithubUserFinderScreenViewModel
import com.pouyaheydari.github.userfinder.ui.models.SelectedUser
import com.pouyaheydari.github.userfinder.ui.models.UiIntents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.internal.verification.Times
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GithubUserFinderScreenViewModelTest {

    private lateinit var viewModel: GithubUserFinderScreenViewModel
    private val searchUsersUseCase: SearchUsersUseCase = mock()
    private val getUserUseCase: GetUserUseCase = mock()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GithubUserFinderScreenViewModel(searchUsersUseCase, getUserUseCase)
    }

    @Test
    fun `when user enters a phrase, searchUsersUseCase is called`() = runTest {
        val phrase = "test"

        viewModel.onUserIntent(UiIntents.OnPhraseChanged(phrase))
        advanceUntilIdle()

        verify(searchUsersUseCase).invoke(phrase)
    }

    @Test
    fun `when user enters a phrase, ui is updated`() = runTest {
        val phrase = "test"

        viewModel.onUserIntent(UiIntents.OnPhraseChanged(phrase))
        advanceUntilIdle()

        assertEquals(phrase, viewModel.uiState.value.phrase)
    }

    @Test
    fun `when searchUsersUseCase returns success, showUsersList is called`() = runTest {
        val users = listOf(UserModel("url", "userName"))
        val phrase = "test"
        whenever(searchUsersUseCase(phrase)).thenReturn(SearchUsersDataState.Success(users))

        viewModel.onUserIntent(UiIntents.OnPhraseChanged(phrase))
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(users, uiState.users)
        assertFalse(uiState.isLoading)
        assertFalse(uiState.isError)
    }

    @Test
    fun `when searchUsersUseCase returns failure, showError is called`() = runTest {
        val phrase = "test"
        whenever(searchUsersUseCase(phrase)).thenReturn(SearchUsersDataState.Failure)

        viewModel.onUserIntent(UiIntents.OnPhraseChanged(phrase))
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertTrue(uiState.isError)
        assertFalse(uiState.isLoading)
    }

    @Test
    fun `when user selects an item, getUserUseCase is called`() = runTest {
        val userName = "username"
        whenever(getUserUseCase(userName))
            .thenReturn(
                UserDetailsDataState.Success(UserDetailsModel(0, 0))
            )

        viewModel.onUserIntent(UiIntents.OnUserItemSelected(userName))
        advanceUntilIdle()

        verify(getUserUseCase).invoke(userName)
    }

    @Test
    fun `when getUserUseCase returns success, ui is updated`() = runTest {
        val userDetails = UserDetailsModel(5, 10)
        val userName = "username"
        whenever(getUserUseCase(userName))
            .thenReturn(
                UserDetailsDataState.Success(userDetails)
            )

        viewModel.onUserIntent(UiIntents.OnUserItemSelected(userName))
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        val selectedUser = SelectedUser(userDetails.repositories, userDetails.followers)
        assertEquals(selectedUser, uiState.selectedUser)
        assertTrue(uiState.shouldShowBottomSheet)
        assertFalse(uiState.isLoading)
        assertFalse(uiState.isError)
    }

    @Test
    fun `when getUserUseCase returns failure, showError is called`() = runTest {
        val userName = "username"
        whenever(getUserUseCase(userName)).thenReturn(UserDetailsDataState.Failure)

        viewModel.onUserIntent(UiIntents.OnUserItemSelected(userName))
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertTrue(uiState.isError)
        assertFalse(uiState.isLoading)
    }


    @Test
    fun `when user dismisses bottom sheet, shouldShowBottomSheet is set to false`() {

        viewModel.onUserIntent(UiIntents.OnBottomSheetDismissed)

        val uiState = viewModel.uiState.value
        assertFalse(uiState.shouldShowBottomSheet)
    }

    @Test
    fun `when user dismisses error, isError is set to false`() {

        viewModel.onUserIntent(UiIntents.OnErrorDismissed)

        val uiState = viewModel.uiState.value
        assertFalse(uiState.isError)
    }

    @Test
    fun `when user requests next page, searchUsers is called`() = runTest {
        val phrase = "test"

        viewModel.onUserIntent(UiIntents.OnPhraseChanged(phrase))
        viewModel.onUserIntent(UiIntents.OnNextPageRequested)
        advanceUntilIdle()

        verify(searchUsersUseCase, Times(2)).invoke(phrase)
    }

    @Test
    fun `when onNextPageRequested is called, isLoading is set to true`() {

        viewModel.onUserIntent(UiIntents.OnNextPageRequested)

        val uiState = viewModel.uiState.value
        assertTrue(uiState.isLoading)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}

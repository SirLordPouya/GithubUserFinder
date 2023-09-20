package com.pouyaheydari.github.userfinder.features.search.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import com.pouyaheydari.github.userfinder.features.search.ui.components.SearchHeaderComponent
import com.pouyaheydari.github.userfinder.features.search.ui.components.UserListComponent
import com.pouyaheydari.github.userfinder.ui.SearchUserScreenViewModel

@Composable
fun SearchUserScreen(
    modifier: Modifier,
    isLoading: Boolean,
    userName: String,
    usersList: LazyPagingItems<UserModel>,
    onSearchValueChanged: (String) -> Unit,
    onUserItemClicked: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchHeaderComponent(
            modifier = Modifier.height(150.dp),
            searchPhrase = userName,
            isLoading = isLoading,
            onSearchValueChange = { onSearchValueChanged(it) },
        )
        Spacer(modifier = Modifier.size(8.dp))
        UserListComponent(
            modifier = modifier,
            users = usersList,
            onUserClicked = { onUserItemClicked(it) },
        )
    }
}
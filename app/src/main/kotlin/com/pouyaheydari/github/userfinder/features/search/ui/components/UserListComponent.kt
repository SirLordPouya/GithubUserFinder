package com.pouyaheydari.github.userfinder.features.search.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import com.pouyaheydari.github.userfinder.common.ui.theme.GithubUserFinderTheme

@Composable
fun UserListComponent(
    modifier: Modifier = Modifier,
    users: List<UserModel> = emptyList(),
    listState: LazyListState = rememberLazyListState(),
    onUserClicked: (String) -> Unit = {},
) {
    Column(modifier) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(users) {
                UserItemComponent(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    imageUrl = it.imageUrl,
                    title = it.title,
                    onUserClicked = onUserClicked,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    GithubUserFinderTheme {
        UserListComponent(
            users = listOf(
                UserModel("https://url.com", "Pouya Heydari"),
                UserModel("https://url.com", "Pouya Heydari"),
                UserModel("https://url.com", "Pouya Heydari"),
            ),
        )
    }
}

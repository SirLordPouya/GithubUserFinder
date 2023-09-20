package com.pouyaheydari.github.userfinder.features.search.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.pouyaheydari.github.userfinder.features.search.data.models.UserModel
import com.pouyaheydari.github.userfinder.ui.theme.GithubUserFinderTheme

@Composable
fun UserListComponent(
    modifier: Modifier = Modifier,
    users: LazyPagingItems<UserModel>,
    onUserClicked: (String) -> Unit = {},
) {
    Column(modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(count = users.itemCount) { index ->
                users[index]?.let {
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
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    GithubUserFinderTheme {
//        UserListComponent(
//            users =
//            PagingData.from(
//                listOf(
//                    UserModel("https://url.com", "Pouya Heydari"),
//                    UserModel("https://url.com", "Pouya Heydari"),
//                    UserModel("https://url.com", "Pouya Heydari"),
//                )
//            ),
//        )
    }
}

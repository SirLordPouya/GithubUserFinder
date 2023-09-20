package com.pouyaheydari.github.userfinder.features.search.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pouyaheydari.github.userfinder.R
import com.pouyaheydari.github.userfinder.common.ui.theme.GithubUserFinderTheme
import com.pouyaheydari.github.userfinder.common.ui.theme.HeaderShape

@Composable
fun SearchHeaderComponent(
    modifier: Modifier = Modifier,
    searchPhrase: String = "",
    onSearchValueChange: (String) -> Unit = {},
    onSearchIconClicked: () -> Unit = {},
    isLoading: Boolean = false
) {
    Box(modifier = modifier.fillMaxWidth()) {
        HeaderComponent(modifier)
        SearchComponent(
            this,
            searchPhrase,
            onSearchValueChange,
            onSearchIconClicked,
            isLoading
        )
    }
}

@Composable
private fun HeaderComponent(modifier: Modifier) {
    Box(
        modifier =
        modifier
            .fillMaxWidth()
            .offset(y = (-30).dp)
            .clip(HeaderShape)
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.github_user_finder),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SearchComponent(
    boxScope: BoxScope,
    searchPhrase: String = "",
    onSearchValueChange: (String) -> Unit,
    onSearchIconClicked: () -> Unit,
    isLoading: Boolean = false
) = with(boxScope) {
    Card(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .align(Alignment.BottomCenter),
    ) {
        SearchFieldComponent(
            searchPhrase = searchPhrase,
            onSearchValueChange = onSearchValueChange,
            isLoading = isLoading,
            onSearchIconClicked = onSearchIconClicked
        )
    }
}

@Composable
private fun SearchFieldComponent(
    modifier: Modifier = Modifier,
    searchPhrase: String = "",
    onSearchValueChange: (String) -> Unit = {},
    isLoading: Boolean = false,
    onSearchIconClicked: () -> Unit = {}
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = searchPhrase,
        placeholder = {
            Text(text = stringResource(R.string.please_enter_a_username))
        },
        onValueChange = { onSearchValueChange(it) },
        leadingIcon = {
            LoadingComponent(isLoading)
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable { onSearchIconClicked() },
                imageVector = Icons.Rounded.Search,
                contentDescription = stringResource(R.string.search),
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
    )
}

@Composable
private fun LoadingComponent(isLoading: Boolean) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
private fun PreviewLight() {
    GithubUserFinderTheme {
        SearchHeaderComponent(modifier = Modifier.height(150.dp))
    }
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun PreviewDark() {
    GithubUserFinderTheme {
        SearchHeaderComponent(modifier = Modifier.height(150.dp))
    }
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun SearchFieldPreview() {
    GithubUserFinderTheme {
        SearchFieldComponent(isLoading = true)
    }
}

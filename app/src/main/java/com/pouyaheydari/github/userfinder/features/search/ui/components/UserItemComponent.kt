package com.pouyaheydari.github.userfinder.features.search.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pouyaheydari.github.userfinder.R
import com.pouyaheydari.github.userfinder.common.ui.theme.GithubUserFinderTheme

@Composable
fun UserItemComponent(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    title: String = "",
    onUserClicked: (String) -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onUserClicked(title) },
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .width(80.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(50.dp)),
                placeholder = painterResource(id = R.drawable.ic_user),
                model = imageUrl,
                contentDescription = null,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    GithubUserFinderTheme {
        UserItemComponent(
            imageUrl = "https://github.com/coil-kt/coil/raw/main/logo.svg",
            title = "User Name",
        )
    }
}

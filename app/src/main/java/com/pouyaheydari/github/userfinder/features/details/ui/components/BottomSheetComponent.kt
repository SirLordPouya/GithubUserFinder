package com.pouyaheydari.github.userfinder.features.details.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pouyaheydari.github.userfinder.R
import com.pouyaheydari.github.userfinder.ui.models.SelectedUser

@Composable
fun BottomSheetComponent(
    modifier: Modifier = Modifier,
    selectedUser: SelectedUser,
) {
    Column(modifier) {
        UserDetailsItem(
            text = stringResource(id = R.string.followers, selectedUser.followers),
            icon = Icons.Rounded.AccountBox,
        )
        UserDetailsItem(
            text = stringResource(id = R.string.repositories, selectedUser.repositories),
            icon = Icons.Rounded.Menu,
        )
    }
}

@Composable
private fun UserDetailsItem(
    modifier: Modifier = Modifier,
    text: String = "",
    icon: ImageVector = Icons.Rounded.Menu,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier =
                Modifier
                    .size(54.dp)
                    .padding(end = 16.dp),
            imageVector = icon,
            contentDescription = null,
        )
        Text(
            maxLines = 1,
            text = text,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    BottomSheetComponent(selectedUser = SelectedUser(123, 456))
}

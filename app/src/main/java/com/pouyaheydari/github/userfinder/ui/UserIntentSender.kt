package com.pouyaheydari.github.userfinder.ui

import com.pouyaheydari.github.userfinder.ui.models.UiIntents

fun sendUserIntent(viewModel: SearchUserScreenViewModel, userIntent: UiIntents) {
    viewModel.onUserIntent(userIntent)
}

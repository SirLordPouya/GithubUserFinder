package com.pouyaheydari.github.userfinder.ui.models

sealed interface UiIntents {
    data class OnPhraseChanged(val phrase: String) : UiIntents
    data class OnUserItemSelected(val userName: String) : UiIntents
    data object OnBottomSheetDismissed : UiIntents
    data object OnErrorDismissed : UiIntents
    data object OnNextPageRequested : UiIntents
}

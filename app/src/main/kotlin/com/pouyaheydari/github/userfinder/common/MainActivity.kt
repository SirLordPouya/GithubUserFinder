package com.pouyaheydari.github.userfinder.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.pouyaheydari.github.userfinder.ui.GithubUserFinderScreen
import com.pouyaheydari.github.userfinder.common.ui.theme.GithubUserFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubUserFinderTheme {
                GithubUserFinderScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

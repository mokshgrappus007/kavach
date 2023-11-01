package com.grappus.kavach.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.grappus.kavach.navigation.NavGraph
import com.grappus.kavach.ui.theme.KavachTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KavachTheme.dark {
                NavGraph()
            }
        }
    }
}

package com.grappus.kavach.presentation

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.grappus.kavach.navigation.NavGraph
import com.grappus.kavach.ui.theme.KavachTheme
import com.grappus.kavach.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetDeviceSize()
            KavachTheme(KavachTheme.lightColorScheme, false) {
                NavGraph(sharedPreferences)
            }
        }
    }
}

@Composable
fun GetDeviceSize() {
    val configuration = LocalConfiguration.current
    Constants.screenHeight = configuration.screenHeightDp
    Constants.screenWidth = configuration.screenWidthDp
}

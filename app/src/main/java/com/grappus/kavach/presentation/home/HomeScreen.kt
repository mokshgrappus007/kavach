package com.grappus.kavach.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.grappus.kavach.ui.theme.KavachTheme

@Composable
fun HomeScreen(navController: NavController) {
    KavachTheme(KavachTheme.darkColorScheme, true) {
        HomeScreenBody()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenBody() {
    Surface(modifier = Modifier.fillMaxSize()) {
    }
}
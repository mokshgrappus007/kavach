package com.grappus.kavach.presentation.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.grappus.kavach.R
import com.grappus.kavach.ui.theme.KavachTheme

@Composable
fun DashboardScreen(navController: NavController) {
    KavachTheme(KavachTheme.darkColorScheme, true) {
        HomeScreenBody()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenBody() {
    Surface(Modifier.fillMaxSize()) {
        Column {
            Spacer(Modifier.height(16.dp))
            Row(Modifier.padding(horizontal = 16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.app_icon),
                    contentDescription = "app logo"
                )
            }
        }
    }
}
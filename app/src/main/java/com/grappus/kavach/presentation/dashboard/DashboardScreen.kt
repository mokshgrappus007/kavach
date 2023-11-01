package com.grappus.kavach.presentation.dashboard

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.grappus.kavach.R
import com.grappus.kavach.navigation.Screen
import com.grappus.kavach.presentation.common.KavachIconButton
import com.grappus.kavach.ui.theme.KavachColor
import com.grappus.kavach.ui.theme.KavachTheme

@Composable
fun DashboardScreen(navController: NavController) {
    KavachTheme.dark {
        Surface(Modifier.fillMaxSize()) {
            var selectedIndex = rememberSaveable { mutableIntStateOf(0) }
            Column {
                Spacer(Modifier.height(16.dp))
                TopBar(onTapped = { navController.navigate(Screen.Support.route) })
                Spacer(Modifier.height(20.dp))
                TabBar(selectedIndex, onTabChanged = {
                    selectedIndex.value = it
                })
                TabBody(selectedIndex)
            }
        }
    }
}

@Composable
private fun TabBody(selectedIndex: State<Int>) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "Coming Soon", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun TopBar(onTapped: () -> Unit) {
    Row(
        Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_icon), contentDescription = "app logo"
        )
        Spacer(Modifier.weight(1F))
        KavachIconButton(onClicked = onTapped) {
            Image(
                painterResource(id = R.drawable.dashboard_support),
                contentDescription = "support button",
            )
        }
        Spacer(Modifier.width(12.dp))
        KavachIconButton(onClicked = onTapped) {
            Image(
                painterResource(id = R.drawable.dashboard_notification),
                contentDescription = "notification button",
            )
        }
        Spacer(Modifier.width(12.dp))
        KavachIconButton(onClicked = onTapped) {
            Image(
                painterResource(id = R.drawable.dashboard_settings),
                contentDescription = "settings button",
            )
        }

    }
}


@Composable
fun TabBar(selectedIndex: State<Int>, onTabChanged: (index: Int) -> Unit) {
    val context = LocalContext.current
    val list =
        remember {
            listOf(
                context.resources.getString(R.string.dashboard_for_you_tab),
                context.resources.getString(R.string.dashboard_read_tab),
                context.resources.getString(R.string.dashboard_watch_tab),
                context.resources.getString(R.string.dashboard_listen_tab),
            )
        }

    ScrollableTabRow(
        selectedTabIndex = selectedIndex.value,
        indicator = {
            Box {}
        },
        divider = {
            Box {}
        },
        edgePadding = 12.dp
    ) {
        list.forEachIndexed { index, text ->
            val isSelected = selectedIndex.value == index

            val transition = updateTransition(isSelected, label = "transition")

            val background by transition.animateColor(label = "color") {
                if (it) KavachColor.BrilliantLavender else KavachColor.Transparent
            }

            val rotation by transition.animateFloat(label = "rotation") {
                if (it) -3F else 0F
            }

            Tab(modifier = Modifier
                .padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(50))
                .border(
                    width = 1.dp,
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 40.dp)
                )
                .drawBehind {
                    drawRect(color = background)
                }
                .graphicsLayer {
                    rotationZ = rotation
                },
                selected = isSelected,
                onClick = { onTabChanged(index) },
                text = {
                    Text(
                        text = text,
                        color = if (isSelected) KavachColor.Black else KavachColor.White,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                })
        }
    }
}

@Composable
fun DummyNavigation(navController: NavController) {
    KavachTheme.dark {
        Surface(Modifier.fillMaxSize()) {
            Column {
                Spacer(Modifier.height(16.dp))
                KavachIconButton(
                    modifier = Modifier.padding(start = 16.dp),
                    image = {
                        Image(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = "back",
                            colorFilter = ColorFilter.tint(KavachColor.White)
                        )
                    }, onClicked = { navController.popBackStack() }
                )
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = "Coming Soon", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
package com.grappus.kavach.presentation.dashboard

import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.grappus.kavach.R
import com.grappus.kavach.domain.model.response_model.ContentListData
import com.grappus.kavach.navigation.Screen
import com.grappus.kavach.presentation.common.KavachIconButton
import com.grappus.kavach.ui.theme.InterFont
import com.grappus.kavach.ui.theme.KavachColor
import com.grappus.kavach.ui.theme.KavachTheme
import com.grappus.kavach.ui.theme.LuckiestGuyFont
import com.squareup.moshi.Moshi

@Composable
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel = hiltViewModel()) {
    KavachTheme.dark {
        Surface(Modifier.fillMaxSize()) {
            val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
            Column {
                Spacer(Modifier.height(16.dp))
                TopBar(onTapped = { navController.navigate(Screen.DashboardNestedScreen.route) })
                Spacer(Modifier.height(20.dp))
                TabBar(selectedIndex, onTabChanged = {
                    selectedIndex.intValue = it
                })
                TabBody(selectedIndex, viewModel, navController = navController)
            }
        }
    }
}

@Composable
private fun TabBody(selectedIndex: State<Int>, viewModel: DashboardViewModel, navController: NavController) {
    var uiState = viewModel.dashboardForYouUiState
    when (selectedIndex.value) {
        0 -> uiState = viewModel.dashboardForYouUiState
        1 -> uiState = viewModel.dashboardReadUiState
        2 -> uiState = viewModel.dashboardWatchUiState
        3 -> uiState = viewModel.dashboardListenUiState
    }
    val topGradient = remember {
        Brush.verticalGradient(
            colors = listOf(KavachColor.RaisinBlack, Color.Transparent),
        )
    }
    val bottomGradient = remember {
        Brush.verticalGradient(
            colors = listOf(Color.Transparent, KavachColor.RaisinBlack),
        )
    }
    Box {
        when {
            uiState.data?.data?.content?.isNotEmpty() == true -> {
                CardItemList(uiState.data!!.data.content , navController = navController)
            }
        }
        Box(
            Modifier
                .height(20.dp)
                .fillMaxWidth()
                .background(topGradient)
                .align(Alignment.TopStart)
        )
        Box(
            Modifier
                .height(12.dp)
                .fillMaxWidth()
                .background(bottomGradient)
                .align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun CardItemList(contentList: List<ContentListData>, navController: NavController) {
    LazyColumn(
        contentPadding = PaddingValues(top = 20.dp, start = 16.dp, end = 16.dp)
    ) {
        itemsIndexed(contentList) { index, content ->
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(ContentListData::class.java).lenient()
            val contentJson = ContentListData(
                id = content.id,
                category = content.category,
                contentKey = content.contentKey,
                contentType = content.contentType,
                createdAt = content.createdAt,
                description = content.description,
                isPortrait = content.isPortrait,
                readTime = content.readTime,
                thumbnail =  Uri.encode(content.thumbnail),
                title = content.title,
                updatedAt = content.updatedAt
            )
            val contentStr = jsonAdapter.toJson(contentJson)
            CardItem(
                heading = content.description,
                contentType = content.category,
                imageUrl = content.thumbnail,
                modifier = Modifier.clickable {
                    navController.navigate(route =  Screen.DetailScreen.route.replace("{content}", contentStr))
                }
            )
        }
    }
}

@Composable
private fun CardItem(
    heading: String,
    contentType: String,
    imageUrl: String? = null,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(bottom = 15.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 12.dp)), elevation = cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Box {
            Column {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(156.dp)
//                        .background(Color.White.copy(alpha = .2F))
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = ColorPainter(Color.White),
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                        onError = {
                            Log.v("image error", it.toString())
                        }
                    )
                }
                Text(
                    modifier = Modifier.padding(18.dp, 26.dp, 23.dp),
                    text = heading,
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontFamily = InterFont,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                    )
                )
                Spacer(Modifier.height(17.dp))
                Text(
                    modifier = Modifier.padding(start = 18.dp, bottom = 17.dp),
                    text = "28 Jul ‘23 • 5 min read",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        fontFamily = InterFont,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                    )
                )
            }
            Box(Modifier.offset(y = 134.dp, x = 18.dp)) {
                Box(
                    Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFF0B0A07),
                            shape = RoundedCornerShape(size = 40.dp)
                        )
                        .background(
                            color = Color(0xFFDE4FF3), shape = RoundedCornerShape(size = 40.dp)
                        )
                        .padding(
                            start = 16.dp, top = 10.dp, end = 16.dp, bottom = 10.dp
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = contentType, style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontFamily = LuckiestGuyFont,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                        )
                    )
                }
            }
        }
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
    val list = remember {
        listOf(
            context.resources.getString(R.string.dashboard_for_you_tab),
            context.resources.getString(R.string.dashboard_read_tab),
            context.resources.getString(R.string.dashboard_watch_tab),
            context.resources.getString(R.string.dashboard_listen_tab),
        )
    }

    ScrollableTabRow(selectedTabIndex = selectedIndex.value, indicator = {
        Box {}
    }, divider = {
        Box {}
    }, edgePadding = 12.dp
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

            Box(
                Modifier
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(0.8f, 0.8f)
                        rotationZ = rotation
                    }
                    .padding(bottom = 4.dp)
                    .padding(horizontal = 4.dp)) {
                Text(text = text,
                    color = if (isSelected) KavachColor.Black else KavachColor.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(size = 40.dp)
                        )
                        .drawBehind {
                            drawRect(color = background)
                        }
                        .clickable { onTabChanged(index) }
                        .padding(
                            start = 20.dp, top = 10.dp, end = 20.dp, bottom = 10.dp
                        ))
            }
        }
    }
}

@Composable
fun DashboardNestedScreen(navController: NavController) {
    KavachTheme.dark {
        Surface(Modifier.fillMaxSize()) {
            Column {
                Spacer(Modifier.height(16.dp))
                KavachIconButton(modifier = Modifier.padding(start = 16.dp), image = {
                    Image(
                        imageVector = Icons.Rounded.KeyboardArrowLeft,
                        contentDescription = "back",
                        colorFilter = ColorFilter.tint(KavachColor.White)
                    )
                }, onClicked = { navController.popBackStack() })
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = "Coming Soon", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}
package com.grappus.kavach.presentation.read

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
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
import com.grappus.kavach.presentation.dashboard.DashboardViewModel
import com.grappus.kavach.ui.theme.InterFont
import com.grappus.kavach.ui.theme.KavachTheme


@Composable
fun DetailScreen(navController: NavController, content: ContentListData) {

    KavachTheme.dark {
        Surface(Modifier.fillMaxSize()) {
            DetailScreenBody(navController, content)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreenBody(navController: NavController, content: ContentListData) {


    val fabItems = listOf(
        MiniFabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.read_fab_like),
            label = "Like",
            identifier = "LikeFab"
        ), MiniFabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.read_fab_bookmark),
            label = "Bookmark",
            identifier = "BookmarkFab"
        ), MiniFabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.read_fab_share),
            label = "Share",
            identifier = "ShareFab"
        )
    )

    val scrollState = rememberScrollState()


    Scaffold(
        floatingActionButton = { BottomFAB(items = fabItems) },
        content = {
            Scaffold(
                floatingActionButton = { BottomFAB(items = fabItems) }
            ) {

                Box(modifier = Modifier.fillMaxSize().background(color = Color.Black)){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(content.thumbnail)
                            .crossfade(true)
                            .build(),
                        placeholder = ColorPainter(Color.White),
                        contentDescription = "Background Image",
                        contentScale = ContentScale.Crop,
                        onError = {
                            Log.v("image error", it.toString())
                        },
                        modifier = Modifier
                            .height(700.dp)
                            .drawWithCache {
                                val gradient = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black),
                                    startY = 300f,
                                    endY = size.height
                                )
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(gradient, blendMode = BlendMode.Multiply)
                                }
                            }
                    )
                }




                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(state = scrollState),
                    verticalArrangement = Arrangement.Top
                ) {
                    FloatingActionButton(onClick = { navController.popBackStack() },
                        elevation = FloatingActionButtonDefaults.elevation(0.dp),
                        modifier = Modifier.padding(top = 5.dp, start = 20.dp),
                        containerColor = Color.Black.copy(alpha = 0.4f),
                        contentColor = Color.White,
                        shape = CircleShape,
                        content = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Back Arrow"
                            )
                        })
                    Text(
                        modifier = Modifier
                            .padding(top = 400.dp, start = 20.dp, end = 50.dp)
                            .padding(end = 50.dp),
                        text = content.title,
                        style = TextStyle(
                            fontFamily = InterFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            lineHeight = 34.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 13.dp, start = 20.dp, end = 40.dp),
                        text = content.description,
                        style = TextStyle(
                            fontFamily = InterFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            lineHeight = 30.sp,
                            letterSpacing = 0.1.sp,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                    )

                    Text(
                        "Scroll to deep dive",
                        style = TextStyle(
                            fontFamily = InterFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            lineHeight = 30.sp,
                            letterSpacing = 0.1.sp,
                            color = Color.White.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(CenterHorizontally),
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    Text(
                        content.description,
                        style = TextStyle(
                            fontFamily = InterFont,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            lineHeight = 30.sp,
                            letterSpacing = 0.1.sp,
                            color = Color.White.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(start = 20.dp, end = 30.dp),
                    )

                    Spacer(modifier = Modifier.height(60.dp))

                }
            }
        }
    )


}

@Composable
fun BottomFAB(
    items: List<MiniFabItem>
) {
    var isExpanded by remember { mutableStateOf(false) }

    val transition = updateTransition(targetState = isExpanded, label = null)

    val rotate by transition.animateFloat(label = "rotate") {
        if (it) 20f else 0f
    }

    val iconContent by transition.animateInt(label = "IconContent") {
        if (it) 1 else 0
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, end = 20.dp, bottom = if (isExpanded) 0.dp else 21.dp),
        horizontalArrangement = Arrangement.End
    ) {

        if (isExpanded) {
            Spacer(modifier = Modifier.width(60.dp))
        }

        if (isExpanded) {
            items.forEach {
                MiniFab(item = it, onMiniFabItemClick = { miniFabItem ->
                    when (miniFabItem.identifier) {
                        "LikeFab" -> {

                        }

                        "BookmarkFab" -> {

                        }

                        "ShareFab" -> {

                        }
                    }

                })
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        FloatingActionButton(
            onClick = {
                isExpanded = !isExpanded
            },
            modifier = Modifier.graphicsLayer { rotationZ = rotate },
            containerColor = Color.White,
            contentColor = Color.Black,
            shape = CircleShape,

            content = {
                Crossfade(targetState = iconContent, label = "Icon Fade") { contentState ->
                    if (contentState == 0) {
                        Box(
                            modifier = Modifier.size(30.dp),
                            contentAlignment = Center,
                        ) {
                            Icon(
                                painterResource(id = R.drawable.read_fab_menu),
                                contentDescription = null
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun MiniFab(
    item: MiniFabItem, onMiniFabItemClick: (MiniFabItem) -> Unit
) {
    Column(
        horizontalAlignment = CenterHorizontally
    ) {
        Canvas(
            modifier = Modifier
                .size(32.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(), onClick = {
                        onMiniFabItemClick.invoke(item)
                    }, indication = rememberRipple(
                        bounded = false, radius = 0.dp, color = Color.White.copy(alpha = 0.08f)

                    )
                )
                .padding(top = 20.dp)
        ) {
            drawCircle(
                color = Color.White.copy(alpha = 0.08f), radius = 64f
            )

            drawImage(
                image = item.icon, topLeft = Offset(
                    center.x - (item.icon.width / 2),
                    center.y - (item.icon.height / 2),
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            item.label,
            style = TextStyle(
                fontFamily = InterFont,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.White
            ),
            modifier = Modifier
                .padding(top = 10.dp)
        )
    }
}


class MiniFabItem(
    val icon: ImageBitmap, val label: String, val identifier: String
) {

}

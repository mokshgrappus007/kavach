package com.grappus.kavach.presentation.detail

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.grappus.kavach.R
import com.grappus.kavach.ui.theme.InterFont
import com.grappus.kavach.ui.theme.KavachTheme


@Composable
fun DetailScreen(
    navController: NavController,
    thumbnail: String,
    title: String,
    description: String
) {
    KavachTheme.dark {
        Surface(Modifier.fillMaxSize()) {
            DetailScreenContent(navController, thumbnail, title, description)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreenContent(
    navController: NavController,
    thumbnail: String,
    title: String,
    description: String
) {
    val fabItems = listOf(
        MiniFabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.read_fab_like),
            label = "Like",
            identifier = MiniFabIdentifier.LikeFab
        ),
        MiniFabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.read_fab_bookmark),
            label = "Bookmark",
            identifier = MiniFabIdentifier.BookmarkFab
        ),
        MiniFabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.read_fab_share),
            label = "Share",
            identifier = MiniFabIdentifier.ShareFab
        )
    )

    val scrollState = rememberScrollState()

    val isClicked = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 5.dp, start = 20.dp)
                    .size(48.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.4f),
                        shape = CircleShape
                    ).clickable {
                        if (!isClicked.value) {
                            isClicked.value = true
                            navController.popBackStack()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back Arrow",
                    tint = Color.White
                )
            }
        },
        floatingActionButton = { BottomFAB(fabItems) }
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(color = Color.Black)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnail)
                    .crossfade(true)
                    .build(),
                placeholder = ColorPainter(Color.White),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
                onError = { ColorPainter(Color.White) }
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(state = scrollState).background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black),
                    startY = 0f,
                    endY = 1460f,
                    tileMode = TileMode.Clamp
                )
            ),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier.padding(top = 400.dp, start = 20.dp, end = 50.dp),
                text = title,
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
                text = description,
                style = Constants.descriptionTextStyle
            )
            Text(
                "Scroll to deep dive",
                style = Constants.descriptionTextStyle,
                modifier = Modifier.padding(top = 20.dp).align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                description,
                style = Constants.descriptionTextStyle,
                modifier = Modifier.align(CenterHorizontally).padding(start = 20.dp, end = 30.dp)
            )
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
fun BottomFAB(items: List<MiniFabItem>) {
    var isExpanded by remember { mutableStateOf(false) }

    val transition = updateTransition(targetState = isExpanded, label = null)

    val rotate by transition.animateFloat(label = "rotate") {
        if (it) 2f else 0f
    }

    val iconContent by transition.animateInt(label = "IconContent") {
        if (it) 1 else 0
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = 20.dp, end = 20.dp, bottom = 10.dp)
            .background(
                if (isExpanded) {
                    Color.Black.copy(alpha = 0.6f)
                } else {
                    Color.Transparent
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        if (isExpanded) {
            Spacer(modifier = Modifier.width(60.dp))
        }

        if (isExpanded) {
            items.forEach {
                MiniFab(item = it, onMiniFabItemClick = { miniFabItem ->
                    when (miniFabItem.identifier) {
                        MiniFabIdentifier.LikeFab -> {
                            // Handle LikeFab click
                        }

                        MiniFabIdentifier.BookmarkFab -> {
                            // Handle BookmarkFab click
                        }

                        MiniFabIdentifier.ShareFab -> {
                            // Handle ShareFab click
                        }
                    }
                }
                )
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
            shape = CircleShape
        ) {
            Crossfade(targetState = iconContent, label = "Icon Fade") { contentState ->
                if (contentState == 0) {
                    Box(
                        modifier = Modifier.size(30.dp),
                        contentAlignment = Alignment.Center
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
        }
    }
}

@Composable
fun MiniFab(item: MiniFabItem, onMiniFabItemClick: (MiniFabItem) -> Unit) {
    Column(horizontalAlignment = CenterHorizontally) {
        Canvas(
            modifier = Modifier
                .size(32.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { onMiniFabItemClick.invoke(item) },
                    indication = rememberRipple(bounded = false, radius = 0.dp, color = Color.White.copy(alpha = 0.08f))
                )
                .padding(top = 20.dp)
        ) {
            drawCircle(
                color = Color.White.copy(alpha = 0.08f),
                radius = 64f
            )
            drawImage(
                image = item.icon,
                topLeft = Offset(
                    center.x - (item.icon.width / 2),
                    center.y - (item.icon.height / 2)
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
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

data class MiniFabItem(val icon: ImageBitmap, val label: String, val identifier: MiniFabIdentifier)

enum class MiniFabIdentifier {
    LikeFab,
    BookmarkFab,
    ShareFab
}

object Constants {
    val descriptionTextStyle = TextStyle(
        fontFamily = InterFont,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.1.sp,
        color = Color.White.copy(alpha = 0.5f)
    )
}

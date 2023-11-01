package com.grappus.kavach.presentation.read

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grappus.kavach.R
import com.grappus.kavach.ui.theme.KavachTheme

@Composable
fun ReadScreen(navController: NavController) {
    KavachTheme(KavachTheme.darkColorScheme, true) {
        ReadScreenBody(navController)
    }
}

@Composable
fun ReadScreenBody(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
        ) {
            Image(
                painter = painterResource(id = R.drawable.read_bg),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                FloatingActionButton(
                    onClick = { navController.popBackStack() },
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    modifier = Modifier
                        .padding(top = 5.dp, start = 20.dp),
                    containerColor = Color.White.copy(alpha = 0.4f),
                    contentColor = Color.White,
                    shape = CircleShape,
                    content = {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back Arrow"
                        )
                    }
                )
                Box(
                    modifier = Modifier.padding(top = 256.dp, start = 20.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.read_tag_bg),
                        contentDescription = "Tag Background Image",
                        modifier = Modifier
                            .width(149.dp)
                            .height(40.dp)

                    )
                    Text(
                        text = "DEPRESSION",
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            lineHeight = 34.sp,
                        )
                    )

                }
                Text(
                    modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 50.dp),
                    text = "These are the 10 ways to rejection proof yourself!",
                    style = TextStyle(fontSize = 28.sp)
                )

            }
        }

    }
}
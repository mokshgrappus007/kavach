package com.grappus.kavach.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.grappus.kavach.navigation.Screen
import com.grappus.kavach.ui.theme.BruleFont
import com.grappus.kavach.ui.theme.KavachColor
import com.grappus.kavach.ui.theme.KavachTheme
import com.grappus.kavach.ui.theme.Typography

@Composable
fun LoginScreen(navController: NavController) {
    var phoneTextFieldState by remember {
        mutableStateOf("");
    }

    KavachTheme.light {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = KavachColor.CornSilk,
                    modifier = Modifier
                        .padding(
                            horizontal = 20.dp,
                            vertical = 10.dp
                        ),
                    onClick = {},
                    elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(50.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = KavachColor.vampireBlack
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.DashboardScreen.route)
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 15.dp)

                        ) {
                            Text(
                                text = "Continue",
                                style = Typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    color = KavachColor.White,
                                    fontWeight = FontWeight.SemiBold
                                ),
                            )
                        }
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = KavachColor.CornSilk
            ) {
                Box(
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 25.dp
                    ),
                ) {
                    Column {
                        Row(Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .size(
                                        height = 3.dp, width = 175.dp
                                    )
                                    .background(color = KavachColor.vampireBlack)
                                    .weight(1f)
                            )
                            Box(
                                modifier = Modifier
                                    .size(
                                        height = 3.dp, width = 175.dp
                                    )
                                    .background(color = KavachColor.Black.copy(alpha = 0.2F))
                                    .weight(1f)
                            )
                        }
                        Box(Modifier.height(25.dp))
                        Box(Modifier.width(240.dp)) {
                            Text(
                                text = "YOUR NUMBER?",
                                style = Typography.titleLarge.copy(
                                    fontSize = 39.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = BruleFont,
                                    lineHeight = 35.sp,
                                )
                            )
                        }
                        Box(Modifier.height(8.dp))
                        Text(
                            text = "We'll use it to verify your account.",
                            style = Typography.bodyLarge.copy(
                                fontSize = 16.sp,
                                color = KavachColor.vampireBlack.copy(alpha = 0.6F)
                            ),
                        )
                        Box(Modifier.height(56.dp))
                        Card(
                            shape = RoundedCornerShape(80.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = KavachColor.vampireBlack.copy(
                                    alpha = 0.1F
                                )
                            ),
                        ) {
                            Box(
                                modifier = Modifier.padding(
                                    horizontal = 16.dp,
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "+91",
                                        style = Typography.bodyLarge.copy(
                                            fontSize = 16.sp,
                                            color = KavachColor.vampireBlack,
                                            fontWeight = FontWeight.Normal
                                        ),
                                    )
                                    Spacer(
                                        modifier = Modifier.width(13.dp)
                                    )
                                    Text(
                                        text = "|",
                                        style = Typography.bodyLarge.copy(
                                            fontSize = 16.sp,
                                            color = KavachColor.vampireBlack,
                                            fontWeight = FontWeight.Normal
                                        ),
                                    )
                                    TextField(
                                        value = phoneTextFieldState,
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = KavachColor.Transparent,
                                            unfocusedContainerColor = KavachColor.Transparent,
                                            focusedIndicatorColor = KavachColor.Transparent,
                                            unfocusedIndicatorColor = KavachColor.Transparent,

                                            ),
                                        placeholder = {
                                            Text(
                                                text = "Enter phone number ...",
                                                style = Typography.bodyLarge.copy(
                                                    fontSize = 16.sp,
                                                    color = KavachColor.vampireBlack,
                                                    fontWeight = FontWeight.Medium
                                                ),
                                            )
                                        },
                                        onValueChange = {
                                            phoneTextFieldState = it
                                        },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                                    )

                                }
                            }
                        }
                        Spacer(
                            modifier = Modifier.height(21.dp)
                        )
                        Box(
                            Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                text = "Use email instead?",
                                style = Typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    color = KavachColor.vampireBlack,
                                    fontWeight = FontWeight.Medium
                                ),

                                )
                        }
                    }
                }
            }
        }
    }
}
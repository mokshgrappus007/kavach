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
import com.grappus.kavach.utils.Constants

@Composable
fun LoginScreen(navController: NavController) {
    var phoneTextFieldState by remember {
        mutableStateOf("");
    }

    KavachTheme.light {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = (Constants.screenWidth * 0.04).dp,
                    vertical = (Constants.screenHeight * 0.02).dp
                )
        ) {
            Column {
                Spacer(
                    modifier = Modifier.height((Constants.screenHeight * 0.015).dp)
                )
                Row(Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(
                                height = 3.dp, width = (Constants.screenWidth * 0.4).dp
                            )
                            .background(color = KavachColor.vampireBlack)
                            .weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .size(
                                height = 3.dp, width = (Constants.screenWidth * 0.4).dp
                            )
                            .background(color = KavachColor.Black.copy(alpha = 0.2F))
                            .weight(1f)
                    )
                }
                Box(Modifier.height((Constants.screenHeight * 0.04).dp))
                Box(Modifier.width((Constants.screenWidth * 0.6).dp)) {
                    Text(
                        text = "YOUR NUMBER?", style = Typography.titleLarge.copy(
                            fontSize = 39.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = BruleFont,
                            lineHeight = 35.sp,
                        )
                    )
                }
                Box(Modifier.height((Constants.screenHeight * 0.015).dp))
                Text(
                    text = "We will use it to verify your account.",
                    style = Typography.bodyLarge.copy(
                        fontSize = 16.sp, color = KavachColor.vampireBlack.copy(alpha = 0.6F)
                    ),
                )
                Box(Modifier.height((Constants.screenHeight * 0.09).dp))
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
                            horizontal = (Constants.screenWidth * 0.04).dp,
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
                                modifier = Modifier.width((Constants.screenWidth * 0.03).dp)
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
                    modifier = Modifier.height((Constants.screenHeight * 0.03).dp)
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
                Spacer(
                    modifier = Modifier.height((Constants.screenHeight * 0.45).dp)
                )
                Card(shape = RoundedCornerShape(50.dp), colors = CardDefaults.cardColors(
                    containerColor = KavachColor.vampireBlack
                ), modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Screen.DashboardScreen.route)
                    }) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = (Constants.screenHeight * 0.018).dp)

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
                Spacer(
                    modifier = Modifier.height((Constants.screenHeight * 0.03).dp)
                )
            }
        }
    }
}
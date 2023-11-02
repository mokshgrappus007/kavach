package com.grappus.kavach.presentation.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.grappus.kavach.navigation.Screen
import com.grappus.kavach.ui.theme.BruleFont
import com.grappus.kavach.ui.theme.KavachColor
import com.grappus.kavach.ui.theme.Typography
import com.grappus.kavach.utils.Constants
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PhoneVerificationPage(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val phoneNumberState = viewModel.phoneTextState
    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AuthViewModel.UiEvent.ShowSnackbar -> {
                    snackbarState.showSnackbar(message = event.message)
                }

                is AuthViewModel.UiEvent.Navigate -> {
                    navController.navigate(Screen.DashboardScreen.route)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarState) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(KavachColor.cornSilk)
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
                            .size(height = 3.dp, width = (Constants.screenWidth * 0.4).dp)
                            .background(color = KavachColor.vampireBlack)
                            .weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .size(height = 3.dp, width = (Constants.screenWidth * 0.4).dp)
                            .background(color = KavachColor.black.copy(alpha = 0.2F))
                            .weight(1f)
                    )
                }
                Box(Modifier.height((Constants.screenHeight * 0.04).dp))
                Box(Modifier.width((Constants.screenWidth * 0.6).dp)) {
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
                Box(Modifier.height((Constants.screenHeight * 0.015).dp))
                Text(
                    text = "We will use it to verify your account.",
                    style = Typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        color = KavachColor.vampireBlack.copy(alpha = 0.6F)
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
                        modifier = Modifier
                            .padding(
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
                                value = phoneNumberState,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = KavachColor.colorTransparent,
                                    unfocusedContainerColor = KavachColor.colorTransparent,
                                    focusedIndicatorColor = KavachColor.colorTransparent,
                                    unfocusedIndicatorColor = KavachColor.colorTransparent,

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
                                    viewModel.onEvent(PhoneVerificationEvent.PhoneNumberChanges(it))
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
            Card(
                shape = RoundedCornerShape(50.dp),
                colors = CardDefaults.cardColors(
                    containerColor = KavachColor.vampireBlack
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = (Constants.screenHeight * 0.018).dp)
                ) {
                    Text(
                        text = "Continue",
                        style = Typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            color = KavachColor.white,
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
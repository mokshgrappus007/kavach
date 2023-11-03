package com.grappus.kavach.presentation.auth

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.grappus.kavach.navigation.Screen
import com.grappus.kavach.ui.theme.BruleFont
import com.grappus.kavach.ui.theme.KavachColor
import com.grappus.kavach.ui.theme.KavachTheme
import com.grappus.kavach.ui.theme.Typography
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val phoneNumberState = viewModel.phoneTextState
    val snackbarState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginViewModel.UiEvent.ShowSnackbar -> {
                    snackbarState.showSnackbar(message = event.message)
                }

                is LoginViewModel.UiEvent.Navigate -> {
                    navController.navigate(route = Screen.DashboardScreen.route, builder = {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                    })
                }
            }
        }
    }

    KavachTheme.light {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarState) },
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = KavachColor.CornSilk,
                    modifier = Modifier.padding(
                        horizontal = 20.dp, vertical = 10.dp
                    ),
                    onClick = {},
                    elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Card(shape = RoundedCornerShape(50.dp), colors = CardDefaults.cardColors(
                        containerColor = KavachColor.vampireBlack
                    ), modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(LoginScreenEvent.SendOtp(phoneNumberState))
                        }) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(vertical = 15.dp)

                        ) {
                            if (viewModel.isLoginInProgress.value) CircularProgressIndicator(
                                Modifier.size(24.dp), color = Color.White
                            )
                            else Text(
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
                        horizontal = 20.dp, vertical = 25.dp
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
                                text = "YOUR NUMBER?", style = Typography.titleLarge.copy(
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
                                        value = phoneNumberState,
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
                                        onValueChange = { number ->
                                            if (number.all { it.isDigit() } && number.length <= 10) {
                                                val phoneNumber = number.toLongOrNull()
                                                viewModel.onEvent(
                                                    LoginScreenEvent.PhoneNumberChanges(
                                                        phoneNumber?.toString() ?: ""
                                                    )
                                                )
                                            }
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
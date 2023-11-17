package com.grappus.kavach.presentation.auth

import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import io.metamask.androidsdk.Dapp

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    twitchAccessToken: String,
) {
    val phoneNumberState = viewModel.phoneTextState
    val snackBarState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(key1 = twitchAccessToken) {
        if (twitchAccessToken != "null") {
            Log.v("token is", twitchAccessToken)
            viewModel.getTwitchUserName(twitchAccessToken)
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is LoginViewModel.UiEvent.ShowSnackbar -> {
                    snackBarState.showSnackbar(message = event.message)
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
        Scaffold(snackbarHost = { SnackbarHost(snackBarState) }, floatingActionButton = {
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
                        focusManager.clearFocus()
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
        }, floatingActionButtonPosition = FabPosition.Center
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
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Column(
                            ) {
                                // Google, Discord, Apple, twitch, Metamask.
                                LoginOption(
                                    onClicked = {
                                        val twitchAuthUrl =
                                            "https://id.twitch.tv/oauth2/authorize" +
                                                    "?response_type=token" +
                                                    "&client_id=bktdr8rtppds8dk55fggg8yqezcgqu" +
                                                    "&redirect_uri=https://tinyurl.com/bdctujsj" +
                                                    "&scope=channel%3Amanage%3Apolls+channel%3Aread%3Apolls" +
                                                    "&state=c3ab8aa609ea11e793ae92361f002671"

                                        val intent =
                                            Intent(Intent.ACTION_VIEW, Uri.parse(twitchAuthUrl))
                                        context.startActivity(intent)
                                    }, title = "Login with Twitch"
                                )
                                LoginOption(
                                    onClicked = {}, title = "Login with Discord"
                                )
                                LoginOption(
                                    onClicked = {
                                        viewModel.connect(
                                            dapp = Dapp(
                                                name = "Moksh",
                                                url = "moksh.com"
                                            )
                                        )
                                    }, title = "Login with Metamask"
                                )
                                LoginOption(
                                    onClicked = {}, title = "Login with Apple"
                                )
                                LoginOption(
                                    onClicked = {}, title = "Login with Google"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LoginOption(onClicked: () -> Unit, title: String) {
    TextButton(
        modifier = Modifier.padding(bottom = 16.dp),
        onClick = onClicked,
    ) {
        Text(
            text = title,
            style = Typography.bodyLarge.copy(
                fontSize = 16.sp, color = KavachColor.vampireBlack, fontWeight = FontWeight.Medium,
            ),
        )
    }
}
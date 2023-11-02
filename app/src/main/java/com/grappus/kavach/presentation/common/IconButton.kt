package com.grappus.kavach.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.grappus.kavach.ui.theme.KavachColor

@Composable
fun KavachIconButton(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
    image: @Composable ()-> Unit,
) {
    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .background(KavachColor.LightSilverOpacity8, CircleShape)
            .clip(CircleShape)
            .clickable {
                onClicked()
            }) {
        Box (Modifier.padding(15.dp)) {
            image()
        }

    }
}
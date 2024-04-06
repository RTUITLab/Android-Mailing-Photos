package com.scrollz.emailphotolab.presentation.photo.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.scrollz.emailphotolab.R

@Composable
fun CameraUI(
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean,
    takePhoto: () -> Unit,
    close: () -> Unit
) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                drawCircle(
                    color = Color.White,
                    style = Stroke(4.dp.toPx()),
                    radius = size.minDimension / 2
                )
            }
            Surface(
                modifier = Modifier
                    .size(56.dp)
                    .clickable(
                        onClick = takePhoto,
                        enabled = isButtonEnabled,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = false,
                            radius = 28.dp,
                            color = Color.Black
                        )
                    ),
                color = Color.White,
                shape = CircleShape
            ) { }
        }
        IconButton(
            modifier = Modifier.align(Alignment.TopStart),
            onClick = close,
            enabled = isButtonEnabled
        ) {
            Icon(
                painter = painterResource(R.drawable.back),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

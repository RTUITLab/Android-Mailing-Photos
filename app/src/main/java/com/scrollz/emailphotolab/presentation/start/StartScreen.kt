package com.scrollz.emailphotolab.presentation.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.scrollz.emailphotolab.presentation.common.PrimaryButton

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    navigateToFill: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Рассыльщик 1.0",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier.widthIn(max = 420.dp),
            text = "Делайте фотографии в локации ИИТ и они отправятся к вам на e-mail!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier.widthIn(max = 420.dp),
            text = "Далее вам необходимо вести электронную почту и сделать 4 фотографии, чтобы алгоритм вас запомнил.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(64.dp))
        PrimaryButton(
            modifier = Modifier
                .widthIn(max = 512.dp)
                .fillMaxWidth()
                .height(56.dp),
            onClick = navigateToFill,
            text = "Начать",
            textStyle = MaterialTheme.typography.displayLarge
        )
    }
}

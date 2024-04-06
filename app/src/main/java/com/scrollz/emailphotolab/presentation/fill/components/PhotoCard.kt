package com.scrollz.emailphotolab.presentation.fill.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.scrollz.emailphotolab.R
import com.scrollz.emailphotolab.domain.model.Photo
import com.scrollz.emailphotolab.presentation.common.PrimaryButton

@Composable
fun PhotoCard(
    modifier: Modifier = Modifier,
    photo: Photo,
    enabled: Boolean,
    takePhoto: () -> Unit,
    painter: Painter
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(photo.uri)
                .memoryCacheKey(photo.key)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            loading = {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            error = {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            contentDescription = null
        )
        PrimaryButton(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(36.dp)
                .align(Alignment.CenterHorizontally),
            onClick = takePhoto,
            enabled = enabled,
            text = if (photo.isTaken) "Изменить" else "Сделать фото",
            shape = RoundedCornerShape(12.dp)
        )
    }
}

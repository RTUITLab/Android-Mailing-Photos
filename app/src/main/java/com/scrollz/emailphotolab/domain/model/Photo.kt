package com.scrollz.emailphotolab.domain.model

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class Photo(
    val title: String,
    val uri: Uri,
    val key: String,
    val isTaken: Boolean = false
)

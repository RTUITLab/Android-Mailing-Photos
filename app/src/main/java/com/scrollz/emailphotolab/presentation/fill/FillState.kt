package com.scrollz.emailphotolab.presentation.fill

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.scrollz.emailphotolab.domain.model.Photo

@Stable
data class FillState(
    val enabled: Boolean = true,
    val isPermissionDialogVisible: Boolean = false,
    val emailText: String = "",
    val isEmailValid: Boolean = true,
    val photos: SnapshotStateList<Photo> = mutableStateListOf(),
    val saved: Boolean = false,
    val checked: Boolean = false
) {
    val isButtonEnabled: Boolean
        get() = photos.all { it.isTaken } && emailText.isNotBlank() && isEmailValid && checked
}

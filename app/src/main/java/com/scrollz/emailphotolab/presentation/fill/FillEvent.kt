package com.scrollz.emailphotolab.presentation.fill

import android.net.Uri

sealed class FillEvent {
    data object TogglePermissionDialog: FillEvent()
    data class ChangeEmail(val value: String): FillEvent()
    data class OnTakePhoto(val index: Int): FillEvent()
    data class TakePhoto(val launch: (Uri) -> Unit): FillEvent()
    data object OnPhotoTaken: FillEvent()
    data class ToggleCheck(val value: Boolean): FillEvent()
    data object Save: FillEvent()
}

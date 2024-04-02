package com.scrollz.emailphotolab.domain.repository

import android.net.Uri

interface GalleryRepository {
    suspend fun saveToGallery(uri: Uri, name: String, folderName: String)
}

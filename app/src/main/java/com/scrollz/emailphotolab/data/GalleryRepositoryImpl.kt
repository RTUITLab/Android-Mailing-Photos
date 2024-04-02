package com.scrollz.emailphotolab.data

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.scrollz.emailphotolab.domain.repository.GalleryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
): GalleryRepository {
    override suspend fun saveToGallery(uri: Uri, name: String, folderName: String) {
        withContext(Dispatchers.IO) {
            runCatching {
                val values = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, name)
                    put(MediaStore.Images.Media.MIME_TYPE, appContext.contentResolver.getType(uri))
                    put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        "${Environment.DIRECTORY_PICTURES}/EmailPhotoLab/$folderName"
                    )
                }

                appContext.contentResolver.run {
                    val galleryUri = insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

                    galleryUri?.let { imageUri ->
                        openInputStream(uri)?.use { inputStream ->
                            openOutputStream(imageUri)?.use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        }
                    }
                }
            }.onFailure {
                Log.e("galleryRepository", "saving to gallery error")
            }
        }
    }
}

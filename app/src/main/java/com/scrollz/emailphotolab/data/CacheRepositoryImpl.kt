package com.scrollz.emailphotolab.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.scrollz.emailphotolab.domain.repository.CacheRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class CacheRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
): CacheRepository {
    override suspend fun createTempFile(name: String): Uri = withContext(Dispatchers.IO) {
        runCatching {
            val file = File.createTempFile(name, ".jpeg", appContext.cacheDir)
            FileProvider.getUriForFile(
                appContext,
                "${appContext.packageName}.provider",
                file
            )
        }.getOrElse {
            Log.e("CacheRepository", "create temp file error")
            Uri.EMPTY
        }
    }

    override suspend fun clear() {
        withContext(Dispatchers.IO) {
            runCatching { appContext.cacheDir.deleteRecursively()  }
        }
    }
}

package com.scrollz.emailphotolab.domain.repository

import android.net.Uri

interface CacheRepository {
    suspend fun createTempFile(name: String): Uri
    suspend fun clear()
}

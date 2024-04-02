package com.scrollz.emailphotolab.domain.use_case

import com.scrollz.emailphotolab.domain.repository.CacheRepository
import javax.inject.Inject

class ClearCacheUseCase @Inject constructor(
    private val cacheRepository: CacheRepository
) {
    suspend operator fun invoke() = cacheRepository.clear()
}

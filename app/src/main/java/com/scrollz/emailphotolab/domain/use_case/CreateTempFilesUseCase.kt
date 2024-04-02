package com.scrollz.emailphotolab.domain.use_case

import com.scrollz.emailphotolab.domain.model.Photo
import com.scrollz.emailphotolab.domain.repository.CacheRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CreateTempFilesUseCase @Inject constructor(
    private val cacheRepository: CacheRepository
) {
    suspend operator fun invoke(names: List<String>): List<Photo> = coroutineScope {
        List(names.size) { i -> async { cacheRepository.createTempFile("photo$i") } }
            .awaitAll()
            .mapIndexed { i, uri ->
                Photo(
                    title = names[i],
                    uri = uri,
                    key = System.currentTimeMillis().toString()
                )
            }
    }
}

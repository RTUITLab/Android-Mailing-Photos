package com.scrollz.emailphotolab.domain.use_case

import com.scrollz.emailphotolab.domain.model.Photo
import com.scrollz.emailphotolab.domain.repository.GalleryRepository
import javax.inject.Inject

class SaveToGalleryUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository
) {
    suspend operator fun invoke(photos: List<Photo>, email: String) {
        photos.forEach { galleryRepository.saveToGallery(it.uri, it.title, email) }
    }
}

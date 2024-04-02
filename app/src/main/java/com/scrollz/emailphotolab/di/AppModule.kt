package com.scrollz.emailphotolab.di

import com.scrollz.emailphotolab.data.CacheRepositoryImpl
import com.scrollz.emailphotolab.data.GalleryRepositoryImpl
import com.scrollz.emailphotolab.domain.repository.CacheRepository
import com.scrollz.emailphotolab.domain.repository.GalleryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun bindCacheRepository(
        cacheRepository: CacheRepositoryImpl
    ): CacheRepository

    @Binds
    @Singleton
    fun bindGalleryRepository(
        galleryRepository: GalleryRepositoryImpl
    ): GalleryRepository

}

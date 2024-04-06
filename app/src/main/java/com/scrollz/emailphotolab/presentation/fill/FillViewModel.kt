package com.scrollz.emailphotolab.presentation.fill

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrollz.emailphotolab.domain.use_case.ClearCacheUseCase
import com.scrollz.emailphotolab.domain.use_case.CreateTempFilesUseCase
import com.scrollz.emailphotolab.domain.use_case.SaveToGalleryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FillViewModel @Inject constructor(
    private val createTempFiles: CreateTempFilesUseCase,
    private val clearCache: ClearCacheUseCase,
    private val saveToGallery: SaveToGalleryUseCase
): ViewModel() {

    private val _state = MutableStateFlow(FillState())
    val state get() = _state.asStateFlow()

    private var lastChangedIndex: Int = 0

    init {
        createTempFiles()
    }

    fun onEvent(event: FillEvent) {
        when (event) {
            is FillEvent.TogglePermissionDialog -> _state.update {
                it.copy(isPermissionDialogVisible = !it.isPermissionDialogVisible)
            }
            is FillEvent.ChangeEmail -> _state.update { it.copy(
                emailText = event.value,
                isEmailValid = event.value.isEmpty() ||
                                Patterns.EMAIL_ADDRESS.matcher(event.value).matches()
            ) }
            is FillEvent.OnTakePhoto -> lastChangedIndex = event.index
            is FillEvent.TakePhoto -> event.launch(_state.value.photos[lastChangedIndex].uri)
            is FillEvent.OnPhotoTaken -> {
                _state.value.photos[lastChangedIndex] = state.value.photos[lastChangedIndex]
                    .copy(key = System.currentTimeMillis().toString(), isTaken = true)
            }
            is FillEvent.ToggleCheck -> _state.update { it.copy(checked = event.value) }
            is FillEvent.Save -> save()
        }
    }

    private fun createTempFiles() {
        viewModelScope.launch {
            clearCache()
            val names = listOf("photo1", "photo2", "photo3", "photo4")
            _state.value.photos.addAll(createTempFiles(names))
        }
    }

    private fun save() {
        viewModelScope.launch {
            _state.update { it.copy(enabled = false) }
            saveToGallery(_state.value.photos, _state.value.emailText)
            _state.update { it.copy(saved = true) }
        }
    }

}

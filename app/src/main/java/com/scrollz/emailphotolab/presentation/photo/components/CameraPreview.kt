package com.scrollz.emailphotolab.presentation.photo.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import android.util.Size
import android.view.Surface
import androidx.annotation.OptIn
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors

@OptIn(ExperimentalCamera2Interop::class)
@Composable
internal fun CameraPreview(
    modifier: Modifier = Modifier,
    isLandscape: Boolean,
    uri: Uri,
    close: (Boolean) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val camera = remember { mutableStateOf<Camera?>(null) }
    val previewView = remember {
        PreviewView(context).apply {
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            scaleType = PreviewView.ScaleType.FILL_CENTER
            rotation = 0f
        }
    }

    var imageTaken by remember { mutableStateOf(false) }
    fun saveImage(imageProxy: ImageProxy) {
        context.contentResolver.openOutputStream(uri).use { out ->
            val matrix = Matrix().apply { postRotate(90f) }
            imageProxy.toBitmap().run {
                Bitmap.createBitmap(
                    this, 0, 0,
                    this.width, this.height,
                    matrix, true
                ).compress(Bitmap.CompressFormat.JPEG, 100, checkNotNull(out))
            }
        }
    }

    remember {
        ProcessCameraProvider.getInstance(context).apply {
            addListener({
                val cameraProvider = get()
                val cameraSelector = CameraSelector.Builder()
                    .addCameraFilter { cameras ->
                        cameras.filter { Camera2CameraInfo.from(it).cameraId == "140" }
                    }
                    .build()

                val resolutionSelector = ResolutionSelector.Builder()
                    .setResolutionStrategy(ResolutionStrategy.HIGHEST_AVAILABLE_STRATEGY)
                    .setResolutionFilter { a, _ ->
                        Log.i("sizes", a.toString());
                        listOf(Size(1920, 1080))
                    }.build()

                val preview = Preview.Builder()
                    .setResolutionSelector(resolutionSelector)
                    .build()
                    .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                val analysis = ImageAnalysis.Builder()
                    .setResolutionSelector(resolutionSelector)
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)
                    .build()
                    .also {
                        it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                            if (imageTaken) {
                                saveImage(imageProxy)
                                close(true)
                            }
                            imageProxy.close()
                        }
                    }

                try {
                    cameraProvider.unbindAll()
                    camera.value = cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        analysis
                    )
                } catch (e: Exception) {
                    Log.e("CameraX1", e.message.toString())
                }
            }, ContextCompat.getMainExecutor(context))
        }
    }


    var isButtonEnabled by remember { mutableStateOf(true) }
    val takePhoto = remember { {
        isButtonEnabled = false
        imageTaken = true
    } }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.aspectRatio(
                ratio = if (isLandscape) 9/16f else 9/16f,
                matchHeightConstraintsFirst = isLandscape
            ),
            factory = { previewView }
        )
        CameraUI(
            modifier = modifier,
            isLandscape = isLandscape,
            isButtonEnabled = isButtonEnabled,
            takePhoto = takePhoto,
            close = { close(false) }
        )
    }

//    Surface(
//        modifier = Modifier,
//        color = Color.Black
//    ) {
//        AndroidView(
//            modifier = Modifier.aspectRatio(
//                ratio = if (isLandscape) 9/16f else 9/16f,
//                matchHeightConstraintsFirst = isLandscape
//            ),
//            factory = { previewView }
//        )
//        CameraUI(
//            modifier = modifier,
//            isLandscape = isLandscape,
//            isButtonEnabled = isButtonEnabled,
//            takePhoto = takePhoto,
//            close = { close(false) }
//        )
//    }
}

package com.scrollz.emailphotolab.presentation.photo

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.view.WindowCompat
import com.scrollz.emailphotolab.presentation.photo.components.CameraPreview
import com.scrollz.emailphotolab.theme.PhotoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        val uri = intent.data ?: throw Exception("bad uri")
        setContent {
            PhotoTheme {
                val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    isLandscape = isLandscape,
                    uri = uri,
                    close = { result ->
                        if (result) setResult(Activity.RESULT_OK)
                        else setResult(Activity.RESULT_CANCELED)
                        finish()
                    }
                )
            }
        }
    }
}

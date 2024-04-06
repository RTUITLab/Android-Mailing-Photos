package com.scrollz.emailphotolab

import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.scrollz.emailphotolab.navigation.Navigation
import com.scrollz.emailphotolab.theme.EmailPhotoLabTheme
import com.scrollz.emailphotolab.theme.PhotoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailPhotoLabTheme {
                Navigation()
            }
        }
    }
}

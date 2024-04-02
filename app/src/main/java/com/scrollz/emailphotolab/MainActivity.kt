package com.scrollz.emailphotolab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.scrollz.emailphotolab.navigation.Navigation
import com.scrollz.emailphotolab.theme.EmailPhotoLabTheme
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

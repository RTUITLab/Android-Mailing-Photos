package com.scrollz.emailphotolab.presentation.fill.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scrollz.emailphotolab.presentation.common.PrimaryButton
import com.scrollz.emailphotolab.presentation.fill.FillEvent
import com.scrollz.emailphotolab.presentation.fill.FillState
import com.scrollz.emailphotolab.util.SettingsContract

@Composable
fun FillScreen(
    modifier: Modifier = Modifier,
    state: FillState,
    onEvent: (FillEvent) -> Unit,
    navigateBack: () -> Unit,
    navigateToFinish: () -> Unit
) {
    val scrollState = rememberScrollState()

    val settingsActivityResultLauncher = rememberLauncherForActivityResult(
        contract = SettingsContract(),
        onResult = {  }
    )
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { saved -> if (saved) { onEvent(FillEvent.OnPhotoTaken) } }
    )
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                onEvent(FillEvent.TakePhoto { uri -> takePictureLauncher.launch(uri) })
            } else {
                onEvent(FillEvent.TogglePermissionDialog)
            }
        }
    )

    if (state.isPermissionDialogVisible) {
        PermissionDialog(
            onDismiss = { onEvent(FillEvent.TogglePermissionDialog) },
            onConfirm = {
                onEvent(FillEvent.TogglePermissionDialog)
                settingsActivityResultLauncher.launch(null)
            }
        )
    }

    LaunchedEffect(state.saved) { if (state.saved) { navigateToFinish() } }

    Scaffold(
        modifier = modifier,
        topBar = { TopBar (navigateBack = navigateBack) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.emailText,
                placeholderText = "Email",
                enabled = state.enabled,
                isError = !state.isEmailValid,
                onValueChange = { value -> onEvent(FillEvent.ChangeEmail(value)) },
                clearField = { onEvent(FillEvent.ChangeEmail("")) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 640.dp),
                columns = GridCells.Adaptive(160.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                userScrollEnabled = false
            ) {
                itemsIndexed(
                    items = state.photos
                ) { index, photo ->
                    PhotoCard(
                        photo = photo,
                        enabled = state.enabled,
                        takePhoto = {
                            onEvent(FillEvent.OnTakePhoto(index))
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Spacer(modifier = Modifier.weight(1f, fill = true))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.checked,
                    onCheckedChange = { value -> onEvent(FillEvent.ToggleCheck(value)) },
                    enabled = state.enabled,
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        checkmarkColor = MaterialTheme.colorScheme.onPrimary,
                        disabledCheckedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledUncheckedColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Text(
                    text = "Я согласен на обработку и хранение данных",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (state.checked) MaterialTheme.colorScheme.onBackground
                            else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = { onEvent(FillEvent.Save) },
                text = "Сохранить",
                textStyle = MaterialTheme.typography.displayLarge,
                enabled = state.enabled && state.isButtonEnabled
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

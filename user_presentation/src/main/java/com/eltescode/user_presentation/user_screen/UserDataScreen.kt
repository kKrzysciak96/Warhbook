package com.eltescode.user_presentation.user_screen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.WorkManager
import com.eltescode.core_ui.utils.UiEvent
import com.eltescode.user_presentation.components.BaseCard
import com.eltescode.user_presentation.components.PhotoChooserDialog
import com.eltescode.user_presentation.components.SettingsDialog
import com.eltescode.user_presentation.components.SettingsIcon
import com.eltescode.user_presentation.components.SignOutIcon
import com.eltescode.user_presentation.components.UserPicture
import com.eltescode.user_presentation.utils.PhotoCompressionWorker
import com.eltescode.user_presentation.utils.UriHelper
import com.eltescode.user_presentation.utils.UserDataScreenEvent
import com.eltescode.user_presentation.utils.UserDataScreenEvent.PhotoDialogEvents.OnChooseFromAlbumClick
import com.eltescode.user_presentation.utils.UserDataScreenEvent.PhotoDialogEvents.OnChooseFromInternetClick
import com.eltescode.user_presentation.utils.UserDataScreenEvent.PhotoDialogEvents.OnTakePhotoClick
import com.eltescode.user_presentation.utils.UserScreenState
import com.eltescode.user_presentation.utils.UserScreens
import com.eltescode.user_presentation.utils.photoOneTimeWorkRequestBuilder
import java.io.File

@Composable
fun UserDataScreen(
    photoUriHelper: UriHelper,
    workManager: WorkManager,
    viewModel: UserDataViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onNextScreen: (String) -> Unit
) {
    val state = viewModel.state
    val context = LocalContext.current
    val workResult = viewModel.workId?.let { id ->
        workManager.getWorkInfoByIdLiveData(id).observeAsState().value
    }
    val takePhotoContract = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isPhotoTaken ->
            if (isPhotoTaken) {
                if (state.photoUri != null) {
                    val request = photoOneTimeWorkRequestBuilder(state.photoUri)
                    viewModel.updateWorkId(request.id)
                    workManager.enqueue(request)
                }
            }
        })

    val selectPhotoFromAlbumContract = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { photoUri ->
            if (photoUri != null) {
                val request = photoOneTimeWorkRequestBuilder(photoUri)
                viewModel.updateWorkId(request.id)
                workManager.enqueue(request)
            }
        }
    )
    LaunchedEffect(key1 = true, block = {
        photoUriHelper.photoUri?.let { photoUri ->
            val request = photoOneTimeWorkRequestBuilder(photoUri)
            viewModel.updateWorkId(request.id)
            workManager.enqueue(request)
            photoUriHelper.photoUri = null
        }
    })


    LaunchedEffect(key1 = workResult?.outputData) {
        if (workResult?.outputData != null) {
            val filePAth = workResult.outputData.getString(PhotoCompressionWorker.KEY_RESULT_PATH)
            filePAth?.let {
                val bytes = File(filePAth).readBytes()
                viewModel.onEvent(UserDataScreenEvent.OnUploadPhoto(bytes))
                viewModel.updateWorkId(null)
                viewModel.resetUri()

            }
        }
    }

    LaunchedEffect(true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.OnNavigateUp -> Unit
                UiEvent.OnNextScreen -> {
                    onNextScreen("")
                }

                is UiEvent.ShowSnackBar -> {}
                UiEvent.Success -> {
                    onSuccess()
                }
            }
        }
    }
    LaunchedEffect(true) {
        viewModel.photoEvent.collect { event ->
            when (event) {
                OnChooseFromAlbumClick -> {
                    selectPhotoFromAlbumContract.launch(arrayOf("image/*"))
                }

                OnChooseFromInternetClick -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                    context.startActivity(intent)
                }

                OnTakePhotoClick -> {
                    val photoFile =
                        File(context.filesDir, "IMG_${System.currentTimeMillis()}.JPG")
                    val photoUri =
                        FileProvider.getUriForFile(
                            context,
                            "com.eltescode.warhbook_custom_file_provider",
                            photoFile
                        )
                    viewModel.onEvent(UserDataScreenEvent.OnFileUriCreated(photoUri))
                    takePhotoContract.launch(photoUri)
                }

                UserDataScreenEvent.PhotoDialogEvents.OnChooseFromFlickr -> {

                }
            }
        }
    }
    UserDataScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun UserDataScreen(state: UserScreenState, onEvent: (UserDataScreenEvent) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        SignOutIcon(
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            onEvent(UserDataScreenEvent.OnSignOut)
        }

        SettingsIcon(
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            onEvent(UserDataScreenEvent.OnSettingsClick)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserPicture(
                userPhoto = state.userData?.photo ?: "",
                userName = state.userData?.name ?: "",
                userSurname = state.userData?.surname ?: "",
                onClick = {
                    onEvent(UserDataScreenEvent.OnPhotoClick)
                }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, bottom = 35.dp, start = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                UserScreens.values().forEach {
                    item {
                        BaseCard(
                            text = it.screenName,
                            modifier = Modifier
                                .size(175.dp)
                                .padding(3.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {},
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        if (state.isSettingsDialogVisible && state.userData != null) {
            SettingsDialog(
                userEmail = state.userData.email,
                userName = state.userData.name,
                userSurname = state.userData.surname,
                onEvent = onEvent,
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.White),
                onDialogDismiss = {
                    onEvent(UserDataScreenEvent.OnSettingsDialogDismiss)
                })
        }

        if (state.isChoosePhotoDialogVisible && state.userData != null) {
            PhotoChooserDialog(
                onEvent = onEvent,
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.White),
                onDialogDismiss = {
                    onEvent(UserDataScreenEvent.OnPhotoDialogDismiss)
                })
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UserDataScreen() {
    UserDataScreen(UserScreenState(null)) {}
}




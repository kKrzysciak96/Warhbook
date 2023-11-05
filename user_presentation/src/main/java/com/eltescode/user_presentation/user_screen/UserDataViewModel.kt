package com.eltescode.user_presentation.user_screen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltescode.core_domain.utils.Result
import com.eltescode.core_ui.R
import com.eltescode.core_ui.navigation.Routes
import com.eltescode.core_ui.utils.UiEvent
import com.eltescode.core_ui.utils.UiText
import com.eltescode.user_domain.repository.UserDataRepository
import com.eltescode.user_domain.repository.UserPhotoUrl
import com.eltescode.user_presentation.utils.UriHelper
import com.eltescode.user_presentation.utils.UserDataScreenEvent
import com.eltescode.user_presentation.utils.UserScreenState
import com.eltescode.user_presentation.utils.photoOneTimeWorkRequestBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val userRepository: UserDataRepository,
    val uriHelper: UriHelper,
) : ViewModel() {

    var state by mutableStateOf(UserScreenState(null))
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _photoEvent = Channel<UserDataScreenEvent.PhotoDialogEvents>()
    val photoEvent = _photoEvent.receiveAsFlow()
    var workId: UUID? by mutableStateOf(null)
        private set

    private var job: Job? = null

    init {
        Log.d("InitVm VM", "${uriHelper.oldUri}")
        refreshUserData()

        viewModelScope.launch {
            uriHelper.uriFlow.collect {
                doWork(it)
            }
        }
    }

    private fun doWork(photoUri: Uri?) {
        photoUri?.let { uri ->
            job = null
            job = viewModelScope.launch {
                val request = photoOneTimeWorkRequestBuilder(uri)
                updateWorkId(request.id)
                _photoEvent.send(UserDataScreenEvent.PhotoDialogEvents.OnWorkManagerEnqueue(request))
                Log.d("Cycle VM", "$uri")
            }
        }
    }

    fun onEvent(event: UserDataScreenEvent) {
        when (event) {

            UserDataScreenEvent.OnSignOut -> {
                when (signOut()) {
                    is Result.Error -> {
                        job = null
                        job = viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_basic)))
                        }
                    }

                    Result.Success -> {
                        job = null
                        job = viewModelScope.launch {
                            _uiEvent.send(UiEvent.Success)
                        }
                    }
                }
            }

            is UserDataScreenEvent.OnNextScreenClick -> {
                job = null
                job = viewModelScope.launch {
                    _uiEvent.send(UiEvent.OnNextScreen(event.route))
                }
            }

            UserDataScreenEvent.OnPhotoClick -> {
                state = state.copy(isChoosePhotoDialogVisible = !state.isChoosePhotoDialogVisible)
            }

            UserDataScreenEvent.OnSettingsClick -> {
                state = state.copy(isSettingsDialogVisible = !state.isSettingsDialogVisible)
            }

            UserDataScreenEvent.OnSettingsSave -> {
                job = null
                job = viewModelScope.launch {
                    handleEditUserDataResult(editUserData())
                }
            }

            is UserDataScreenEvent.OnNameEntered -> {
                state = state.copy(userData = state.userData?.copy(name = event.name))
            }

            UserDataScreenEvent.OnSettingsDialogDismiss -> {
                state = state.copy(isSettingsDialogVisible = false)
            }

            is UserDataScreenEvent.OnSurnameEntered -> {
                state = state.copy(userData = state.userData?.copy(surname = event.surname))
            }

            UserDataScreenEvent.OnPhotoDialogDismiss -> {
                state = state.copy(isChoosePhotoDialogVisible = false)
            }

            is UserDataScreenEvent.OnUploadPhoto -> {
                job = null
                job = viewModelScope.launch {
                    state.userData?.let { userData ->
                        uploadUserPhoto(event.bytes)
                            .onSuccess { photoUrl ->
                                state =
                                    state.copy(userData = userData.copy(photo = photoUrl.toString()))
                                handleEditUserDataResult(editUserData())
                            }
                            .onFailure { throwable ->
                                val message = throwable.message.toString()
                                _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(message)))
                            }
                    }
                }
            }

            is UserDataScreenEvent.OnFileUriCreated -> {
                state = state.copy(photoUri = event.uri)
            }

            UserDataScreenEvent.PhotoDialogEvents.OnChooseFromAlbumClick -> {
                job = null
                job = viewModelScope.launch {
                    _photoEvent.send(event as UserDataScreenEvent.PhotoDialogEvents)
                }
            }

            UserDataScreenEvent.PhotoDialogEvents.OnChooseFromInternetClick -> {
                job = null
                job = viewModelScope.launch {
                    _photoEvent.send(event as UserDataScreenEvent.PhotoDialogEvents)
                }
            }

            UserDataScreenEvent.PhotoDialogEvents.OnTakePhotoClick -> {
                job = null
                job = viewModelScope.launch {
                    _photoEvent.send(event as UserDataScreenEvent.PhotoDialogEvents)
                }
            }

            UserDataScreenEvent.PhotoDialogEvents.OnChooseFromFlickr -> {
                state = state.copy(isChoosePhotoDialogVisible = false)
                job = null
                job = viewModelScope.launch {
                    _uiEvent.send(UiEvent.OnNextScreen(Routes.SEARCH_PHOTO))
                }
            }

            is UserDataScreenEvent.PhotoDialogEvents.OnWorkManagerEnqueue -> {
                job = null
                job = viewModelScope.launch {
                    _photoEvent.send(event)
                }
            }
        }
    }

    fun updateWorkId(uuid: UUID?) {
        workId = uuid
    }

    fun resetUri() {
        state = state.copy(photoUri = null)
    }

    private fun refreshUserData() {
        job = null
        job = viewModelScope.launch {
            userRepository.getUserData()
                .onSuccess {
                    state = state.copy(userData = it)
                }
                .onFailure { throwable ->
                    val message = throwable.message.toString()
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(message)))
                }
        }
    }

    private suspend fun uploadUserPhoto(bytes: ByteArray): kotlin.Result<UserPhotoUrl> {
        return userRepository.uploadUserPhoto(bytes)
    }


    private suspend fun editUserData(): Result? {
        return state.userData?.let {
            userRepository.editUserPersonalData(newUserData = it)
        }
    }

    private suspend fun handleEditUserDataResult(result: Result?) {
        when (result) {
            is Result.Error -> {
                val message = result.message.toString()
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(message)))
            }

            Result.Success -> {
                refreshUserData()
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.update_success)))
            }

            null -> {
                _uiEvent
                    .send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_basic)))
            }
        }
    }

    private fun signOut(): Result {
        return userRepository.signOut()
    }
}
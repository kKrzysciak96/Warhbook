package com.eltescode.photo_presentation.search_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltescode.core_ui.navigation.Routes
import com.eltescode.core_ui.utils.UiEvent
import com.eltescode.core_ui.utils.UiText
import com.eltescode.photo_domain.use_cases.SearchPhotosUseCase
import com.eltescode.photo_presentation.utils.SearchPhotoEvent
import com.eltescode.photo_presentation.utils.SearchPhotoScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPhotoViewModel @Inject constructor(private val searchPhotosUseCase: SearchPhotosUseCase) :
    ViewModel() {

    var state by mutableStateOf(SearchPhotoScreenState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var job: Job? = null

    fun onEvent(event: SearchPhotoEvent) {
        when (event) {
            is SearchPhotoEvent.OnPhotoSearch -> {
                state = state.copy(isSearching = true)
                job = null
                job = viewModelScope.launch {
                    searchPhotosUseCase(state.query)
                        .onSuccess {
                            state = state.copy(photos = it, isSearching = false)
                        }
                        .onFailure {
                            state = state.copy(isSearching = false)
                            _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(it.message.toString())))
                        }
                }
            }

            is SearchPhotoEvent.OnQueryEntered -> {
                state = state.copy(query = event.query)
            }

            is SearchPhotoEvent.OnSetProfilePhoto -> {
                job = null
                job = viewModelScope.launch {
                    val url = state.photoDialogState.photoToSetProfilePicture.replace(
                        oldChar = '/',
                        newChar = '|'
                    )

                    _uiEvent.send(UiEvent.OnNextScreen(Routes.USER_PROFILE + "?photo_url=$url"))
                }
            }

            is SearchPhotoEvent.OnPhotoClick -> {
                state = state.copy(
                    photoDialogState = state.photoDialogState.copy(
                        isDialogVisible = true,
                        photoToSetProfilePicture = event.photoUrl
                    )
                )
            }

            SearchPhotoEvent.OnPhotoDialogDismiss -> {
                state = state.copy(
                    photoDialogState = state.photoDialogState.copy(isDialogVisible = false)
                )
            }
        }
    }
}
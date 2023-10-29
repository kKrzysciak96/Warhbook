package com.eltescode.user_presentation.user_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltescode.core_domain.utils.Result
import com.eltescode.core_ui.R
import com.eltescode.core_ui.utils.UiEvent
import com.eltescode.core_ui.utils.UiText
import com.eltescode.user_domain.repository.UserDataRepository
import com.eltescode.user_presentation.utils.UserDataScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewModel @Inject constructor(
    private val authRepository: UserDataRepository
) : ViewModel() {

    var currentUser by mutableStateOf(authRepository.getCurrentUser())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var job: Job? = null
    fun onEvent(event: UserDataScreenEvent) {
        when (event) {
            UserDataScreenEvent.OnSignOut -> {
                val result = signOut()
                when (result) {
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
        }
    }

    private fun signOut(): Result {
        return authRepository.signOut()
    }
}
package com.eltescode.auth_presentation.sign_in_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltescode.auth_domain.repository.AuthRepository
import com.eltescode.auth_domain.utils.EmailAndPasswordCredentials
import com.eltescode.auth_presentation.utils.SignInScreenEvent
import com.eltescode.auth_presentation.utils.SignInScreenState
import com.eltescode.core_domain.utils.Result
import com.eltescode.core_ui.utils.UiEvent
import com.eltescode.core_ui.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val currentUser = authRepository.getCurrentUser()

    var state by mutableStateOf(SignInScreenState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var job: Job? = null

    fun onEvent(event: SignInScreenEvent) {
        when (event) {
            SignInScreenEvent.OnGoToSignUpScreenClick -> {
                job = null
                job = viewModelScope.launch {
                    _uiEvent.send(UiEvent.OnNextScreen)
                }
            }

            is SignInScreenEvent.OnEmailEntered -> {
                state = state.copy(email = event.email)
            }

            is SignInScreenEvent.OnPasswordEntered -> {
                state = state.copy(password = event.password)
            }

            SignInScreenEvent.OnShowPasswordClick -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            SignInScreenEvent.OnSignInClick -> {
                job = null
                job = viewModelScope.launch {
                    signIn()
                }
            }

            SignInScreenEvent.UserAlreadySignedIn -> {
                job = null
                job = viewModelScope.launch {
                    _uiEvent.send(UiEvent.Success)
                }
            }

            SignInScreenEvent.AdminOnSignInClick -> {
                job = null
                job = viewModelScope.launch {
                    signInAdmin()
                }
            }
        }
    }

    private suspend fun signIn() {
        val credentials = EmailAndPasswordCredentials(state.email, state.password)
        val result = authRepository.signIn(credentials)

        when (result) {
            is Result.Error -> {
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(result.message.toString())))
            }

            Result.Success -> {
                _uiEvent.send(UiEvent.Success)
            }
        }
    }

    private suspend fun signInAdmin() {
        val credentials = EmailAndPasswordCredentials("admin1@adres.pl", "Qwertyu")
        val result = authRepository.signIn(credentials)

        when (result) {
            is Result.Error -> {
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.DynamicString(result.message.toString())))
            }

            Result.Success -> {
                _uiEvent.send(UiEvent.Success)
            }
        }
    }
}
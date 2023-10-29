package com.eltescode.auth_presentation.sign_up_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltescode.auth_domain.repository.AuthRepository
import com.eltescode.auth_domain.use_cases.PasswordResult
import com.eltescode.auth_domain.use_cases.PasswordsValidator
import com.eltescode.auth_domain.utils.EmailAndPasswordCredentials
import com.eltescode.auth_presentation.utils.SignUpScreenEvent
import com.eltescode.auth_presentation.utils.SignUpScreenState
import com.eltescode.core_ui.R
import com.eltescode.core_ui.utils.UiEvent
import com.eltescode.core_ui.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val passwordsValidator: PasswordsValidator
) :
    ViewModel() {
    var state by mutableStateOf(SignUpScreenState())
        private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var job: Job? = null
    fun onEvent(event: SignUpScreenEvent) {
        when (event) {
            is SignUpScreenEvent.OnEmailEntered -> {
                state = state.copy(email = event.email)
            }

            is SignUpScreenEvent.OnPasswordEntered -> {
                state = state.copy(password = event.password)
            }

            is SignUpScreenEvent.OnRepeatedPasswordEntered -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }

            SignUpScreenEvent.OnShowPasswordClick -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            is SignUpScreenEvent.OnSignUpClick -> {
                job = null
                val result = passwordsValidator(state.password, state.repeatedPassword)

                when (result) {
                    PasswordResult.BlankPassword -> {
                        job = null
                        job = viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.blank_password)))
                        }
                    }

                    PasswordResult.DifferentPasswords -> {
                        job = null
                        job = viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.different_passwords)))
                        }
                    }

                    PasswordResult.Success -> {
                        val credentials = EmailAndPasswordCredentials(
                            email = state.email,
                            password = state.password
                        )
                        job = viewModelScope.launch {
                            authRepository.signUp(credentials)
                            _uiEvent.send(UiEvent.Success)
                        }
                    }
                }
            }
        }
    }
}
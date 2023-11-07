package com.eltescode.notes_presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltescode.core_ui.R
import com.eltescode.core_ui.utils.UiText
import com.eltescode.notes_domain.model.Note
import com.eltescode.notes_domain.repository.Result
import com.eltescode.notes_domain.use_cases.NoteUseCases
import com.eltescode.notes_domain.utils.InvalidNoteException
import com.eltescode.notes_presentation.mappers.mapToNote
import com.eltescode.notes_presentation.model.NoteDisplayable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = UiText.StringResource(R.string.enter_title_hint)
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = UiText.StringResource(R.string.enter_content_hint)
        )
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableIntStateOf(NoteDisplayable.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow

    private var currentNoteId: UUID = UUID.randomUUID()

    init {
        savedStateHandle.get<String>("noteId")?.let { noteId ->
            if (noteId != "-1") {
                viewModelScope.launch {
                    noteUseCases.getNoteUseCase(UUID.fromString(noteId))?.also { note: Note ->
                        currentNoteId = UUID.fromString(note.noteId)
                        _noteTitle.value =
                            noteTitle.value.copy(
                                text = note.title,
                                isHintVisible = note.title.isBlank()
                            )
                        _noteContent.value =
                            noteContent.value.copy(
                                text = note.content,
                                isHintVisible = note.content.isBlank()
                            )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value =
                    noteTitle.value.copy(
                        isHintVisible = !event.focusState.isFocused &&
                                noteTitle.value.text.isBlank()
                    )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(text = event.value)
            }

            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(text = event.value)
            }

            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        val result = noteUseCases.addNoteUseCase(
                            NoteDisplayable(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                noteId = currentNoteId,
                            ).mapToNote()
                        )
                        when (result) {
                            is Result.Error -> {
                                throw Exception(result.message)
                            }

                            Result.SuccessLocal -> {
                                _eventFlow.emit(UiEvent.SaveNote)
                            }

                            Result.SuccessRemote -> {
                                _eventFlow.emit(UiEvent.SaveNote)
                            }
                        }

                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(e.message.toString()))
                    }
                }
            }
        }
    }
}
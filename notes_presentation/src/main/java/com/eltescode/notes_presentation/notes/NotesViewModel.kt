package com.eltescode.notes_presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltescode.core_ui.R
import com.eltescode.core_ui.utils.UiEvent
import com.eltescode.core_ui.utils.UiText
import com.eltescode.notes_domain.model.Note
import com.eltescode.notes_domain.repository.Result
import com.eltescode.notes_domain.use_cases.NoteUseCases
import com.eltescode.notes_domain.utils.NoteOrder
import com.eltescode.notes_presentation.mappers.mapToNote
import com.eltescode.notes_presentation.mappers.mapToNoteDisplayable
import com.eltescode.notes_presentation.model.NoteDisplayable
import com.eltescode.notes_presentation.util.NoteScreen
import com.eltescode.notes_presentation.util.NotesEvent
import com.eltescode.notes_presentation.util.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel() {


    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state
    private var recentlyDeletedNote: NoteDisplayable? = null

    private var getNotesJob: Job? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var job: Job? = null


    init {
        viewModelScope.launch {
            if (noteUseCases.checkSyncNeedUseCase()) {
                syncData()
                getNotes()
            } else {
                getNotes()
            }
        }
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                job = null
                job =
                    viewModelScope.launch {
                        try {
                            val result = noteUseCases.deleteNoteUseCase(event.note.mapToNote())
                            when (result) {
                                is Result.Error -> {
                                    throw Exception(result.message)
                                }

                                Result.SuccessLocal -> {
                                    recentlyDeletedNote = event.note
                                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.note_deleted)))
                                    getNotes()
                                }

                                Result.SuccessRemote -> {
                                    recentlyDeletedNote = event.note
                                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.note_deleted)))
                                    getNotes()
                                }

                            }
                        } catch (e: Exception) {
                            _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_basic)))
                        }
                    }
            }

            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                _state.value = _state.value.copy(noteOrder = event.noteOrder)
                sortNotes(state.value.notes.map { it.mapToNote() }, event.noteOrder)
            }

            is NotesEvent.RestoreNote -> {
                job = null
                job = viewModelScope.launch {
                    noteUseCases.addNoteUseCase(recentlyDeletedNote?.mapToNote() ?: return@launch)
                    recentlyDeletedNote = null
                    getNotes()
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value =
                    state.value.copy(isOrderSectionVisible = !state.value.isOrderSectionVisible)
            }

            is NotesEvent.OnAddEditNote -> {
                job = null
                job = viewModelScope.launch {
                    _uiEvent.send(
                        if (event.id != null) {
                            UiEvent.OnNextScreen(
                                NoteScreen.AddEditNoteNoteScreen.route +
                                        "?noteId=${event.id}&noteColor=${event.color}"
                            )
                        } else {
                            UiEvent.OnNextScreen(
                                NoteScreen.AddEditNoteNoteScreen.route
                            )
                        }
                    )
                }
            }
        }
    }

    private suspend fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase().onEach { notes ->
            sortNotes(notes, state.value.noteOrder)
        }.launchIn(viewModelScope)
    }

    private fun sortNotes(notes: List<Note>, noteOrder: NoteOrder) {
        val sortedNotes =
            noteUseCases.sortNotesUseCase(notes, noteOrder).map { it.mapToNoteDisplayable() }
        _state.value = state.value.copy(
            notes = sortedNotes,
        )
    }

    private suspend fun syncData() {
        noteUseCases.syncDataUseCase()
    }
}
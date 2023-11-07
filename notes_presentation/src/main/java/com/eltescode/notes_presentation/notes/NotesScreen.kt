package com.eltescode.notes_presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eltescode.core_ui.R
import com.eltescode.core_ui.components.silverBackgroundBrush
import com.eltescode.core_ui.ui.fontFamily_croissant
import com.eltescode.core_ui.utils.UiEvent
import com.eltescode.notes_presentation.notes.comopnents.NoteItem
import com.eltescode.notes_presentation.notes.comopnents.OrderSection
import com.eltescode.notes_presentation.util.NotesEvent
import com.eltescode.notes_presentation.util.NotesState
import kotlinx.coroutines.launch


@Composable
fun NotesScreen(
    snackBarHostState: SnackbarHostState,
    viewModel: NotesViewModel = hiltViewModel(),
    onNextScreen: (String?) -> Unit,
) {
    val state = viewModel.state.value

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.OnNextScreen -> {
                    onNextScreen(event.route)
                }

                is UiEvent.ShowSnackBar -> {
                    scope.launch {
                        snackBarHostState.currentSnackbarData?.dismiss()

                        val result = snackBarHostState.showSnackbar(
                            message = event.message.asString(context),
                            actionLabel = context.getString(R.string.undo),
                            duration = SnackbarDuration.Long
                        )

                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(NotesEvent.RestoreNote)
                        }
                    }
                }

                else -> Unit
            }
        }
    }

    NotesScreen(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(state: NotesState, onEvent: (NotesEvent) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(silverBackgroundBrush()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.your_notes),
                    fontSize = 24.sp,
                    fontFamily = fontFamily_croissant
                )
                IconButton(
                    onClick = { onEvent(NotesEvent.ToggleOrderSection) },
                    content = { Icon(imageVector = Icons.Default.Sort, contentDescription = null) })
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier.fillMaxWidth(),
                    noteOrder = state.noteOrder,
                    onOrderChange = { onEvent(NotesEvent.Order(it)) })
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize(), content = {
                items(items = state.notes, key = { item -> item.noteId }) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .animateItemPlacement()
                            .fillMaxWidth()
                            .clickable {
                                onEvent(
                                    NotesEvent.OnAddEditNote(
                                        id = note.noteId,
                                        color = note.color
                                    )
                                )
                            },
                        onDeleteClick = {
                            onEvent(NotesEvent.DeleteNote(note))
                        })
                }
            })
        }
        FloatingActionButton(
            onClick = {
                onEvent(NotesEvent.OnAddEditNote(null, null))
            },
            containerColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            content = { Icon(imageVector = Icons.Default.Add, contentDescription = null) })
    }
}

@Preview
@Composable
private fun NotesScreen() {
    NotesScreen(
        state = NotesState(isOrderSectionVisible = true),
    ) {}
}
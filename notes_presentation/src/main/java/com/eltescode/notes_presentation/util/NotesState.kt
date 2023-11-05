package com.eltescode.notes_presentation.util

import com.eltescode.notes_domain.utils.NoteOrder
import com.eltescode.notes_domain.utils.OrderType
import com.eltescode.notes_presentation.model.NoteDisplayable

data class NotesState(
    val notes: List<NoteDisplayable> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)

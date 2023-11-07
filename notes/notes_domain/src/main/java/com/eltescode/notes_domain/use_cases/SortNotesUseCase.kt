package com.eltescode.notes_domain.use_cases

import com.eltescode.notes_domain.model.Note
import com.eltescode.notes_domain.utils.NoteOrder
import com.eltescode.notes_domain.utils.OrderType


class SortNotesUseCase() {

    operator fun invoke(
        notes: List<Note>,
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): List<Note> {
        return when (noteOrder.orderType) {
            is OrderType.Descending -> {
                when (noteOrder) {
                    is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                    is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                    is NoteOrder.Color -> notes.sortedByDescending { it.color }
                }
            }

            is OrderType.Ascending -> {
                when (noteOrder) {
                    is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                    is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                    is NoteOrder.Color -> notes.sortedBy { it.color }
                }
            }
        }

    }
}
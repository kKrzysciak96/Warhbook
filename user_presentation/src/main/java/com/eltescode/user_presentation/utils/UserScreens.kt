package com.eltescode.user_presentation.utils

import com.eltescode.core_ui.R
import com.eltescode.core_ui.navigation.Routes

enum class UserScreens(val route: String, val screenNameRes: Int) {
    NotesScreen(Routes.NOTES, R.string.notes_screen_name),
    YourSheetsScreen(Routes.YOUR_SHEETS, R.string.your_sheets_screen_name),
    FavouriteProfessionsScreen(
        Routes.FAVOURITE_PROFESSIONS,
        R.string.favourite_professions_screen_name
    ),
    FavouriteMagicScreen(Routes.FAVOURITE_MAGIC, R.string.favourite_magic_screen_name),
    CareerCreatorScreen(Routes.PROFESSION_CREATOR, R.string.profession_creator_screen_name),
    CosikScreen(Routes.ADDITIONAL, R.string.something_screen_name),
    NewCharacterScreen(Routes.NEW_CHARACTER, R.string.new_character_screen_name)
}
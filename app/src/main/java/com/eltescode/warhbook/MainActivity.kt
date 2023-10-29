package com.eltescode.warhbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import com.eltescode.auth_presentation.sign_up_screen.SignUpScreen
import com.eltescode.warhbook.ui.theme.WarhbookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            WarhbookTheme {
                SignUpScreen(snackBarHostState = snackBarHostState) {}
            }
        }
    }
}

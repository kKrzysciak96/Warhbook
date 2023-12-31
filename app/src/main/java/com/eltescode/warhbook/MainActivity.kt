package com.eltescode.warhbook

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.WorkManager
import com.eltescode.auth_presentation.sign_in_screen.SignInScreen
import com.eltescode.auth_presentation.sign_up_screen.SignUpScreen
import com.eltescode.core_ui.navigation.Routes
import com.eltescode.notes_presentation.add_edit_note.AddEditNoteScreen
import com.eltescode.notes_presentation.notes.NotesScreen
import com.eltescode.photo_presentation.search_screen.SearchPhotoScreen
import com.eltescode.user_presentation.user_screen.UserDataScreen
import com.eltescode.user_presentation.utils.UriHelper
import com.eltescode.warhbook.splash_screen.SplashScreen
import com.eltescode.warhbook.ui.theme.WarhbookTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var workManager: WorkManager

    @Inject
    lateinit var uriHelper: UriHelper

    private var job: Job? = null

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val snackBarHostState = remember { SnackbarHostState() }
            val navController = rememberNavController()
            val scope = rememberCoroutineScope()
            WarhbookTheme {
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        NavHost(
                            navController = navController,
                            startDestination = Routes.SIGN_IN
                        )
                        {

                            composable(route = Routes.SPLASH_SCREEN) {
                                SplashScreen {
                                    navController.popBackStack()
                                    navController.navigate(Routes.SIGN_IN)
                                }
                            }
                            composable(route = Routes.SIGN_IN) {
                                SignInScreen(
                                    snackBarHostState = snackBarHostState,
                                    onNextScreen = { navController.navigate(Routes.SIGN_UP) },
                                    onSuccess = {
                                        navController.popBackStack()
                                        navController.navigate(Routes.USER_PROFILE)
                                    })
                            }
                            composable(route = Routes.SIGN_UP) {
                                SignUpScreen(
                                    snackBarHostState = snackBarHostState,
                                    onSuccess = {
                                        navController.popBackStack(Routes.SIGN_IN, true)
                                        navController.navigate(Routes.USER_PROFILE)
                                    })
                            }
                            composable(
                                route = Routes.USER_PROFILE + "?photo_url={photo_url}",
                                arguments = listOf(navArgument("photo_url") {
                                    type = NavType.StringType
                                    nullable = true
                                }
                                )
                            ) {
                                createUriFromNavArguments(it)
                                Log.d("CYCLE main compose", "${uriHelper.oldUri}")
                                UserDataScreen(
                                    workManager = workManager,
                                    onSuccess = {
                                        navController.popBackStack()
                                        navController.navigate(route = Routes.SIGN_IN)
                                    },
                                    onNextScreen = { route ->
                                        navController.navigate(route)
                                    }
                                )
                            }
                            composable(route = Routes.SEARCH_PHOTO) {
                                SearchPhotoScreen { route ->
                                    navController.popBackStack()
                                    navController.navigate(route)
                                }
                            }
                            composable(route = Routes.NOTES) {
                                NotesScreen(snackBarHostState = snackBarHostState) {
                                    navController.navigate(it ?: "")
                                }
                            }
                            composable(
                                route = Routes.ADD_EDIT_NOTE +
                                        "?noteId={noteId}&noteColor={noteColor}",
                                arguments = listOf(
                                    navArgument(name = "noteId") {
                                        type = NavType.StringType
                                        defaultValue = "-1"
                                    },
                                    navArgument(name = "noteColor") {
                                        type = NavType.IntType
                                        defaultValue = -1
                                    })
                            ) {
                                val color = it.arguments?.getInt("noteColor") ?: -1
                                AddEditNoteScreen(
                                    snackBarHostState = snackBarHostState,
                                    noteColor = color
                                ) {
                                    navController.popBackStack(Routes.NOTES, true)
                                    navController.navigate(Routes.NOTES)
                                }
                            }
                            composable(route = Routes.YOUR_SHEETS) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Not implemented yet")
                                }
                            }
                            composable(route = Routes.FAVOURITE_PROFESSIONS) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Not implemented yet")
                                }
                            }
                            composable(route = Routes.FAVOURITE_MAGIC) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Not implemented yet")
                                }
                            }
                            composable(route = Routes.PROFESSION_CREATOR) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Not implemented yet")
                                }
                            }
                            composable(route = Routes.ADDITIONAL) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Not implemented yet")
                                }
                            }
                            composable(route = Routes.NEW_CHARACTER) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("Not implemented yet")
                                }
                            }
                        }
                    })
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val new = intent?.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
        job = null
        job = CoroutineScope(Job()).launch {
            uriHelper.uriFlow.emit(new)
        }
    }

    private fun createUriFromNavArguments(navBackStackEntry: NavBackStackEntry) {
        val decodedUrl = navBackStackEntry.arguments?.getString("photo_url")
            ?.replace(oldChar = '|', newChar = '/')
        decodedUrl?.let { url ->
            val newUri = Uri.parse(url)
            if (uriHelper.oldUri != newUri) {
                uriHelper.oldUri = newUri
                job = null
                job = CoroutineScope(Job()).launch {
                    uriHelper.uriFlow.emit(Uri.parse(url))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job = null
    }
}

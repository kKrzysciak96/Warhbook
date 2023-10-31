package com.eltescode.warhbook

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.WorkManager
import com.eltescode.auth_presentation.sign_in_screen.SignInScreen
import com.eltescode.auth_presentation.sign_up_screen.SignUpScreen
import com.eltescode.core_ui.navigation.Routes
import com.eltescode.user_presentation.user_screen.UserDataScreen
import com.eltescode.user_presentation.utils.UriHelper
import com.eltescode.warhbook.ui.theme.WarhbookTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    private lateinit var workManager: WorkManager
    @Inject
    lateinit var uriHelper: UriHelper

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workManager = WorkManager.getInstance(applicationContext)

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
                            composable(route = Routes.USER_PROFILE) {
                                UserDataScreen(
                                    photoUriHelper = uriHelper,
                                    workManager = workManager,
                                    onSuccess = {
                                        navController.popBackStack()
                                        navController.navigate(route = Routes.SIGN_IN)
                                    },
                                    onNextScreen = {}
                                )
                            }
                        }
                    })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val new = intent?.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
        if (uriHelper.photoUri == null && uriHelper.oldUri != new) {
            uriHelper.photoUri = new
            uriHelper.oldUri = new
        }
    }

}

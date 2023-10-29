package com.eltescode.warhbook

import android.annotation.SuppressLint
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
import com.eltescode.auth_presentation.sign_in_screen.SignInScreen
import com.eltescode.auth_presentation.sign_up_screen.SignUpScreen
import com.eltescode.user_presentation.user_screen.UserDataScreen
import com.eltescode.warhbook.navigation.Routes
import com.eltescode.warhbook.ui.theme.WarhbookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
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
                                UserDataScreen(onSuccess = {
                                    navController.popBackStack()
                                    navController.navigate(route = Routes.SIGN_IN)
                                })
                            }
                        }
                    })
            }
        }
    }
}

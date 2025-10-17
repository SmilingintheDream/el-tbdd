package com.example.bttuan4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bttuan4.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

@Composable
fun App() {
    AppTheme {
        val nav = rememberNavController()
        NavHost(navController = nav, startDestination = "forgot") {
            composable("forgot") {
                ForgotScreen(
                    onNext = { email ->
                        nav.navigate("verify?email=${email}")
                    }
                )
            }
            composable(
                "verify?email={email}",
                arguments = listOf(navArgument("email") { type = NavType.StringType; defaultValue = "" })
            ) { backStack ->
                val email = backStack.arguments?.getString("email") ?: ""
                VerifyScreen(
                    email = email,
                    onBack = { nav.navigateUp() },
                    onNext = { code ->
                        nav.navigate("reset?email=${email}&code=${code}")
                    }
                )
            }
            composable(
                "reset?email={email}&code={code}",
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType; defaultValue = "" },
                    navArgument("code") { type = NavType.StringType; defaultValue = "" }
                )
            ) { backStack ->
                val email = backStack.arguments?.getString("email") ?: ""
                val code = backStack.arguments?.getString("code") ?: ""
                ResetPasswordScreen(
                    onBack = { nav.navigateUp() },
                    onNext = { pwd ->
                        nav.navigate("confirm?email=${email}&code=${code}&pwd=${pwd}")
                    }
                )
            }
            composable(
                "confirm?email={email}&code={code}&pwd={pwd}",
                arguments = listOf(
                    navArgument("email") { type = NavType.StringType; defaultValue = "" },
                    navArgument("code") { type = NavType.StringType; defaultValue = "" },
                    navArgument("pwd") { type = NavType.StringType; defaultValue = "" }
                )
            ) { backStack ->
                ConfirmScreen(
                    email = backStack.arguments?.getString("email") ?: "",
                    code = backStack.arguments?.getString("code") ?: "",
                    password = backStack.arguments?.getString("pwd") ?: "",
                    onFinish = { nav.popBackStack("forgot", inclusive = false) }
                )
            }
        }
    }
}

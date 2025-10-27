package com.example.lienketfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {

    // Khởi tạo ViewModel
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation(profileViewModel)
        }
    }
}

@Composable
fun AppNavigation(profileViewModel: ProfileViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(navController = navController)
        }

        // Sửa đổi composable cho "profile" để truyền vào ViewModel
        composable("profile") {
            ProfileScreen(
                navController = navController,
                profileViewModel = profileViewModel
            )
        }
    }
}

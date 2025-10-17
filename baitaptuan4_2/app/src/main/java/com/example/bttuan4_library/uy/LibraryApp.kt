package com.example.bttuan4_library.uy

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bttuan4_library.uy.screens.BooksScreen
import com.example.bttuan4_library.uy.screens.ManagerScreen
import com.example.bttuan4_library.uy.screens.StudentsScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person

enum class Dest(val route: String, val label: String, val icon: @Composable () -> Unit) {
    Manager("manager", "Quản lý", { Icon(Icons.Default.Home, null) }),
    Books("books", "DS Sách", { Icon(Icons.Default.MenuBook, null) }),
    Students("students", "Sinh viên", { Icon(Icons.Default.Person, null) })
}

@Composable
fun LibraryApp(vm: LibraryViewModel = viewModel()) {
    val nav = rememberNavController()
    val items = remember { listOf(Dest.Manager, Dest.Books, Dest.Students) }
    val current = nav.currentBackStackEntryAsState().value?.destination?.route ?: Dest.Manager.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { d ->
                    NavigationBarItem(
                        selected = current == d.route,
                        onClick = {
                            nav.navigate(d.route) {
                                popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = d.icon,
                        label = { Text(d.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Dest.Manager.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Dest.Manager.route) { ManagerScreen(vm) }
            composable(Dest.Books.route) { BooksScreen(vm) }
            composable(Dest.Students.route) { StudentsScreen(vm) }
        }
    }
}

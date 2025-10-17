package com.example.bttuan4_library.uy.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bttuan4_library.uy.LibraryViewModel

@Composable
fun BooksScreen(vm: LibraryViewModel) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Danh sách sách", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        vm.books.forEach { b ->
            val checked = b.id in vm.selectedStudent.borrowedBookIds
            Card(
                Modifier.fillMaxWidth().padding(vertical = 6.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = checked, onCheckedChange = {
                        vm.toggleBorrow(b.id, it == true)
                    })
                    Spacer(Modifier.width(8.dp))
                    Text(b.title, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

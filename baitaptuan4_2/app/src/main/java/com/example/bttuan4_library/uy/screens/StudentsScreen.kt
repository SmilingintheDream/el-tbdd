package com.example.bttuan4_library.uy.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bttuan4_library.uy.LibraryViewModel

@Composable
fun StudentsScreen(vm: LibraryViewModel) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Danh sách sinh viên", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        vm.students.forEachIndexed { index, s ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { vm.selectStudent(index) },
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(s.name, style = MaterialTheme.typography.titleMedium)
                    val count = s.borrowedBookIds.size
                    Text("$count quyển đang mượn", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

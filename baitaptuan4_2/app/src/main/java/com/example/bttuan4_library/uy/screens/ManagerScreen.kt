package com.example.bttuan4_library.uy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bttuan4_library.uy.LibraryViewModel
import androidx.compose.foundation.text.KeyboardOptions // <- nhớ import đúng package theo lưu ý của bạn

@Composable
fun ManagerScreen(
    vm: LibraryViewModel,
    onGoBooks: () -> Unit = {}
) {
    var nameField by remember { mutableStateOf(vm.selectedStudent.name) }
    LaunchedEffect(vm.selectedStudentIndex) { nameField = vm.selectedStudent.name }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hệ thống\nQuản lý Thư viện", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        // Sinh viên + Thay đổi
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = nameField,
                onValueChange = { nameField = it },
                modifier = Modifier.weight(1f),
                label = { Text("Sinh viên") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                vm.changeStudentByName(nameField)
                nameField = vm.selectedStudent.name
            }) { Text("Thay đổi") }
        }

        Spacer(Modifier.height(20.dp))
        Text("Danh sách sách", style = MaterialTheme.typography.titleMedium)

        val borrowed = vm.selectedStudent.borrowedBookIds
        if (borrowed.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color(0xFFEFF2F7), RoundedCornerShape(12.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Bạn chưa mượn quyển sách nào\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF6F7FB), RoundedCornerShape(12.dp))
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                vm.books.filter { it.id in borrowed }.forEach { b ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = true,
                            onCheckedChange = { vm.toggleBorrow(b.id, it == true) }
                        )
                        OutlinedTextField(
                            value = b.title,
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                val ok = vm.addFirstAvailableBook()  // (đã fix recomposition trước đó)
                // Dù ok hay không, điều hướng sang DS Sách theo yêu cầu
                onGoBooks()
            },
            modifier = Modifier.fillMaxWidth(0.6f).height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) { Text("Thêm") }
    }
}

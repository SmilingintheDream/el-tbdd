package com.example.bttuan4

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpBoxes(
    length: Int = 6,
    onCompleted: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Mỗi ô là 1 ký tự, auto-focus sang ô tiếp theo
    val values = remember { mutableStateListOf(*Array(length) { "" }) }
    val focusers = remember { Array(length) { FocusRequester() } }

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        for (i in 0 until length) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .border(1.5.dp, Color(0xFFDFE6E9), RoundedCornerShape(8.dp))
                    .background(Color(0xFFF8F9FA), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = values[i],
                    onValueChange = { str ->
                        // Chỉ nhận số, chỉ 1 ký tự
                        val filtered = str.filter { it.isDigit() }.take(1)
                        values[i] = filtered
                        // nếu có ký tự -> focus next
                        if (filtered.isNotEmpty() && i < length - 1) {
                            focusers[i + 1].requestFocus()
                        }
                        // nếu đủ độ dài -> callback
                        val code = values.joinToString("")
                        if (code.length == length) onCompleted(code)
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    ),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusers[i]),
                    decorationBox = { inner ->
                        // 👉 Canh GIỮA theo cả 2 trục cho nội dung TextField
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (values[i].isEmpty()) {
                                // Placeholder tàng hình để giữ chiều cao
                                Text("0", color = Color.Transparent, fontSize = 18.sp)
                            }
                            inner()
                        }
                    }
                )
            }
        }
    }

    // focus ô đầu tiên khi render
    LaunchedEffect(Unit) { focusers.first().requestFocus() }
}

@Composable
fun VerifyScreen(
    email: String,
    onBack: () -> Unit,
    onNext: (String) -> Unit
) {
    val ctx = LocalContext.current
    var current by remember { mutableStateOf("") }

    androidx.compose.material3.Scaffold(
        topBar = { ScreenTop("Verify Code", onBack) }
    ) { pad ->
        Column(
            modifier = Modifier.padding(pad).padding(24.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLogo()
            Spacer(Modifier.height(8.dp))
            Text("SmartTasks", fontWeight = FontWeight.Bold, color = Color(0xFF2E86DE))
            Spacer(Modifier.height(16.dp))
            Text(
                "Enter the 6-digit code\nwe just sent you on your registered Email:\n$email",
                color = Color.Gray, textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(16.dp))

            OtpBoxes(onCompleted = { code -> current = code })

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    if (current.length < 6) {
                        Toast.makeText(ctx, "Nhập đủ 6 số nhé!", Toast.LENGTH_SHORT).show()
                    } else onNext(current)
                },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) { Text("Next") }
        }
    }
}

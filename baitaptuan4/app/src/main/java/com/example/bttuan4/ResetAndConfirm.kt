package com.example.bttuan4

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext

@Composable
fun ResetPasswordScreen(
    onBack: () -> Unit,
    onNext: (String) -> Unit
) {
    val ctx = LocalContext.current
    var pass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }

    var triedSubmit by remember { mutableStateOf(false) }
    val mismatch = triedSubmit && pass.isNotEmpty() && confirm.isNotEmpty() && pass != confirm

    Scaffold(
        topBar = { ScreenTop("Create new password", onBack) },
        containerColor = MaterialTheme.colorScheme.background
    ) { pad ->
        Box(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val scroll = rememberScrollState()
            Column(
                Modifier
                    .align(Alignment.TopCenter)
                    .widthIn(max = 420.dp)
                    .fillMaxWidth()
                    .verticalScroll(scroll)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppLogo()
                Spacer(Modifier.height(12.dp))
                Text(
                    "Your new password must be different from previously used password",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = pass,
                    onValueChange = {
                        pass = it
                        if (triedSubmit) triedSubmit = false
                    },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = confirm,
                    onValueChange = {
                        confirm = it
                        if (triedSubmit) triedSubmit = false
                    },
                    label = { Text("Confirm Password") },
                    singleLine = true,
                    isError = mismatch, // CHỈ báo đỏ sau khi bấm Next mà không khớp
                    supportingText = {
                        if (mismatch) Text("Mật khẩu không khớp")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        triedSubmit = true
                        when {
                            pass.isBlank() || confirm.isBlank() ->
                                Toast.makeText(ctx, "Vui lòng nhập đủ 2 ô mật khẩu", Toast.LENGTH_SHORT).show()
                            pass != confirm ->
                                Toast.makeText(ctx, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                            else -> onNext(pass) // hợp lệ → sang màn Confirm
                        }
                    },
                    // luôn cho bấm
                    enabled = true,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) { Text("Next") }
            }
        }
    }
}


@Composable
fun ConfirmScreen(
    email: String,
    code: String,
    password: String,
    onFinish: () -> Unit
) {
    Scaffold(topBar = { ScreenTop("Confirm", onBack = null) }) { pad ->
        Column(
            Modifier.padding(pad).padding(24.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLogo()
            Spacer(Modifier.height(16.dp))
            Text("We are here to help you!")
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email, onValueChange = {}, label = { Text("Email") },
                readOnly = true, modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = code, onValueChange = {}, label = { Text("Verify Code") },
                readOnly = true, modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = password, onValueChange = {}, label = { Text("Password") },
                readOnly = true, visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onFinish,
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) { Text("Submit") }
        }
    }
}

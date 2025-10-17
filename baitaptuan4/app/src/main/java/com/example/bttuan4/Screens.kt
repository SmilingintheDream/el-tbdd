package com.example.bttuan4

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast

@Composable
fun AppLogo() {
    Image(
        painter = painterResource(id = R.drawable.uth_logo),
        contentDescription = "UTH",
        modifier = Modifier.size(96.dp)
    )
}

@Composable
fun ScreenTop(title: String, onBack: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        if (onBack != null) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        )
    }
}

@Composable
fun ForgotScreen(onNext: (String) -> Unit) {
    val ctx = LocalContext.current
    var email by remember { mutableStateOf("") }
    var tried by remember { mutableStateOf(false) }

    val emailNormalized = email.trim().lowercase()
    val invalid = tried && !isAllowedEmail(email)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val scroll = rememberScrollState()
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .widthIn(max = 420.dp)
                    .fillMaxWidth()
                    .verticalScroll(scroll)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppLogo()
                Spacer(Modifier.height(8.dp))
                Text("SmartTasks", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E86DE))
                Spacer(Modifier.height(12.dp))
                ScreenTop("Forget Password?")

                Text(
                    "Enter your Email, we will send you a verification code.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (tried) tried = false
                    },
                    singleLine = true,
                    isError = invalid,
                    label = { Text("Your Email") },
                    supportingText = {
                        if (invalid) Text("Vui lòng nhập đúng định dạng Email (ví dụ: ten@gmail.com)")
                    },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        tried = true
                        val normalized = email.trim().lowercase()
                        when {
                            normalized.isBlank() ->
                                Toast.makeText(ctx, "Vui lòng nhập email", Toast.LENGTH_SHORT).show()
                            !isAllowedEmail(normalized) ->
                                Toast.makeText(ctx, "Email không đúng định dạng", Toast.LENGTH_SHORT).show()
                            else -> onNext(normalized)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                )  { Text("Next") }
            }
        }
    }
}

private fun isAllowedEmail(
    raw: String,
    extraDomains: Set<String> = setOf("uth.edu.vn"),
    extraSuffixes: Set<String> = setOf(".uth.edu.vn", ".edu.vn")
): Boolean {
    val email = raw.trim().lowercase()
    if (email.isEmpty()) return false
    if (email.count { it == '@' } != 1) return false
    val parts = email.split("@")
    val local = parts[0]
    val domain = parts[1]
    val localOk = local.matches(Regex("^[a-z0-9._%+-]+$")) &&
            !local.startsWith(".") && !local.endsWith(".") && !local.contains("..")
    if (!localOk) return false
    if (domain == "gmail.com") return true
    if (domain in extraDomains) return true
    if (extraSuffixes.any { domain.endsWith(it) }) return true

    return false
}



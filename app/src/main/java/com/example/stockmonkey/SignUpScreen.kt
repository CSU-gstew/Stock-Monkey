package com.example.stockmonkey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockmonkey.ui.theme.StockMonkeyTheme
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    userDao: UserDao,
    onSignUpSuccess: () -> Unit,
    onGoToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    StockMonkeyTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        0.0f to Color(0xFFB1A1BA),
                        0.8f to Color(0xFFF3F0F4),
                        1.0f to Color(0xFFF3F0F4)
                    )
                )
        ) {
            BgImage()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Create an account to get started.",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username", color = MaterialTheme.colorScheme.secondary, fontFamily = FontFamily.Monospace) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFF9885A8),
                        focusedBorderColor = Color(0xFF852EFF)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = MaterialTheme.colorScheme.secondary, fontFamily = FontFamily.Monospace) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFF9885A8),
                        focusedBorderColor = Color(0xFF852EFF)
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "8-20 characters, at least 1 number and 1 special character",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password", color = MaterialTheme.colorScheme.secondary, fontFamily = FontFamily.Monospace) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFF9885A8),
                        focusedBorderColor = Color(0xFF852EFF)
                    )
                )
                Spacer(modifier = Modifier.height(28.dp))

                val mainGradient = listOf(Color(0xFFA05CFF), Color(0xFF852EFF))
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val error = signUp(userDao, username, password, confirmPassword)
                            if (error == null) {
                                onSignUpSuccess()
                            } else {
                                errorText = error
                                showErrorDialog = true
                            }
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(brush = Brush.verticalGradient(mainGradient)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text("Sign Up", fontSize = 17.sp, fontFamily = FontFamily.Monospace)
                }
                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onGoToLogin) {
                    Text("Already have an account? Log In", fontFamily = FontFamily.Monospace,color = MaterialTheme.colorScheme.primary)
                }
            }

            if (showErrorDialog) {
                AlertDialog(
                    containerColor = Color(0xFFF3F0F4),
                    onDismissRequest = { showErrorDialog = false },
                    title = { Text("Error", fontFamily = FontFamily.Monospace) },
                    text = { Text(errorText, fontFamily = FontFamily.Monospace) },
                    confirmButton = {
                        TextButton(onClick = { showErrorDialog = false }) { Text("OK", fontFamily = FontFamily.Monospace) }
                    }
                )
            }
        }
    }
}
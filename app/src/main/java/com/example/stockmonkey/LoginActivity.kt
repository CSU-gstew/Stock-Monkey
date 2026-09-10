package com.example.stockmonkey

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.stockmonkey.ui.theme.StockMonkeyTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val userDao = UserDatabase.getDatabase(this).userDao()

        setContent {
            StockMonkeyTheme {
                LoginHolder(
                    onLoginAttempt = { username, password, onError ->
                        lifecycleScope.launch {
                            val user = withContext(Dispatchers.IO) {
                                userDao.getUserWUsername(username)
                            }

                            if (user == null) {
                                //Temp Code to add to database in case it didn't work on ur end..... idk how to push da table
                                //To Use -> try to log in and when the app says "User does not exist" it'll create
                                //val testUser = UserItem(0,"TestUser", "1234", listOf<StockTicker>())
                                //userDao.insertAll(testUser)
                                onError("User does not exist", null)
                            } else if (user.password != password) {
                                onError(null, "Incorrect password")
                            } else {
                                Toast.makeText(this@LoginActivity, "Welcome, ${user.username}!", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this@LoginActivity, HomePage::class.java).apply {
                                    putExtra("LOGGED_IN_USER_ID", user.id)
                                    putExtra("LOGGED_IN_USERNAME", user.username)
                                }
                                startActivity(intent)
                                finish()
                            }
                        }
                    },
                    onNavigateToSignUp = {
                        startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun LoginHolder(
    onLoginAttempt: (username: String, password: String, onError: (userErr: String?, passErr: String?) -> Unit) -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center
        ) {
            LoginTitleCard()

            UsernameInput(
                value = username,
                onValueChange = {
                    username = it
                    usernameError = null
                },
                errorMessage = usernameError
            )

            PasswordInput(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                errorMessage = passwordError,
                passwordVisible = passwordVisible,
                onToggleVisibility = { passwordVisible = !passwordVisible }
            )

            LoginSubmitButton(
                onClick = {
                    val trimmedUser = username.trim()
                    val trimmedPass = password.trim()

                    var hasError = false
                    if (trimmedUser.isEmpty()) {
                        usernameError = "Username is required"
                        hasError = true
                    }
                    if (trimmedPass.isEmpty()) {
                        passwordError = "Password is required"
                        hasError = true
                    }

                    if (!hasError) {
                        onLoginAttempt(trimmedUser, trimmedPass) { userErr, passErr ->
                            usernameError = userErr
                            passwordError = passErr
                        }
                    }
                }
            )
        }

        SignUpNavButton(
            onClick = onNavigateToSignUp,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun LoginTitleCard(modifier: Modifier = Modifier) {
    Text(
        text = "Welcome!",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(bottom = 32.dp)
    )
}

@Composable
fun UsernameInput(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Username") },
        singleLine = true,
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}

@Composable
fun PasswordInput(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?,
    passwordVisible: Boolean,
    onToggleVisibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Password") },
        singleLine = true,
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            TextButton(onClick = onToggleVisibility) {
                Text(if (passwordVisible) "Hide" else "Show")
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    )
}

@Composable
fun LoginSubmitButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(text = "Log In", fontSize = 17.sp)
    }
}

@Composable
fun SignUpNavButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = "Create Account", fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    StockMonkeyTheme {
        LoginHolder(
            onLoginAttempt = { _, _, _ -> },
            onNavigateToSignUp = {}
        )
    }
}
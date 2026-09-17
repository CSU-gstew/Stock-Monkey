package com.example.stockmonkey

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.stockmonkey.ui.theme.StockMonkeyTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

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
            .background(
                brush = Brush.verticalGradient(0.0f to Color(0xFFB1A1BA), 0.8f to Color(0xFFF3F0F4), 1.0f to Color(0xFFF3F0F4))
            )
    ) {
        BgImage()

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
}

@Composable
fun BgImage(){
    val bgImgGradient = listOf(Color(0xFF9B87A6), Color(0xFFF3F0F4))
    Image(
        painter = painterResource(id = R.drawable.stockimage),
        contentDescription = null,
        alignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .scale(1f)
            .padding(bottom = 200.dp)
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
            .drawWithContent{
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = bgImgGradient
                    ),
                    blendMode = BlendMode.SrcIn
                )
            },
        contentScale = ContentScale.Crop
    )
}

@Composable
fun LoginTitleCard() {
    val mainGradient = listOf(Color(0xFFA05CFF), Color(0xFF852EFF))

    Text(
        text = "Welcome to",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
    )
    Text(
        text = "StockMonkey",
        fontSize = 50.sp,
        style = TextStyle(brush = Brush.verticalGradient(colors = mainGradient)),
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier
            .padding(bottom = 80.dp)
            .fillMaxWidth(),
        color = Color(0xFF3C2253),
        textAlign = TextAlign.Center,
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
        label = { Text(text = "Username", fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.secondary) },
        singleLine = true,
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontFamily = FontFamily.Monospace)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFF9885A8),
            focusedBorderColor = Color(0xFF852EFF)
        )
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
        label = { Text(text = "Password", fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.secondary) },
        singleLine = true,
        isError = errorMessage != null,
        supportingText = {
            if (errorMessage != null) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontFamily = FontFamily.Monospace)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            TextButton(onClick = onToggleVisibility) {
                Text(text = if (passwordVisible) "Hide" else "Show", fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.secondary)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFFA376B7),
            focusedBorderColor = Color(0xFF9885A8)
        )
    )
}

@Composable
fun LoginSubmitButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mainGradient = listOf(Color(0xFFA05CFF), Color(0xFF852EFF))
    Button(
        onClick = onClick,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(56.dp)
            .background(brush = Brush.verticalGradient(mainGradient)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )
    ) {
        Text(text = "Log In", fontSize = 17.sp, fontFamily = FontFamily.Monospace)
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
            .padding(bottom = 30.dp)
    ) {

        Text(text = "Create Account", fontSize = 14.sp, fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.primary)
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
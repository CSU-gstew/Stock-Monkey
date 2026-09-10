package com.example.stockmonkey

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tilUsername = findViewById<TextInputLayout>(R.id.tilLoginUsername)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilLoginPassword)
        val etUsername = findViewById<TextInputEditText>(R.id.etLoginUsername)
        val etPassword = findViewById<TextInputEditText>(R.id.etLoginPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLoginSubmit)
        val btnCreateAccount = findViewById<MaterialButton>(R.id.btnGoToSignUp)

        val userDao = UserDatabase.getDatabase(this).userDao()

        btnLogin.setOnClickListener {
            val username = etUsername.text?.toString()?.trim().orEmpty()
            val password = etPassword.text?.toString()?.trim().orEmpty()

            tilUsername.error = null
            tilPassword.error = null

            var hasError = false
            if (username.isEmpty()) {
                tilUsername.error = "Username is required"
                hasError = true
            }
            if (password.isEmpty()) {
                tilPassword.error = "Password is required"
                hasError = true
            }

            if (hasError) return@setOnClickListener


            lifecycleScope.launch {
                val user = withContext(Dispatchers.IO) {
                    userDao.getUserWUsername(username)
                }

                if (user == null) {
                    tilUsername.error = "User does not exist"
                } else if (user.password != password) {
                    tilPassword.error = "Incorrect password"
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
        }

        btnCreateAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
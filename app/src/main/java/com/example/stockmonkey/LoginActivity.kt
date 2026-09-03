package com.example.stockmonkey

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<MaterialButton>(R.id.btnLoginSubmit)
        val btnCreateAccount = findViewById<MaterialButton>(R.id.btnGoToSignUp)

        // Redirects to MainActivity when Login is clicked
        btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Prevents going back to the login screen on back press
        }

        // Redirects to SignUpActivity when Create Account is clicked
        btnCreateAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    // Global declarations
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Link XML views
        email = findViewById(R.id.editTextTextEmailAddress)
        password = findViewById(R.id.editTextTextPassword)
        val button: Button = findViewById(R.id.button)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        button.setOnClickListener {

            val txtEmail = email.text.toString().trim()
            val txtPassword = password.text.toString().trim()

            // Input Validation
            if (TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {

                Toast.makeText(this, "Empty credentials", Toast.LENGTH_SHORT).show()

            } else if (txtPassword.length < 6) {

                Toast.makeText(this, "Password too short!", Toast.LENGTH_SHORT).show()

            } else {

                registerUser(txtEmail, txtPassword)
            }
        }

        // Edge-to-Edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
    }

    // Register User Function
    private fun registerUser(txtEmail: String, txtPassword: String) {

        auth.createUserWithEmailAndPassword(txtEmail, txtPassword)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    Toast.makeText(
                        this,
                        "Registration Successful!",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {

                    Toast.makeText(
                        this,
                        "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
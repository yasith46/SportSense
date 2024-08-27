package com.google.mediapipe.examples.poselandmarker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ActivityLogin : AppCompatActivity() {

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var buttonAccount2: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonAccount2 = findViewById(R.id.buttonAccount2)

        buttonAccount2.setOnClickListener {
            val intent = Intent(this, ActivityRegistrations::class.java)
            startActivity(intent)
        }

        // Set click listener for login button
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString()

            // Validate email and password
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Sign in user with Firebase Authentication
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login success
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                        // Example: Navigate to MainActivity after successful login
                        val intent = Intent(this, MainActivity2::class.java)
                        startActivity(intent)
                        finish() // Close the login activity so user can't go back to it
                    } else {
                        // Login failed
                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}

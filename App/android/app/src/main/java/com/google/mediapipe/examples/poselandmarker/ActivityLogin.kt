package com.google.mediapipe.examples.poselandmarker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ActivityLogin : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var textViewSignUp: TextView
    private var isPasswordVisible: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewSignUp = findViewById(R.id.textViewSignUp)

        // Set up clickable "Sign Up" text
        setupSignUpText()

        // Add the toggle functionality to show/hide password
        editTextPassword.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (editTextPassword.right - editTextPassword.compoundDrawables[2].bounds.width())) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
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

            val userName = email.substringBefore('@')

            // Sign in user with Firebase Authentication
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login success
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                        // Example: Navigate to MainActivity after successful login
                        val intent = Intent(this, MainActivity2::class.java)
                        intent.putExtra("USER_NAME", userName)
                        startActivity(intent)
                        finish() // Close the login activity so user can't go back to it
                    } else {
                        // Login failed
                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun setupSignUpText() {
        val fullText = "New to Sportsense? Create an Account"
        val spannableString = SpannableString(fullText)

        // Apply a different color to "Sign Up"
        val colorSpan = ForegroundColorSpan(Color.GREEN) // Change to your desired color
        spannableString.setSpan(colorSpan, fullText.indexOf("Create an Account"), fullText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Make "Sign Up" clickable
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle the click event for "Sign Up"
                val intent = Intent(this@ActivityLogin, ActivityRegistrations::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false // Optional: Remove underline from clickable text
            }
        }

        spannableString.setSpan(clickableSpan, fullText.indexOf("Create an Account"), fullText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Apply the SpannableString to the TextView
        textViewSignUp.text = spannableString
        textViewSignUp.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide Password
            editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0) // Set "eye closed" icon
        } else {
            // Show Password
            editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_red_eye_24, 0) // Set "eye open" icon
        }
        isPasswordVisible = !isPasswordVisible

        // Move the cursor to the end of the text
        editTextPassword.setSelection(editTextPassword.text?.length ?: 0)
    }
}

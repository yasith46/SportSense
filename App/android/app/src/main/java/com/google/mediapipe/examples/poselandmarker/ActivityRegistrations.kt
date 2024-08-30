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


class ActivityRegistrations : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var textViewLogIn: TextView
    private var isPasswordVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrations)

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegister)
        textViewLogIn = findViewById(R.id.textViewLogin)

        // Set up clickable "Sign Up" text
        setupLogInText()

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

        // Set click listener for register button
        buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString()


            // Validate email and password
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userName = email.substringBefore('@')

            // Register user with Firebase Authentication
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration success
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

                        // Update user in the Leaderboard table
                        FirebaseManager.addUserToLeader(userName)

                        val intent = Intent(this, MainActivity2::class.java)
                        intent.putExtra("USER_NAME", userName)
                        startActivity(intent)
                        finish()


                        // Optionally, you can add further actions here like navigating to the next activity
                    } else {
                        // Registration failed
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    private fun setupLogInText() {
        val fullText = "Already have an account? Log in"
        val spannableString = SpannableString(fullText)

        // Apply a different color to "Sign Up"
        val colorSpan = ForegroundColorSpan(Color.GREEN) // Change to your desired color
        spannableString.setSpan(colorSpan, fullText.indexOf("Log in"), fullText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Make "Sign Up" clickable
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle the click event for "Sign Up"
                val intent = Intent(this@ActivityRegistrations, ActivityLogin::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false // Optional: Remove underline from clickable text
            }
        }

        spannableString.setSpan(clickableSpan, fullText.indexOf("Log in"), fullText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Apply the SpannableString to the TextView
        textViewLogIn.text = spannableString
        textViewLogIn.movementMethod = LinkMovementMethod.getInstance()
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

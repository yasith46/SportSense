package com.google.mediapipe.examples.poselandmarker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class second : AppCompatActivity() {
    private lateinit var buttonGoToLogin: Button
    private lateinit var buttonGoToSignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Initialize views
        buttonGoToLogin = findViewById(R.id.buttonGoToLogin)
        buttonGoToSignup = findViewById(R.id.buttonGoToSignup)

        // Set click listener for login button
        buttonGoToLogin.setOnClickListener {
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
            finish()
        }

        buttonGoToSignup.setOnClickListener {
            val intent = Intent(this, ActivityRegistrations::class.java)
            startActivity(intent)
            finish()
        }
    }
}

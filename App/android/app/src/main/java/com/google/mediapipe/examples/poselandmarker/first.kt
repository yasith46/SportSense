package com.google.mediapipe.examples.poselandmarker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class first : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        // Use Handler to navigate to ActivityLogin after 1 second
        Handler(Looper.getMainLooper()).postDelayed({
            // Intent to navigate to ActivityLogin
            val intent = Intent(this, second::class.java)
            startActivity(intent)
            finish() // Finish the current activity so it won't be returned to
        }, 1000) // 1000 milliseconds = 1 second
    }
}
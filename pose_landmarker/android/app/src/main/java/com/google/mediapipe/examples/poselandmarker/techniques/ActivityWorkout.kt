package com.google.mediapipe.examples.poselandmarker.techniques

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.mediapipe.examples.poselandmarker.ActivityVideo
import com.google.mediapipe.examples.poselandmarker.MainActivity
import com.google.mediapipe.examples.poselandmarker.R

private var technique = "no"
private var sport = "no"

class ActivityWorkout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        sport = intent.getStringExtra("EXTRA_MESSAGE") ?: "No message"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        val buttonS = findViewById<Button>(R.id.button1)
        val buttonD = findViewById<Button>(R.id.button2)
        buttonS.setOnClickListener {
            technique = "Squat"
            callActivity()
        }
        buttonD.setOnClickListener {
            technique = "Push Up"
            callActivity()
        }
    }

    private fun callActivity() {



        val intent = Intent(this, ActivityVideo::class.java).also {
            it.putExtra("EXTRA_TECHNIQUE", technique)
            it.putExtra("EXTRA_MESSAGE", sport)


            startActivity(it)
        }
    }
}
package com.google.mediapipe.examples.poselandmarker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.mediapipe.examples.poselandmarker.techniques.ActivitySprint

private var sport= "Sprint"

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val buttonBB = findViewById<Button>(R.id.button5)
        buttonBB.setOnClickListener {
            sport= "Sprint"

            callActivity()
        }
    }

    private fun callActivity() {



        val intent = Intent(this, ActivitySprint::class.java).also {
            it.putExtra("EXTRA_MESSAGE", sport)
            startActivity(it)
        }
    }
}

package com.google.mediapipe.examples.poselandmarker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

private var sport= "sprint"

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val buttonBB = findViewById<Button>(R.id.button5)
        buttonBB.setOnClickListener {
            sport= "cricket"

            callActivity()
        }
    }

    private fun callActivity() {



        val intent = Intent(this, MainActivity::class.java).also {
            it.putExtra("EXTRA_MESSAGE", sport)
            startActivity(it)
        }
    }
}

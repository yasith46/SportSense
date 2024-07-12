package com.google.mediapipe.examples.poselandmarker.techniques

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.google.mediapipe.examples.poselandmarker.ActivityVideo
import com.google.mediapipe.examples.poselandmarker.FirebaseManager.fetchCollectionNames
import com.google.mediapipe.examples.poselandmarker.R

private var technique = "no"
private var sport = "no"

class ActivitySprint : AppCompatActivity() {
    private lateinit var buttonContainer: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {

        sport = intent.getStringExtra("EXTRA_MESSAGE") ?: "No message"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sprint)

        buttonContainer = findViewById(R.id.buttonContainer)
        loadButtonsFromFirestore()


    }

    private fun callActivity() {


        Intent(this, ActivityVideo::class.java).also {
            it.putExtra("EXTRA_TECHNIQUE", technique)
            it.putExtra("EXTRA_MESSAGE", sport)


            startActivity(it)
        }
    }

    private fun loadButtonsFromFirestore() {

        // Replace "sportName" with your actual sport collection name

        fetchCollectionNames(sport) { collectionNames ->
            buttonContainer.removeAllViews() // Clear existing buttons

            for (collectionName in collectionNames) {
                val button = Button(this).apply {
                    text = collectionName
                    technique=collectionName
                    setOnClickListener {
                        callActivity()
                        // Handle button click, e.g., open a new activity or fragment
                    }
                }

                buttonContainer.addView(button)
            }
        }
    }
}
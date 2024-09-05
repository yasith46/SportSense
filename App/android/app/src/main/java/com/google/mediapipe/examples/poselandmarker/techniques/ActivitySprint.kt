package com.google.mediapipe.examples.poselandmarker.techniques
import android.view.MotionEvent
import androidx.core.content.ContextCompat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.mediapipe.examples.poselandmarker.ActivityVideo
import com.google.mediapipe.examples.poselandmarker.FirebaseManager.fetchCollectionNames
import com.google.mediapipe.examples.poselandmarker.R

private var technique = "loading"
private var sport = "loading"

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
        val intent = intent
        val userName = intent.getStringExtra("USER_NAME")


        Intent(this, ActivityVideo::class.java).also {
            it.putExtra("EXTRA_TECHNIQUE", technique)
            it.putExtra("EXTRA_MESSAGE", sport)
            it.putExtra("USER_NAME", userName)

            startActivity(it)
        }
    }

    private fun loadButtonsFromFirestore() {
        fetchCollectionNames(sport) { collectionNames ->
            buttonContainer.removeAllViews() // Clear existing buttons

            for (collectionName in collectionNames) {
                val button = Button(this).apply {
                    val buttonName = collectionName
                    text = buttonName
                    Log.d("button","$buttonName")
                    setTextColor(ContextCompat.getColor(context, R.color.darkteal))
                    textSize = 25f
                    setPadding(40, 80, 40, 80) // Adjust padding if necessary
                    setBackgroundResource(R.drawable.button_background_3) // Use your drawable resource here

                    gravity = Gravity.START or Gravity.CENTER_VERTICAL

                    // Set the OnClickListener to handle the button click
                    setOnClickListener {
                        technique = (it as Button).text.toString()
                        Log.d("check","$technique")
                        callActivity()
                    }

                    // Add the OnTouchListener for the scaling effect
                    setOnTouchListener { v, event ->
                        when (event.action) {
                            MotionEvent.ACTION_DOWN -> {
                                v.scaleX = 0.98f
                                v.scaleY = 0.98f
                            }
                            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                                v.scaleX = 1f
                                v.scaleY = 1f
                            }
                        }
                        false
                    }
                }

                // Set button size programmatically
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    // Set margins (top, right, bottom, left)
                    setMargins(10, 10, 10, 10)
                }
                button.layoutParams = layoutParams

                buttonContainer.addView(button)
            }
        }
    }
}
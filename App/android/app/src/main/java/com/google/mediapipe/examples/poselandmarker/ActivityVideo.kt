package com.google.mediapipe.examples.poselandmarker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.mediapipe.examples.poselandmarker.FirebaseManager.fetchVideoURL
import com.google.mediapipe.examples.poselandmarker.PoseLandmarkerHelper.Companion.TAG


private var technique = "no"
private var message = "no"
private var videoUrl = "no"



class ActivityVideo : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()




        val videoId = "Ig-7QmBvLUA" // Replace with your YouTube video ID
        //videoUrl = "https://www.youtube.com/shorts/iZTxa8NJH2g"


        message = intent.getStringExtra("EXTRA_MESSAGE") ?: "No message"
        technique = intent.getStringExtra("EXTRA_TECHNIQUE") ?: "No message"
        Log.d(TAG, "Message: $message")
        Log.d(TAG, "technique: $technique")


        fetchVideoURL(message, technique) { videoURL ->
            if (videoURL != null) {
                videoUrl = videoURL
                Log.d(TAG, "Message: $message")
                Log.d(TAG, "technique: $technique")
                Log.d(TAG, "URL: $videoUrl")

                runOnUiThread {
                    webView.loadUrl(videoUrl) // Load the video URL into WebView
                    Log.d(TAG, "Loaded URL: $videoUrl")
                }

                // Use videoURL as needed
                Log.d("Firestore", "Video URL: $videoURL")
            } else {
                Log.w("Firestore", "Video URL not found or retrieval failed")
            }
        }

        val buttonS = findViewById<Button>(R.id.button3)
        buttonS.setOnClickListener {
            callActivity()

        }



    }


    private fun callActivity() {


        Intent(this, MainActivity::class.java).also {
            it.putExtra("EXTRA_TECHNIQUE", technique)
            it.putExtra("EXTRA_MESSAGE", message)




            startActivity(it)
        }
    }


}
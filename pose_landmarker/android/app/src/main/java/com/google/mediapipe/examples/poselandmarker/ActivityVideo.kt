package com.google.mediapipe.examples.poselandmarker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button


private var technique = "no"
private var message = "no"

class ActivityVideo : AppCompatActivity() {
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        val videoId = "Ig-7QmBvLUA" // Replace with your YouTube video ID
        val videoUrl = "https://www.youtube.com/shorts/iZTxa8NJH2g"


        message = intent.getStringExtra("EXTRA_MESSAGE") ?: "No message"
        technique = intent.getStringExtra("EXTRA_TECHNIQUE") ?: "No message"

        val buttonS = findViewById<Button>(R.id.button3)
        buttonS.setOnClickListener {
            callActivity()

        }



        webView.loadUrl(videoUrl)
    }

    private fun callActivity() {


        Intent(this, MainActivity::class.java).also {
            it.putExtra("EXTRA_TECHNIQUE", technique)
            it.putExtra("EXTRA_MESSAGE", message)


            startActivity(it)
        }
    }
}
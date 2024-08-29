package com.google.mediapipe.examples.poselandmarker

import ViewPagerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.mediapipe.examples.poselandmarker.techniques.ActivitySprint

private var sport= "Sprint"
private var activity= "no"


class MainActivity2 : AppCompatActivity() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)



        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        val intent = intent
        val userName = intent.getStringExtra("USER_NAME")

        // Initialize the adapter and set it to the ViewPager2
        viewPagerAdapter = ViewPagerAdapter(this, userName)
        viewPager.adapter = viewPagerAdapter


        // Attach the TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Tab ${position + 1}"  // Set tab title based on position
        }.attach()

    }

//    private fun callActivity() {
//        val targetActivity = when (activity) {
//            "ActivityWorkout" -> ActivitySprint::class.java
//            "ActivitySprint" -> ActivitySprint::class.java
//            else -> null
//        }
//
//        targetActivity?.let {
//            val intent = Intent(this, it).apply {
//                putExtra("EXTRA_MESSAGE", sport)
//            }
//            startActivity(intent)
//        } ?: run {
//            // Handle the case where the activity is not found
//            Log.e("MainActivity2", "Activity not found")
//        }
//    }

}

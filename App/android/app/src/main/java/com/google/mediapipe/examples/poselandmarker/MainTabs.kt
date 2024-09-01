package com.google.mediapipe.examples.poselandmarker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.mediapipe.examples.poselandmarker.Home.Companion.ARG_DATA
import com.google.mediapipe.examples.poselandmarker.Leaderboard.Companion
import com.google.mediapipe.examples.poselandmarker.techniques.ActivitySprint
import kotlin.math.log

private var sport= "Sprint"
private var activity= "no"

class Profile: Fragment() {

    companion object {
        private const val ARG_DATA = "arg_data"

        fun newInstance(data: String?): Profile {
            val fragment = Profile()
            val args = Bundle().apply {
                putString(ARG_DATA, data)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        // Retrieve the data from arguments
        val userName = arguments?.getString(ARG_DATA) ?: "User Name"

        val myTextView = rootView.findViewById<TextView>(R.id.userName)
        myTextView.text = userName

        FirebaseManager.fetchScore(userName){Score ->
            val score = Score ?: 0
            val level = score/5
            val myTextScore = rootView.findViewById<TextView>(R.id.textViewScore)
            myTextScore.text = score.toString() + " XP"

            val myTextLevel = rootView.findViewById<TextView>(R.id.textViewLevel)
            myTextLevel.text = level.toString()
        }

        val buttonLogout = rootView.findViewById<Button>(R.id.logoutButton)
        buttonLogout.setOnClickListener {
            sport= "Sprint"
            //activity = "ActivitySprint"
            callActivity()
        }

        return rootView
    }

    private fun callActivity() {

        val intent = Intent(activity, ActivityLogin::class.java).also {
            startActivity(it)
        }
    }
}

class Leaderboard: Fragment(R.layout.fragment_leaderboard) {

    private lateinit var adapter: LeaderboardAdapter

    companion object {
        private const val ARG_DATA = "arg_data"

        fun newInstance(data: String?): Leaderboard {
            val fragment = Leaderboard()
            val args = Bundle().apply {
                putString(ARG_DATA, data)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false)

        // Retrieve the data from arguments
        val userName = arguments?.getString(ARG_DATA) ?: "User Name"

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the RecyclerView by its ID from the inflated view
        val leaderboardRecyclerView = view.findViewById<RecyclerView>(R.id.leaderboardRecyclerView)

        // Set LayoutManager (LinearLayoutManager for a vertical list)
        leaderboardRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter with an empty list or initial data
        adapter = LeaderboardAdapter(listOf())
        leaderboardRecyclerView.adapter = adapter

        FirebaseManager.fetchLeader { sortedData ->
            val leaderboardItems = sortedData.map { (fieldName, value) ->
                LeaderboardItem(fieldName, value.toString() + " XP")
            }
            adapter.updateData(leaderboardItems) // Update adapter with new data
        }

    }
}

class Home : Fragment() {

    companion object {
        private const val ARG_DATA = "arg_data"

        fun newInstance(data: String?): Home {
            val fragment = Home()
            val args = Bundle().apply {
                putString(ARG_DATA, data)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        // Retrieve the data from arguments
        val userName = arguments?.getString(ARG_DATA) ?: "User Name"
        val myTextView = rootView.findViewById<TextView>(R.id.textView4)

        Log.d("Checkuser", "Plsss: $userName")
        myTextView.text = "Hi " + userName + "!"

        val buttonS = rootView.findViewById<Button>(R.id.button5)
        buttonS.setOnClickListener {
            sport= "Sprint"
            //activity = "ActivitySprint"
            callActivity(userName)
        }
        val buttonW = rootView.findViewById<Button>(R.id.button7)
        buttonW.setOnClickListener {
            sport= "Workout"
            //activity = "ActivityWorkout"

            callActivity(userName)
        }
        val buttonC = rootView.findViewById<Button>(R.id.button8)
        buttonC.setOnClickListener {
            sport= "Cricket"
            //activity = "ActivityWorkout"

            callActivity(userName)
        }

        return rootView
    }

    private fun callActivity(user: String) {

        val intent = Intent(activity, ActivitySprint::class.java).also {
            it.putExtra("EXTRA_MESSAGE", sport)
            it.putExtra("USER_NAME", user)

            startActivity(it)
        }
    }
}
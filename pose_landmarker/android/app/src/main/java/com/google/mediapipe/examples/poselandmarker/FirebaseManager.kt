package com.google.mediapipe.examples.poselandmarker


import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseManager {

        @SuppressLint("StaticFieldLeak")
        private val db = FirebaseFirestore.getInstance()

        // Function to add a technique with multiple joint sets, each including an expected angle
        fun addTechnique(sportName: String, techniqueName: String, joints: List<Map<String, Any>>) {
            val techniqueRef = db.collection(sportName).document(techniqueName)

            // Add each set of joints with its expected angle
            for ((index, jointSet) in joints.withIndex()) {
                techniqueRef.collection("joints").document("set_$index")
                    .set(jointSet)
                    .addOnSuccessListener { Log.d("Firestore", "Joint set successfully written!") }
                    .addOnFailureListener { e -> Log.w("Firestore", "Error writing document", e) }
            }
        }

        // Function to fetch joints and their expected angles for a specific sport and technique
        fun fetchJointsAndAngles(sportName: String, techniqueName: String, subcollectionName: String, onComplete: (List<Map<String, Any>>) -> Unit) {
            val techniqueRef = db.collection(sportName).document(techniqueName).collection(subcollectionName)

            techniqueRef.get().addOnSuccessListener { jointsSnapshot ->
                val joints = jointsSnapshot.documents.mapNotNull { it.data }
                onComplete(joints)
            }.addOnFailureListener { e ->
                Log.w("Firestore", "Error getting $subcollectionName collection", e)
                onComplete(emptyList())
            }
        }

}




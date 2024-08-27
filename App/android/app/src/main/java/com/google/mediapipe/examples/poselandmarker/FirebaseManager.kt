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
    fun fetchCollectionNames(sportName: String, onComplete: (List<String>) -> Unit) {
        db.collection(sportName).get().addOnSuccessListener { result ->
            val collectionNames = result.documents.map { it.id }
            onComplete(collectionNames)
        }.addOnFailureListener { e ->
            Log.w("Firestore", "Error getting collection names", e)
            onComplete(emptyList())
        }
    }

    fun fetchVideoURL(sportName: String, techniqueName: String, onComplete: (String?) -> Unit) {
        db.collection(sportName)
            .document(techniqueName)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val videoURL = document.getString("videoURL")
                    onComplete(videoURL)
                } else {
                    onComplete(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting video URL", exception)
                onComplete(null)
            }
    }

    fun fetchCollections(sportName: String, techniqueName: String, onComplete: (List<String>) -> Unit) {
        db.collection(sportName)
            .document(techniqueName)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val collections = documentSnapshot.data?.keys?.toList() ?: emptyList()
                    onComplete(collections)
                } else {
                    onComplete(emptyList())
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error fetching collections", exception)
                onComplete(emptyList())
            }
    }



}
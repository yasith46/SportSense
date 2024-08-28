package com.google.mediapipe.examples.poselandmarker


import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

object FirebaseManager {

    @SuppressLint("StaticFieldLeak")
    private val db = FirebaseFirestore.getInstance()

    // Function to update score in the leaderboard
    fun updateScore(fieldName: String, newScore: Int, callback: (Boolean?) -> Unit){
        val docRef = db.collection("Leaderboard").document("Score")

        val updateData = mapOf(fieldName to newScore)

        docRef.update(updateData)
            .addOnSuccessListener {
                Log.d("FirebaseManager", "Field value updated successfully.")
                callback(true)
            }
            .addOnFailureListener { exception ->
                Log.d("FirebaseManager", "Error updating field value.", exception)
                callback(false)
            }
    }

    // Function to receive score of the user
    fun fetchScore(fieldName: String, callback: (Int?) -> Unit) {
        val docRef = db.collection("Leaderboard").document("Score")

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("checkcheck", "check123: $fieldName")
                    Log.d("check", "check1: ${document.data}")
                    val fieldValue = document.getLong(fieldName)?.toInt()
                    Log.d("GetValue", "Plsss: $fieldValue")
                    callback(fieldValue)
                } else {
                    Log.d("Firestore", "No such document")
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "Failed to fetch document", exception)
                callback(null)
            }
    }

    // Function to add user to database with a score of 0
    fun addUserToLeader(user: String) {
        val updateUser = hashMapOf(user to 0)

        db.collection("Leaderboard").document("Score")
            .set(updateUser, SetOptions.merge())
            .addOnSuccessListener { Log.d("Firestore", "User Successfully Registered!") }
            .addOnFailureListener { e -> Log.w("Firestore", "Error writing document", e) }
    }

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
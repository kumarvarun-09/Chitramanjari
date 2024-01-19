package com.comiccoder.chitramanjari.database

import android.app.ProgressDialog
import android.net.Uri
import com.comiccoder.chitramanjari.constants.POST
import com.comiccoder.chitramanjari.constants.REEL
import com.comiccoder.chitramanjari.dataModels.User
import com.comiccoder.chitramanjari.constants.SUCCESS
import com.comiccoder.chitramanjari.constants.USER_DETAILS
import com.comiccoder.chitramanjari.constants.USER_NODE
import com.comiccoder.chitramanjari.dataModels.Post
import com.comiccoder.chitramanjari.dataModels.VideoPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun registerUser(user: User, password: String, callback: (String?) -> Unit) {
    FirebaseAuth.getInstance()
        .createUserWithEmailAndPassword(user.email!!, password)
        .addOnCompleteListener { result ->
            if (result.isSuccessful) {
                Firebase.firestore.collection(USER_NODE)
                    .document(Firebase.auth.currentUser!!.uid)
                    .collection(USER_DETAILS)
                    .document(USER_DETAILS)
                    .set(user)
                    .addOnSuccessListener {
                        callback(SUCCESS)
                    }
            } else {
                callback(result.exception!!.localizedMessage)
            }
        }
}

fun loginUser(email: String, password: String, callback: (String?) -> Unit) {
    FirebaseAuth.getInstance()
        .signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { result ->
            if (result.isSuccessful) {
                callback(SUCCESS)
            } else {
                callback(result.exception!!.localizedMessage)
            }
        }
}

fun getCurrentUser(): FirebaseUser? {
    return FirebaseAuth.getInstance().currentUser
}

fun getCurrentUserData(callback: (User?) -> Unit) {
    Firebase.firestore.collection(USER_NODE).document(getCurrentUser()!!.uid).get()
        .addOnSuccessListener {
            it.toObject(User::class.java)
        }
}

fun addPost(post: Post, callback: (String?) -> Unit) {
    Firebase.firestore.collection(USER_NODE)
        .document(Firebase.auth.currentUser!!.uid)
        .collection(POST)
        .document(post.postTime!!).set(post)
        .addOnSuccessListener {
            callback(SUCCESS)
        }
}

fun addVideoPost(videoPost: VideoPost, callback: (String?) -> Unit) {
    Firebase.firestore.collection(USER_NODE)
        .document(Firebase.auth.currentUser!!.uid)
        .collection(REEL)
        .document(videoPost.postTime!!).set(videoPost)
        .addOnSuccessListener {
            callback(SUCCESS)
        }
}

fun uploadDocument(
    uri: Uri,
    folderName: String,
    progressDialog: ProgressDialog?,
    callback: (String?) -> Unit
) {
    progressDialog!!.setTitle("Uploading...")
    progressDialog.setCancelable(false)
    progressDialog.show()
    var documentURL: String? = null
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                documentURL = it.toString()
                progressDialog.dismiss()
                callback(documentURL)
            }
        }
        .addOnProgressListener {
            val uploadedValue = (it.bytesTransferred * 100) / it.totalByteCount
            progressDialog.setMessage("Uploaded $uploadedValue%")
        }
}

fun getMyPosts(callback: (ArrayList<Post>?) -> Unit) {
    val postList = ArrayList<Post>()
    Firebase.firestore.collection(USER_NODE).document(getCurrentUser()!!.uid).collection(POST).get()
        .addOnSuccessListener {
            for (i in it.documents) {
                postList.add(i.toObject<Post>()!!)
            }
            callback(postList)
        }
}
fun getMyReels(callback: (ArrayList<VideoPost>?) -> Unit) {
    val videoPostList = ArrayList<VideoPost>()
    Firebase.firestore.collection(USER_NODE).document(getCurrentUser()!!.uid).collection(REEL).get()
        .addOnSuccessListener {
            for (i in it.documents) {
                videoPostList.add(i.toObject<VideoPost>()!!)
            }
            callback(videoPostList)
        }
}
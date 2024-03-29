package com.comiccoder.chitramanjari.database

import android.app.ProgressDialog
import android.net.Uri
import android.util.Log
import com.comiccoder.chitramanjari.constants.ALL_POSTS
import com.comiccoder.chitramanjari.constants.ALL_REELS
import com.comiccoder.chitramanjari.constants.POST
import com.comiccoder.chitramanjari.constants.REEL
import com.comiccoder.chitramanjari.dataModels.User
import com.comiccoder.chitramanjari.constants.SUCCESS
import com.comiccoder.chitramanjari.constants.USER_FOLLOWS
import com.comiccoder.chitramanjari.constants.USER_NODE
import com.comiccoder.chitramanjari.constants.USER_POSTS_AND_REELS
import com.comiccoder.chitramanjari.dataModels.AllPostModel
import com.comiccoder.chitramanjari.dataModels.AllReelsModel
import com.comiccoder.chitramanjari.dataModels.Post
import com.comiccoder.chitramanjari.dataModels.VideoPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
                user.id = getCurrentUser()!!.uid
                Firebase.firestore.collection(USER_NODE)
                    .document(user.id!!)
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
    getUserDataWithId(getCurrentUser()!!.uid) {
        callback(it)
    }
}

fun getUserDataWithId(uid: String, callback: (User?) -> Unit) {
    Firebase.firestore.collection(USER_NODE)
        .document(uid)
        .get()
        .addOnSuccessListener {
            callback(it.toObject<User>())
        }
}

fun getPostDataWithUIdAndPostId(uid: String, postId: String, callback: (Post?) -> Unit) {
    Firebase.firestore.collection(USER_POSTS_AND_REELS)
        .document(uid)
        .collection(POST)
        .document(postId)
        .get()
        .addOnSuccessListener {
            callback(it.toObject<Post>())
        }
}

fun getReelDataWithUidAndReelId(uid: String, reelId: String, callback: (VideoPost?) -> Unit) {
    Firebase.firestore.collection(USER_POSTS_AND_REELS)
        .document(uid)
        .collection(REEL)
        .document(reelId)
        .get()
        .addOnSuccessListener {
            callback(it.toObject<VideoPost>())
        }
}

fun addPost(post: Post, callback: (String?) -> Unit) {
    Firebase.firestore.collection(USER_POSTS_AND_REELS)
        .document(getCurrentUser()!!.uid)
        .collection(POST)
        .document(post.postTime!!.toString()).set(post)
        .addOnSuccessListener {
            Firebase.firestore.collection(ALL_POSTS)
                .document(post.postTime!!.toString() + getCurrentUser()!!.uid)
                .set(AllPostModel(getCurrentUser()!!.uid, post.postTime!!.toString()))
                .addOnSuccessListener {
                    callback(SUCCESS)
                }
        }
}

fun addVideoPost(videoPost: VideoPost, callback: (String?) -> Unit) {
    Firebase.firestore.collection(USER_POSTS_AND_REELS)
        .document(getCurrentUser()!!.uid)
        .collection(REEL)
        .document(videoPost.postTime!!.toString()).set(videoPost)
        .addOnSuccessListener {
            Firebase.firestore.collection(ALL_REELS)
                .document(videoPost.postTime!!.toString() + getCurrentUser()!!.uid)
                .set(AllReelsModel(getCurrentUser()!!.uid, videoPost.postTime!!.toString()))
                .addOnSuccessListener {
                    callback(SUCCESS)
                }
        }
}

fun getAllUsersData(searchText: String, callback: (ArrayList<User>) -> Unit) {
    val searchUserList = ArrayList<User>()
    Firebase.firestore.collection(USER_NODE).get()
        .addOnSuccessListener {
            for (i in it.documents) {
                if (i.id != getCurrentUser()!!.uid) {
                    val user = i.toObject<User>()
                    val name = user!!.name!!.lowercase()
                    val email = user.email!!.lowercase()
                    if (name.contains(searchText) || email.contains(searchText)) {
                        searchUserList.add(user)
                    }
                }
            }
            callback(searchUserList)
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
    Firebase.firestore.collection(USER_POSTS_AND_REELS).document(getCurrentUser()!!.uid)
        .collection(POST).get()
        .addOnSuccessListener {
            for (i in it.documents) {
                postList.add(i.toObject<Post>()!!)
            }
            callback(postList)
        }
}

fun getMyReels(callback: (ArrayList<VideoPost>?) -> Unit) {
    val videoPostList = ArrayList<VideoPost>()
    Firebase.firestore.collection(USER_POSTS_AND_REELS).document(getCurrentUser()!!.uid)
        .collection(REEL).get()
        .addOnSuccessListener {
            for (i in it.documents) {
                videoPostList.add(i.toObject<VideoPost>()!!)
            }
            callback(videoPostList)
        }
}

fun getAllPosts(callback: (ArrayList<AllPostModel>?) -> Unit) {
    val allPostList = ArrayList<AllPostModel>()
    Firebase.firestore.collection(ALL_POSTS).get()
        .addOnSuccessListener {
            for (i in it.documents) {
                allPostList.add(i.toObject<AllPostModel>()!!)
            }
            callback(allPostList)
        }
}


fun getAllReels(callback: (ArrayList<AllReelsModel>?) -> Unit) {
    val allReelsList = ArrayList<AllReelsModel>()
    Firebase.firestore.collection(ALL_REELS).get()
        .addOnSuccessListener {
            for (i in it.documents) {
                allReelsList.add(i.toObject<AllReelsModel>()!!)
            }
            callback(allReelsList)
        }
}

fun followUserWithId(id: String, callback: (String) -> Unit) {
    Firebase.firestore.collection(USER_FOLLOWS)
        .document(getCurrentUser()!!.uid)
        .collection(USER_FOLLOWS)
        .document(id).set({null})
        .addOnSuccessListener {
            callback(SUCCESS)
        }
}

fun unFollowUserWithId(id: String, callback: (String) -> Unit) {
    Firebase.firestore.collection(USER_FOLLOWS)
        .document(getCurrentUser()!!.uid)
        .collection(USER_FOLLOWS)
        .document(id).delete()
        .addOnSuccessListener {
            callback(SUCCESS)
        }
}

fun iFollowUserOrNot(id: String, callback: (Boolean) -> Unit) {
    Firebase.firestore.collection(USER_FOLLOWS)
        .document(getCurrentUser()!!.uid)
        .collection(USER_FOLLOWS)
        .document(id)
        .get()
        .addOnSuccessListener {
            if(it != null)
            {
                Log.d("it callback", it.id)
                callback(true)
            }
            else
                callback(false)
        }
}
fun getIFollow(callback: (ArrayList<String>?) -> Unit)
{
    val iFollowList = ArrayList<String>()
    Firebase.firestore.collection(USER_FOLLOWS)
        .document(getCurrentUser()!!.uid)
        .collection(USER_FOLLOWS)
        .get()
        .addOnSuccessListener {
            for(i in it.documents)
            {
                iFollowList.add(i.id)
            }
            callback(iFollowList)
        }
}
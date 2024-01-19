package com.comiccoder.chitramanjari.posts

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.comiccoder.chitramanjari.databinding.ActivityPostsBinding
import com.comiccoder.chitramanjari.constants.POST_FOLDER
import com.comiccoder.chitramanjari.dataModels.Post
import com.comiccoder.chitramanjari.dataModels.VideoPost
import com.comiccoder.chitramanjari.database.addPost
import com.comiccoder.chitramanjari.database.uploadDocument
import com.comiccoder.chitramanjari.utils.getCurrentTime
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class PostsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPostsBinding.inflate(layoutInflater) }

    private val post by lazy { Post(null, null, null) }

    val progressDialog by lazy { ProgressDialog(this@PostsActivity) }

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadDocument(uri, POST_FOLDER, progressDialog) {
                if (it != null) {
                    binding.circleImageView.setImageURI(uri)
                    post.postUrl = it
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        post.postUrl = intent.getStringExtra(POST_FOLDER)
        Picasso.get().load(post.postUrl).into(binding.circleImageView)

        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }
        binding.circleImageView.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.postBtn.setOnClickListener {
            if (post.postUrl == null) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Please select an image to post",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            post.caption = binding.captionTV.editText!!.text.toString().trim()
            post.postTime = getCurrentTime()

            addPost(post) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Uploaded Successfully",
                    Snackbar.LENGTH_SHORT
                ).show()
                finish()
            }
        }
        binding.cancelBtn.setOnClickListener {
            finish()
        }

    }

}
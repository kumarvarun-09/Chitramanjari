package com.comiccoder.chitramanjari.posts

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.comiccoder.chitramanjari.constants.POST_FOLDER
import com.comiccoder.chitramanjari.constants.VIDEOS_FOLDER
import com.comiccoder.chitramanjari.dataModels.VideoPost
import com.comiccoder.chitramanjari.database.addVideoPost
import com.comiccoder.chitramanjari.database.uploadDocument
import com.comiccoder.chitramanjari.databinding.ActivityReelsBinding
import com.comiccoder.chitramanjari.utils.getCurrentTime
import com.comiccoder.chitramanjari.utils.getTimeStamp
import com.google.android.material.snackbar.Snackbar

class ReelsActivity : AppCompatActivity() {
    private val binding by lazy { ActivityReelsBinding.inflate(layoutInflater) }
    private val videoPost by lazy { VideoPost(null, null, null) }
    val progressDialog by lazy { ProgressDialog(this@ReelsActivity) }
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){uri->
        uri?.let {
            uploadDocument(uri, POST_FOLDER, progressDialog){
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        videoPost.videoUrl = intent.getStringExtra(VIDEOS_FOLDER)

        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.postBtn.setOnClickListener {
            if (videoPost.videoUrl == null) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Please select a video to post",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            videoPost.caption = binding.captionTV.editText!!.text.toString().trim()
            videoPost.postTime = getTimeStamp()

            addVideoPost(videoPost) {
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
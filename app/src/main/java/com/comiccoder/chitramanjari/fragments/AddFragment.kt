package com.comiccoder.chitramanjari.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.comiccoder.chitramanjari.constants.POST_FOLDER
import com.comiccoder.chitramanjari.constants.VIDEOS_FOLDER
import com.comiccoder.chitramanjari.dataModels.Post
import com.comiccoder.chitramanjari.dataModels.VideoPost
import com.comiccoder.chitramanjari.database.uploadDocument
import com.comiccoder.chitramanjari.databinding.FragmentAddBinding
import com.comiccoder.chitramanjari.posts.PostsActivity
import com.comiccoder.chitramanjari.posts.ReelsActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {

    private val binding by lazy { FragmentAddBinding.inflate(layoutInflater) }
    private val post by lazy { Post(null, null, null) }
    private val videoPost by lazy { VideoPost(null, null, null) }
    private val progressDialog by lazy {ProgressDialog(context)}
    private val launcherPhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                uploadDocument(uri, POST_FOLDER, progressDialog) {
                    if (it != null) {
                        post.postUrl = it
                        val intent = Intent(requireContext(), PostsActivity::class.java)
                        intent.putExtra(POST_FOLDER, post.postUrl)
                        requireActivity().startActivity(intent)
                    }
                }
            }
        }
    private val launcherVideo =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                uploadDocument(uri, VIDEOS_FOLDER, progressDialog) {
                    if (it != null) {
                        videoPost.videoUrl = it
                        val intent = Intent(requireContext(), ReelsActivity::class.java)
                        intent.putExtra(VIDEOS_FOLDER, videoPost.videoUrl)
                        requireActivity().startActivity(intent)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.addPost.setOnClickListener {
            launcherPhoto.launch("image/*")
        }
        binding.addReel.setOnClickListener {
            launcherVideo.launch("video/*")
        }
        return binding.root
    }

    companion object {
    }
}
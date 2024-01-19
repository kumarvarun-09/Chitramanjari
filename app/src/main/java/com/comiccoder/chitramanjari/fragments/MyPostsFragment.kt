package com.comiccoder.chitramanjari.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.comiccoder.chitramanjari.adapters.MyPostRvAdapter
import com.comiccoder.chitramanjari.dataModels.Post
import com.comiccoder.chitramanjari.database.getMyPosts
import com.comiccoder.chitramanjari.databinding.FragmentMyPostsBinding

class MyPostsFragment : Fragment() {
    private val binding by lazy { FragmentMyPostsBinding.inflate(layoutInflater) }
    private lateinit var postsList: ArrayList<Post>
    private lateinit var adapter: MyPostRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        getMyPosts {
            postsList = it!!
            postsList.reverse()
            adapter = MyPostRvAdapter(requireContext(), postsList)
            binding.rvPosts.layoutManager =
                StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
            binding.rvPosts.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
    }

}
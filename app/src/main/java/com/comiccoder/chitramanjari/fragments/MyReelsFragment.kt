package com.comiccoder.chitramanjari.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.comiccoder.chitramanjari.adapters.MyReelsRvAdapter
import com.comiccoder.chitramanjari.dataModels.VideoPost
import com.comiccoder.chitramanjari.database.getMyReels
import com.comiccoder.chitramanjari.databinding.FragmentMyReelsBinding

class MyReelsFragment : Fragment() {
    private val binding by lazy { FragmentMyReelsBinding.inflate(layoutInflater) }
    private lateinit var postsList: ArrayList<VideoPost>
    private lateinit var adapter: MyReelsRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        getMyReels {
            postsList = it!!
            postsList.reverse()
            adapter = MyReelsRvAdapter(requireContext(), postsList)
            binding.rvReels.layoutManager =
                StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
            binding.rvReels.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }
}
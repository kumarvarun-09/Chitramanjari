package com.comiccoder.chitramanjari.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.comiccoder.chitramanjari.R
import com.comiccoder.chitramanjari.adapters.ReelViewAdapter
import com.comiccoder.chitramanjari.dataModels.VideoPost
import com.comiccoder.chitramanjari.database.getMyReels
import com.comiccoder.chitramanjari.databinding.FragmentReelBinding

class ReelFragment : Fragment() {

    private val binding by lazy {
        FragmentReelBinding.inflate(layoutInflater)
    }
    private lateinit var reelAdapter: ReelViewAdapter
    private var reelList = ArrayList<VideoPost>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getMyReels {
            reelList = it!!
            reelAdapter = ReelViewAdapter(requireContext(), reelList)
            binding.reelViewPager.adapter = reelAdapter
        }

        return binding.root
    }

    companion object {
    }
}
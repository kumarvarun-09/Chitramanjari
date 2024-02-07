package com.comiccoder.chitramanjari.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.comiccoder.chitramanjari.adapters.AllReelsAdapter
import com.comiccoder.chitramanjari.dataModels.AllReelsModel
import com.comiccoder.chitramanjari.database.getAllReels
import com.comiccoder.chitramanjari.databinding.FragmentReelBinding

class ReelFragment : Fragment() {

    private val binding by lazy {
        FragmentReelBinding.inflate(layoutInflater)
    }
    private lateinit var reelAdapter: AllReelsAdapter
    private var reelList = ArrayList<AllReelsModel>()

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
        getAllReels {
            reelList = it!!
            reelList.reverse()
            reelAdapter = AllReelsAdapter(requireContext(), reelList)
            binding.reelViewPager.adapter = reelAdapter
        }

        return binding.root
    }

    companion object {
    }
}
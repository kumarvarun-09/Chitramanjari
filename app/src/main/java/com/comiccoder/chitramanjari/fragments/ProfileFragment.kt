package com.comiccoder.chitramanjari.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.comiccoder.chitramanjari.R
import com.comiccoder.chitramanjari.adapters.ViewPagerAdapter
import com.comiccoder.chitramanjari.constants.MY_POSTS_FRAGMENT_TITLE
import com.comiccoder.chitramanjari.constants.MY_REELS_FRAGMENT_TITLE
import com.comiccoder.chitramanjari.dataModels.User
import com.comiccoder.chitramanjari.databinding.FragmentProfileBinding
import com.comiccoder.chitramanjari.database.getCurrentUser
import com.comiccoder.chitramanjari.database.getCurrentUserData
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragment(MyPostsFragment(), MY_POSTS_FRAGMENT_TITLE)
        viewPagerAdapter.addFragment(MyReelsFragment(), MY_REELS_FRAGMENT_TITLE)
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    companion object {
    }

    override fun onStart() {
        super.onStart()
        getCurrentUserData {
            binding.name.text = it!!.name
            binding.email.text = it.email
            if (!it.image.isNullOrEmpty()) {
                Picasso.get().load(it.image).into(binding.circleImageView)
            }
        }
    }
}
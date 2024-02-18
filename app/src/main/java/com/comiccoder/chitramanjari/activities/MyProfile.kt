package com.comiccoder.chitramanjari.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.comiccoder.chitramanjari.R
import com.comiccoder.chitramanjari.adapters.ViewPagerAdapter
import com.comiccoder.chitramanjari.constants.MY_POSTS_FRAGMENT_TITLE
import com.comiccoder.chitramanjari.constants.MY_REELS_FRAGMENT_TITLE
import com.comiccoder.chitramanjari.database.getCurrentUserData
import com.comiccoder.chitramanjari.databinding.ActivityMyProfileBinding
import com.comiccoder.chitramanjari.fragments.MyPostsFragment
import com.comiccoder.chitramanjari.fragments.MyReelsFragment
import com.squareup.picasso.Picasso

class MyProfile : AppCompatActivity() {
    val binding by lazy { ActivityMyProfileBinding.inflate(layoutInflater) }
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        val actionBar = supportActionBar

        if (actionBar != null) {

            // Customize the back button
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back__black_24);

            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getCurrentUserData {
            binding.name.text = it!!.name
            binding.email.text = it.email
            if (!it.image.isNullOrEmpty()) {
                Picasso.get().load(it.image).into(binding.circleImageView)
            }
        }
        viewPagerAdapter = ViewPagerAdapter(this@MyProfile.supportFragmentManager)
        viewPagerAdapter.addFragment(MyPostsFragment(), MY_POSTS_FRAGMENT_TITLE)
        viewPagerAdapter.addFragment(MyReelsFragment(), MY_REELS_FRAGMENT_TITLE)
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    // this event will enable the back
    // function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
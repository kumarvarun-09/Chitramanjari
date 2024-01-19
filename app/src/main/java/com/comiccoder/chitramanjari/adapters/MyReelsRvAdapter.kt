package com.comiccoder.chitramanjari.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.comiccoder.chitramanjari.dataModels.Post
import com.comiccoder.chitramanjari.dataModels.VideoPost
import com.comiccoder.chitramanjari.databinding.MyPostRvBinding
import com.comiccoder.chitramanjari.databinding.MyReelRvBinding
import com.squareup.picasso.Picasso

class MyReelsRvAdapter (val context: Context, private val videoPostList: ArrayList<VideoPost>) :
    RecyclerView.Adapter<MyReelsRvAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: MyReelRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyReelRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return videoPostList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(videoPostList[position].videoUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.ivPostReel)
    }
}
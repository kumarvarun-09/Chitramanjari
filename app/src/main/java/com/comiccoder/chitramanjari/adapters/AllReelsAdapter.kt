package com.comiccoder.chitramanjari.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.comiccoder.chitramanjari.R
import com.comiccoder.chitramanjari.dataModels.AllPostModel
import com.comiccoder.chitramanjari.dataModels.AllReelsModel
import com.comiccoder.chitramanjari.dataModels.Post
import com.comiccoder.chitramanjari.dataModels.VideoPost
import com.comiccoder.chitramanjari.database.getPostDataWithUIdAndPostId
import com.comiccoder.chitramanjari.database.getReelDataWithUidAndReelId
import com.comiccoder.chitramanjari.database.getUserDataWithId
import com.comiccoder.chitramanjari.databinding.ReelPageBinding
import com.github.marlonlom.utilities.timeago.TimeAgo

class AllReelsAdapter(var context: Context, var reelList: ArrayList<AllReelsModel>) :
    RecyclerView.Adapter<AllReelsAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ReelPageBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReelPageBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reelsVar = reelList[position]
        var reelData: VideoPost? = null
        getUserDataWithId(reelsVar.userId!!) { it ->
            if (it!!.image != null) {
                Glide.with(context).load(it.image).into(holder.binding.circleImageView)
            }
            holder.binding.name.text = it.name
        }
        getReelDataWithUidAndReelId(reelsVar.userId!!, reelsVar.reelId!!) { it ->
            reelData = it
            holder.binding.videoCaption.text = reelData!!.caption
            holder.binding.videoView.setVideoPath(reelData!!.videoUrl)
            holder.binding.videoView.setOnPreparedListener {
                holder.binding.progressBar.visibility = View.GONE
                it.start()
            }
            holder.binding.videoCaption.text = it!!.caption
        }
    }
}
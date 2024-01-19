package com.comiccoder.chitramanjari.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.comiccoder.chitramanjari.R
import com.comiccoder.chitramanjari.dataModels.VideoPost
import com.comiccoder.chitramanjari.databinding.ReelPageBinding
import com.squareup.picasso.Picasso

class ReelViewAdapter(val context: Context, private val reelList: ArrayList<VideoPost>) :
    RecyclerView.Adapter<ReelViewAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ReelPageBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ReelPageBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList[position].profileLink).placeholder(R.drawable.user_img).into(holder.binding.circleImageView)
        holder.binding.videoCaption.text = reelList[position].caption
        holder.binding.videoView.setVideoPath(reelList[position].videoUrl)
        holder.binding.videoView.setOnPreparedListener {
            holder.binding.progressBar.visibility = View.GONE
            it.start()
        }
    }
}
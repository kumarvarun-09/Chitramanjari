package com.comiccoder.chitramanjari.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.comiccoder.chitramanjari.R
import com.comiccoder.chitramanjari.dataModels.AllPostModel
import com.comiccoder.chitramanjari.dataModels.Post
import com.comiccoder.chitramanjari.database.getPostDataWithUIdAndPostId
import com.comiccoder.chitramanjari.database.getUserDataWithId
import com.comiccoder.chitramanjari.databinding.PostRvItemBinding
import com.github.marlonlom.utilities.timeago.TimeAgo

class AllPostsAdapter(var context: Context, var postList: ArrayList<AllPostModel>) :
    RecyclerView.Adapter<AllPostsAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: PostRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PostRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postVar = postList[position]
        var postData: Post? = null
        getUserDataWithId(postVar.userId!!) { it ->
            if (it!!.image != null) {
                Glide.with(context).load(it.image).into(holder.binding.circleImageView)
            }
            holder.binding.name.text = it.name
        }
        getPostDataWithUIdAndPostId(postVar.userId!!, postVar.postId!!) { it ->
            postData = it
            holder.binding.time.text = TimeAgo.using(it!!.postTime!!)
            Glide.with(context).load(it.postUrl).into(holder.binding.postImage)
            holder.binding.postCaption.text = it.caption
        }
        holder.binding.likeButton.setOnClickListener {
            holder.binding.likeButton.setImageResource(R.drawable.heart_red)
        }
        holder.binding.shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, postData!!.caption + "\n" + postData!!.postUrl)
            context.startActivity(intent)
        }
    }
}
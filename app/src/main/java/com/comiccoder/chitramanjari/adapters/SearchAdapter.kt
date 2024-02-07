package com.comiccoder.chitramanjari.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.comiccoder.chitramanjari.R
import com.comiccoder.chitramanjari.constants.FOLLOW
import com.comiccoder.chitramanjari.constants.UNFOLLOW
import com.comiccoder.chitramanjari.dataModels.User
import com.comiccoder.chitramanjari.database.followUserWithId
import com.comiccoder.chitramanjari.database.iFollowUserOrNot
import com.comiccoder.chitramanjari.database.unFollowUserWithId
import com.comiccoder.chitramanjari.databinding.SearchRvItemBinding

class SearchAdapter(
    val context: Context,
    private val userList: ArrayList<User>? = null,
    private val iFollowList: ArrayList<String>? = null
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: SearchRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(userList!![position].image).into(holder.binding.circleImageView)
        holder.binding.name.text = userList[position].name
        holder.binding.email.text = userList[position].email
        holder.binding.btnFollow.setOnClickListener {
            if (iFollowList!!.contains(userList[position].id!!)) {
                unFollowUserWithId(userList[position].id!!) {
                    holder.binding.btnFollow.text = FOLLOW
                    iFollowList.remove(userList[position].id!!)
                }
            } else {
                followUserWithId(userList[position].id!!) {
                    holder.binding.btnFollow.text = UNFOLLOW
                    iFollowList.add(userList[position].id!!)
                }
            }
        }
        if (iFollowList!!.contains(userList[position].id!!)) {
            holder.binding.btnFollow.text = UNFOLLOW
        }
    }
}
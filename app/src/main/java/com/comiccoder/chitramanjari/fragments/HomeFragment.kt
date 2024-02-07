package com.comiccoder.chitramanjari.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.comiccoder.chitramanjari.R
import com.comiccoder.chitramanjari.adapters.AllPostsAdapter
import com.comiccoder.chitramanjari.dataModels.AllPostModel
import com.comiccoder.chitramanjari.database.getAllPosts
import com.comiccoder.chitramanjari.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var postsList: ArrayList<AllPostModel>
    private lateinit var adapter: AllPostsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar)
        setHasOptionsMenu(true)

        getAllPosts {
            postsList = it!!
            postsList.reverse()
            adapter = AllPostsAdapter(requireContext(), postsList)
            binding.allPostsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.allPostsRecyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_fragment_top_options_menu, menu)
    }

    companion object {

    }
}
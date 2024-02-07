package com.comiccoder.chitramanjari.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.comiccoder.chitramanjari.adapters.SearchAdapter
import com.comiccoder.chitramanjari.dataModels.User
import com.comiccoder.chitramanjari.database.getAllUsersData
import com.comiccoder.chitramanjari.database.getIFollow
import com.comiccoder.chitramanjari.database.getUserDataWithId
import com.comiccoder.chitramanjari.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    lateinit var adapter: SearchAdapter
    private var iFollowList: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding.searchEDT.setOnEditorActionListener { textView, _, _ ->
            val searchText = textView.text.toString().trim().lowercase()
            if (searchText.isNotEmpty()) {
                getIFollow { followList ->
                    iFollowList = followList!!
                    getAllUsersData(searchText) {
                        adapter = SearchAdapter(requireContext(), it, iFollowList!!)
                        binding.searchUserRecyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.searchUserRecyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            true
        }

        return binding.root
    }

    companion object {
    }

}
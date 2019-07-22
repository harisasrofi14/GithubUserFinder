package com.example.haris.githubuserfinder.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.haris.githubuserfinder.R
import com.example.haris.githubuserfinder.model.SearchItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_profile.view.*

class RecyclerViewAdapter(private val items:List<SearchItem> ) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
      return ViewHolder(
          LayoutInflater.from(viewGroup.context).inflate(
              R.layout.item_profile,
              viewGroup,
              false
          )
      )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bindResult(items[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName = view.tv_username
        private val ivProfile = view.iv_profile




        fun bindResult (result: SearchItem) {
            Picasso.get().load(result.avatar_url).into(ivProfile)
            tvName.text = result.login

        }
    }

}
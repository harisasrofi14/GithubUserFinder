package com.example.haris.githubuserfinder.presenter

import android.widget.ImageView
import com.example.haris.githubuserfinder.SearchView
import com.example.haris.githubuserfinder.api.ApiRespository
import com.example.haris.githubuserfinder.api.GithubApi
import com.example.haris.githubuserfinder.model.SearchResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchPresenter(private val view: SearchView, private val apiRespository: ApiRespository,
                      private val gson: Gson){

    fun getSearchResult(user:String,page:Int,per_page:Int){
        GlobalScope.launch (Dispatchers.Main){
            val  data = gson.fromJson(apiRespository.doRequest(GithubApi.getGithubProfiles(user,page,per_page)).await(),
                SearchResponse::class.java)
            view.showResultList(data.items!!)
        }
    }
}
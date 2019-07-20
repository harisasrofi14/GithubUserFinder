package com.example.haris.githubuserfinder

import com.example.haris.githubuserfinder.model.SearchItem
import com.example.haris.githubuserfinder.model.SearchResponse

interface SearchView {
    fun showLoading()
    fun hideLoading()
    fun showResultList(data : List<SearchItem>)
}
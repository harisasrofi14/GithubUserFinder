package com.example.haris.githubuserfinder.model
import com.google.gson.annotations.SerializedName
data class SearchResponse(@SerializedName("total_count") val total_count : Int,@SerializedName("incomplete_results") val incomplete_results: Boolean,@SerializedName("items")val items: List<SearchItem>?)
package com.example.haris.githubuserfinder.api

import com.example.haris.githubuserfinder.BuildConfig

object GithubApi {

    fun getGithubProfiles(user:String,page:Int,per_page:Int): String{
        return "${BuildConfig.BASE_URL}/search/users?q=$user&page=$page&per_page=$per_page"
    }
}
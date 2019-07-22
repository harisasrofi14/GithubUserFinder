package com.example.haris.githubuserfinder.network

import com.example.haris.githubuserfinder.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

interface RetrofitService {
    @GET("search/users")
    fun getUserSearch(@Query("q") q: String,
                      @Query("page")page: Int,
                      @Query("per_page")per_page:Int):Observable<SearchResponse>
}
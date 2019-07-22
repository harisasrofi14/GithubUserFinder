package com.example.haris.githubuserfinder.presenter

import android.util.Log
import android.widget.ImageView
import com.example.haris.githubuserfinder.SearchView
import com.example.haris.githubuserfinder.api.ApiRespository
import com.example.haris.githubuserfinder.api.GithubApi
import com.example.haris.githubuserfinder.model.SearchResponse
import com.example.haris.githubuserfinder.network.RetrofitBuilder
import com.example.haris.githubuserfinder.network.RetrofitService
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import java.lang.Exception

class SearchPresenter(private val view: SearchView){

    fun getSearchResult(user:String,page:Int,per_page:Int){
        view.showLoading()
        val retrofit= RetrofitBuilder.getClient().create(RetrofitService::class.java)
        CompositeDisposable().add(
            retrofit.getUserSearch(user,page,per_page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        view.showResultList(it.items!!)
                        view.hideLoading()
                    }, { error ->
                        Log.e("Error", error.message)
                        view.hideLoading()
                        view.onErrorGetProfile()
                    })
        )
    }
}



package com.example.haris.githubuserfinder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.example.haris.githubuserfinder.adapter.RecyclerViewAdapter
import com.example.haris.githubuserfinder.api.ApiRespository
import com.example.haris.githubuserfinder.model.SearchItem
import com.example.haris.githubuserfinder.model.SearchResponse
import com.example.haris.githubuserfinder.presenter.SearchPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import com.facebook.shimmer.ShimmerFrameLayout



class MainActivity : AppCompatActivity(),SearchView {

    private  var profiles : MutableList<SearchItem> = mutableListOf()
    private lateinit var presenter: SearchPresenter
    private val gson = Gson()
    private val apiRepository = ApiRespository()
    private val searchAdapter = RecyclerViewAdapter(profiles)
    private lateinit var mShimmerViewContainer: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container)
        presenter = SearchPresenter(
            this,
            apiRepository,
            gson
        )

        rv_github_profile.apply {
            layoutManager = LinearLayoutManager(context)
        }
        rv_github_profile.adapter = searchAdapter

        edt_search_text.onSubmit {
            presenter.getSearchResult(edt_search_text.text.toString(),1,20)
        }
        mShimmerViewContainer.visibility = View.GONE
    }


    override fun showLoading() {
        mShimmerViewContainer.visibility = View.VISIBLE
        mShimmerViewContainer.startShimmerAnimation()
    }

    override fun hideLoading() {
        mShimmerViewContainer.visibility = View.GONE
        mShimmerViewContainer.stopShimmerAnimation()
    }

    override fun showResultList(data: List<SearchItem>) {
        profiles.clear()
        profiles.addAll(data)
        searchAdapter.notifyDataSetChanged()
        rv_github_profile.scrollToPosition(0)

    }

    private fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                func()
            }

            false

        }
    }
}

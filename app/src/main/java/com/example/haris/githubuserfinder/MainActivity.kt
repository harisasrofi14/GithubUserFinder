package com.example.haris.githubuserfinder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
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
    private var originItems: MutableList<SearchItem> = mutableListOf()
    private lateinit var presenter: SearchPresenter
    private val gson = Gson()
    private val apiRepository = ApiRespository()
    private val searchAdapter = RecyclerViewAdapter(profiles)
    private lateinit var mShimmerViewContainer: ShimmerFrameLayout
    private lateinit var manager: LinearLayoutManager

    private lateinit var scrollListener: RecyclerView.OnScrollListener
    internal var currentItems: Int = 0
    internal var totalItems: Int = 0
    internal var scrollOutItems: Int = 0
    internal var page = 1
    internal var isLoading = false
    private lateinit var keyword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = LinearLayoutManager(this)
        rv_github_profile.layoutManager = manager
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container)
        presenter = SearchPresenter(
            this
        )

        rv_github_profile.adapter = searchAdapter

        edt_search_text.onSubmit {
            wrapper_not_found.visibility= View.GONE
            keyword = edt_search_text.text.toString()
            if (keyword.isEmpty()){
                Toast.makeText(this, resources.getString(R.string.mandatory_filed_warning), Toast.LENGTH_SHORT).show()

            }
            else{
                profiles.clear()
                page = 1
                rv_github_profile.addOnScrollListener(scrollListener)
                presenter.getSearchResult(keyword,page,20)
            }

        }
        mShimmerViewContainer.visibility = View.GONE
        setRecyclerViewScrollListener()

    }


    override fun showLoading() {
        mShimmerViewContainer.visibility = View.VISIBLE
        mShimmerViewContainer.startShimmerAnimation()
        isLoading = true
    }

    override fun hideLoading() {
        mShimmerViewContainer.visibility = View.GONE
        mShimmerViewContainer.stopShimmerAnimation()
        isLoading = false
    }

    override fun onErrorGetProfile() {
        Toast.makeText(this, resources.getString(R.string.error_warning), Toast.LENGTH_LONG).show()
        rv_github_profile.removeOnScrollListener(scrollListener)
    }


    override fun showResultList(data: List<SearchItem>) {
        if (data.isEmpty()){
            wrapper_not_found.visibility= View.VISIBLE
            rv_github_profile.removeOnScrollListener(scrollListener)
        }
        else {
            profiles.addAll(data)
            originItems.addAll(profiles)
            searchAdapter.notifyDataSetChanged()
            page = page.inc()
            rv_github_profile.addOnScrollListener(scrollListener)
        }

    }

    private fun EditText.onSubmit(func: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                func()
            }

            false

        }
    }

    private fun setRecyclerViewScrollListener() {
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(rv_github_profile, newState)

                currentItems = manager.childCount
                totalItems = profiles.size
                scrollOutItems = manager.findFirstVisibleItemPosition()
                val totalItemCount = manager.itemCount
                if (totalItemCount == currentItems + scrollOutItems)
                {

                    rv_github_profile.removeOnScrollListener(scrollListener)
                    Log.e("page: ", page.toString())
                    if(!isLoading) {
                        presenter.getSearchResult(keyword, page, 20)
                    }
                }
            }
        }
    }
}

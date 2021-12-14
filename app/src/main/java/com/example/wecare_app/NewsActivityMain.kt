package com.example.wecare_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_news_main.*
import retrofit2.Call
import retrofit2.Response

class NewsActivityMain : AppCompatActivity() {

    lateinit var layoutManager : LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog: android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_main)

        // Init cache DB
        Paper.init(this)

        // Init Service
        mService = Common.newsService

        // Init View
        swipe_to_refresh.setOnRefreshListener {
            loadWebSiteSource(true)
        }

        recycler_view_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = layoutManager

        dialog = SpotsDialog(this)
        loadWebSiteSource(false)
    }

    private fun loadWebSiteSource(isRefresh: Boolean) {
        if(!isRefresh){
            val cache = Paper.book().read<String>("cache")
            if(cache!=null && !cache.isBlank() && cache!="null"){
                // Read cache
                val webSite = Gson().fromJson<WebSite>(cache,WebSite::class.java)
                adapter = ListSourceAdapter(this,webSite)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter = adapter
            }
            else{
                // Load website and Read cache
                dialog.show()
                //Fetch new data
                mService.sources.enqueue(object : retrofit2.Callback<WebSite>{
                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        adapter = ListSourceAdapter(this@NewsActivityMain , response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter = adapter

                        // Save to cache
                        Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                        dialog.dismiss()
                    }

                    override fun onFailure(call: Call<WebSite>, t: Throwable) {
                        Toast.makeText(baseContext, "Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
        else{
            swipe_to_refresh.isRefreshing = true
            // Fetch new data
            mService.sources.enqueue(object : retrofit2.Callback<WebSite>{
                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    adapter = ListSourceAdapter(this@NewsActivityMain , response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter = adapter

                    // Save to cache
                    Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                    swipe_to_refresh.isRefreshing = false
                }

                override fun onFailure(call: Call<WebSite>, t: Throwable) {
                    Toast.makeText(baseContext, "Failed", Toast.LENGTH_LONG).show()
                }
            })

        }

    }

}

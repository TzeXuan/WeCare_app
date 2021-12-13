package com.example.wecare_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListNews : AppCompatActivity() {

    var source = ""
    var webHotUrl : String?= ""

    lateinit var dialog : android.app.AlertDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        //Init view
        mService = Common.newsService

        dialog = SpotsDialog(this)

        swipe_to_refresh.setOnRefreshListener {
            loadNews(source,true)
        }

        diagonalLayout.setOnClickListener{
            val detail = Intent( this, NewsDetail::class.java)
            detail.putExtra("webURL", webHotUrl)
            startActivity(detail)
        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)

        if (intent!=null){
            source = intent.getStringExtra("source")!!
            if(!source.isEmpty())
                loadNews(source,false)
        }

    }

    private fun loadNews(source: String?, isRefreshed : Boolean) {
        if(isRefreshed){

            dialog.show()
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<News> {
                    override fun onResponse(call: Call<News>?, response: Response<News>?) {
                        dialog.dismiss()

                        // Get the first article to hot news
                        Picasso.with(baseContext)
                            .load(response!!.body()!!.articles!![0].urlToImage)
                            .into(top_image)

                        top_title.text = response!!.body()!!.articles!![0].title
                        top_author.text = response!!.body()!!.articles!![0].author

                        webHotUrl = response!!.body()!!.articles!![0].url

                        // Load all remain articles
                        val removeFirstItem = response!!.body()!!.articles
                        // As we get the first item to the hot news, so we need to remove it
                        removeFirstItem!!.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem,this@ListNews)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                    }

                    override fun onFailure(call: Call<News>, t: Throwable) {

                    }

                })
        }
        else{
            swipe_to_refresh.isRefreshing = true
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<News> {
                    override fun onResponse(call: Call<News>?, response: Response<News>?) {
                        swipe_to_refresh.isRefreshing = false

                        // Get the first article to hot news
                        Picasso.with(this@ListNews)
                            .load(response!!.body()!!.articles!![0].urlToImage)
                            .into(top_image)

                        top_title.text = response!!.body()!!.articles!![0].title
                        top_author.text = response!!.body()!!.articles!![0].author

                        webHotUrl = response!!.body()!!.articles!![0].url

                        // Load all remain articles
                        val removeFirstItem = response.body()!!.articles
                        // As we get the first item to the hot news, so we need to remove it
                        removeFirstItem!!.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem,this@ListNews)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                    }

                    override fun onFailure(call: Call<News>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        }

    }

}

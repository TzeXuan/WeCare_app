package com.example.wecare_app

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    // v2/top-headlines/sources?apiKey=6c941904ae0c4aab93802deae4842cd0
    @get:GET("v2/sources?apiKey=6c941904ae0c4aab93802deae4842cd0")
    val sources: Call<WebSite>

    @GET
    fun getNewsFromSource(@Url url:String): Call<News>
}
package com.example.wecare_app

import java.lang.StringBuilder

object Common {

    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "6c941904ae0c4aab93802deae4842cd0"

    val newsService: NewsService
    get()= RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source: String):String{
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiUrl
    }

}

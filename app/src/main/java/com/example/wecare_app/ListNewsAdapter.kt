package com.example.wecare_app

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ListNewsAdapter(val articleList: MutableList<Article>, private val context: Activity): RecyclerView.Adapter<ListNewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.news_layout, parent, false)
        return ListNewsViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {

        // Load the image
        Picasso.with(context)
            .load(articleList[position].urlToImage)
            .into(holder.article_image)

        if(articleList[position].title!!.length>65){
            holder.article_title.text = articleList[position].title!!.substring(0,65)+"..."
        }
        else{
            holder.article_title.text = articleList[position].title!!
        }

        if (articleList[position].publishedAt!=null){
            val formatter = SimpleDateFormat("yyyy-MM-dd 'T'HH:mm:ssz")
            formatter.timeZone = TimeZone.getTimeZone("UTC")

            val dateInArticle = articleList[position].publishedAt!!.toString()

            val dtStart = dateInArticle
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val date: Date = format.parse(dtStart)

            holder.article_time.setReferenceTime(date.time)
        }

        //When user click to the news and read
        holder.setItemClickListener(object :ItemClickListener{
            override fun onClick(view: View, position: Int) {
                val detail = Intent(context, NewsDetail::class.java)
                detail.putExtra("webURL",articleList[position].url)
                context.startActivity(detail)
            }
        })

    }

    override fun getItemCount(): Int {
        return articleList.size
    }

}
package com.example.wecare_app

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ListSourceAdapter(private val context: Activity, private val webSite: WebSite) : RecyclerView.Adapter<ListSourceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.source_news_layout,parent,false)
        return ListSourceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {
        holder.source_title.text =webSite.sources!![position].name

        holder.setItemClickListener(object: ItemClickListener{

            // function start List News Activity when click on Sources News
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, ListNews::class.java)
                intent.putExtra("source",webSite.sources!![position].id)
                context.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return webSite.sources!!.size
    }


}
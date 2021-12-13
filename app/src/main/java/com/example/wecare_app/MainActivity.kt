package com.example.wecare_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.wecare_app.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }
}


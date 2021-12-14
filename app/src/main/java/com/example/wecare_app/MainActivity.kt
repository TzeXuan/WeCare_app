package com.example.wecare_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       //  val loginn = LogIn()
         //supportFragmentManager.beginTransaction().replace(R.id.fragment_container,loginn).commit() //1


   /* private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(saveInstanceState: Bundle?) {
        super.onCreate(saveInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)*/
    }

    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }

    /*override fun passDataCom(editText: TextInputEditText:String)
    val bundle = Bundle()
    bundle.putString("message",editTextInput)

    val transaction = this.supportFragmentManager.beginTransaction()
    val reset= updatePassword()
    reset.arguements = bundle

    transaction.replace(R.id.fragment_container,reset)
    transaction.commit()*/
}


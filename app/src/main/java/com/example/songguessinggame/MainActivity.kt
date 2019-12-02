package com.example.songguessinggame

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onButtonClick(v: View?) {
        val myIntent = Intent(baseContext, MusicLibraryActivity::class.java)
        startActivity(myIntent)
    }

    fun onButtonMapClick(v: View?) {
        val myIntent = Intent(baseContext, SongsMap::class.java)
        startActivity(myIntent)
    }



}

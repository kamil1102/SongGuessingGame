package com.example.songguessinggame

//import sun.jvm.hotspot.utilities.IntArray

import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class GuessingActivity : AppCompatActivity() {

    var song = Song()



    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guessing)
        song = intent.extras?.get("Song") as Song
        val textView: TextView = findViewById(R.id.txtLyric) as TextView
        textView.text = song.songLyric

        val linearLayout = findViewById(R.id.LL) as LinearLayout

        val btn1Show = Button(this)
        btn1Show.setText(song.songTitle)
        btn1Show.getBackground().setColorFilter(btn1Show.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        btn1Show.setHeight(200);

        btn1Show.setOnClickListener { Toast.makeText(this, "hej", Toast.LENGTH_LONG).show() }

        val btn2Show = Button(this)
        btn2Show.setText(getRandomString())
        //btn2Show.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))

        btn2Show.getBackground().setColorFilter(btn2Show.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        btn2Show.setHeight(200);

        btn2Show.setOnClickListener { Toast.makeText(this, "hej", Toast.LENGTH_LONG).show() }

        val btn3Show = Button(this)
        btn3Show.setText(getRandomString())
        btn3Show.getBackground().setColorFilter(btn2Show.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        btn3Show.setHeight(200);

        btn3Show.setOnClickListener { Toast.makeText(this, "hej", Toast.LENGTH_LONG).show() }


        val mylist = ArrayList<Button>()
        mylist.add(btn1Show)
        mylist.add(btn2Show)
        mylist.add(btn3Show)

        Collections.shuffle(mylist);

        for (i in mylist){
            linearLayout?.addView(i)
        }


    }

    fun getRandomString(): String? {
        val list: MutableList<String> = ArrayList()
        list.add("One")
        list.add("Two")
        list.add("Two3")
        list.add("Two6")
        list.add("Two8")
        val random = list[Random().nextInt(list.size)]
        return random
    }

}

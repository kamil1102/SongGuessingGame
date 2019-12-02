package com.example.songguessinggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MusicLibraryActivity : AppCompatActivity() {


    companion object{
        lateinit var dbHandler: DBHandler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_library)
        dbHandler = DBHandler(this, null, null, 1)



        val newSong4 = Song()
        newSong4.songTitle = "Like w4t2t4 rolling stone"
        newSong4.songArtist = "Bob fwfwefe4Dylan"
        newSong4.songLyric = "Once upon a time you dressed so fine\n" +
                "You threw the bums a dime in your prime,\n" +
                "Didn't you?"
        newSong4.mapLongitude =  -3.879926
        newSong4.mapLatitude = 51.619396
        newSong4.isClassic = 0
        newSong4.isCollected = 0


        dbHandler.addSong(this,newSong4)

        val newSong3 = Song()
        newSong3.songTitle = "stone"
        newSong3.songArtist = "Bob "
        newSong3.songLyric = "Once upon a time you dressed so fine\n" +
                "You threw the bums a dime in your prime,\n" +
                "Didn't you?"
        newSong3.mapLongitude =  -3.876117
        newSong3.mapLatitude = 51.619343
        newSong3.isClassic = 0
        newSong3.isCollected = 0


        dbHandler.addSong(this,newSong3)


        viewSongs()
    }
    private fun viewSongs(){
        val songlist = dbHandler.getSongs(this)
        val adapter = SongAdapter(this,songlist)
        val rv : RecyclerView = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this,
            RecyclerView.AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS, false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }


}

package com.example.songguessinggame

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DBHandler(
    context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?,
    version: Int) :
    SQLiteOpenHelper(context,DATABES_NAME, factory, DATABASE_VERSION){

    companion object{
        private val DATABES_NAME = "SongData.db"
        private val  DATABASE_VERSION = 2


        val SONG_TABLE_NAME = "Songs"
        val COLUMN_SONGID = "songId"
        val COLUMN_SONGTITLE = "songTitle"
        val COLUMN_SONGARTIST = "songArtist"
        val COLUMN_SONG_LYRIC = "songLyric"
        val COLUMN_SONG_ICOLLECTED = "songIsCollected"
        val COLUMN_SONG_ISCLASSIC = "songIsClassic"
        val COLUMN_SONG_LATITUDE = "songLatitude"
        val COLUMN_SONG_LONGITUDENEW = "songLongitudeNew"
        val COLUMN_SONG_LONGITUDE = "songLongitude"


        val PLAYER_TABLE = "Uses"
        val COLUMN_PLAYER_ID = "playerID"
        val COLUMN_PLAYER_NAME = "playerName"
        val COLUMN_PLAYER_POINTS = "playerPoints"
        val COLUMN_PLAYER_LEVEL = "playerLevel"
        val COLUMN_PLAYER_STARS = "playerStars"

    }

    override fun onCreate(db: SQLiteDatabase?) {


        val CREATE_SONG_TABLE : String = ("CREATE TABLE $SONG_TABLE_NAME("+
                "$COLUMN_SONGID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_SONGTITLE TEXT," +
                "$COLUMN_SONGARTIST TEXT,"+
                "$COLUMN_SONG_ICOLLECTED INTEGER,"+
                "$COLUMN_SONG_ISCLASSIC INTEGER,"+
                "$COLUMN_SONG_LONGITUDE FLOAT,"+
                "$COLUMN_SONG_LATITUDE  FLOAT,"+
                "$COLUMN_SONG_LYRIC TEXT)")

        db?.execSQL(CREATE_SONG_TABLE)


        val CREATE_PLAYER_TABLE : String = ("CREATE TABLE $PLAYER_TABLE("+
                "$COLUMN_PLAYER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_PLAYER_NAME TEXT," +
                "$COLUMN_PLAYER_POINTS INTEGER,"+
                "$COLUMN_PLAYER_LEVEL INTEGER,"+
                "$COLUMN_PLAYER_STARS INTEGER)")

        db?.execSQL(CREATE_PLAYER_TABLE)





    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion <2){
            db?.execSQL(" Alter Table $SONG_TABLE_NAME Add  $COLUMN_SONG_LONGITUDENEW FLOAT NULL")
        }

    }

    fun getSongs(mCtx : Context) : ArrayList<Song>{

        val qry = "Select * From $SONG_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry,null)
        val songs = ArrayList<Song>()

        if(cursor.count == 0)
            Toast.makeText(mCtx,"No record Found", Toast.LENGTH_SHORT).show() else{
            cursor.moveToFirst()
        while (!cursor.isAfterLast){
            val song = Song()
           // song.mapLongitudeNew = cursor.getDouble(cursor.getColumnIndex(COLUMN_SONG_LONGITUDENEW))
            song.mapLongitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_SONG_LONGITUDE))
            song.songID = cursor.getInt(cursor.getColumnIndex(COLUMN_SONGID))
            song.songTitle = cursor.getString(cursor.getColumnIndex(COLUMN_SONGTITLE))
            song.songArtist = cursor.getString(cursor.getColumnIndex(COLUMN_SONGARTIST))
            song.songLyric = cursor.getString(cursor.getColumnIndex(COLUMN_SONG_LYRIC))
            song.isCollected = cursor.getInt(cursor.getColumnIndex(COLUMN_SONG_ICOLLECTED))
            song.isClassic = cursor.getInt(cursor.getColumnIndex(COLUMN_SONG_ISCLASSIC))
            song.mapLatitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_SONG_LATITUDE ))




                songs.add(song)

            cursor.moveToNext()

        }
            Toast.makeText(mCtx, "${cursor.count.toString()} Records Found", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return songs

    }


    fun getSongsCollected(mCtx : Context) : ArrayList<Song>{

        val qry = "Select * From $SONG_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry,null)
        val songs = ArrayList<Song>()

        if(cursor.count == 0)
            Toast.makeText(mCtx,"No record Found", Toast.LENGTH_SHORT).show() else{
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                val song = Song()
               // song.mapLongitudeNew = cursor.getDouble(cursor.getColumnIndex(COLUMN_SONG_LONGITUDENEW))
                song.mapLongitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_SONG_LONGITUDE))
                song.songID = cursor.getInt(cursor.getColumnIndex(COLUMN_SONGID))
                song.songTitle = cursor.getString(cursor.getColumnIndex(COLUMN_SONGTITLE))
                song.songArtist = cursor.getString(cursor.getColumnIndex(COLUMN_SONGARTIST))
                song.songLyric = cursor.getString(cursor.getColumnIndex(COLUMN_SONG_LYRIC))
                song.isCollected = cursor.getInt(cursor.getColumnIndex(COLUMN_SONG_ICOLLECTED))
                song.isClassic = cursor.getInt(cursor.getColumnIndex(COLUMN_SONG_ISCLASSIC))
                song.mapLatitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_SONG_LATITUDE ))


                if(song.isCollected==1){
                    songs.add(song)
                }

                cursor.moveToNext()

            }
            Toast.makeText(mCtx, "${cursor.count.toString()} Records Found", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return songs

    }

    fun addSong(mCtx: Context, song: Song){
        val values = ContentValues()
        values.put(COLUMN_SONGTITLE,song.songTitle)
        values.put(COLUMN_SONGARTIST,song.songArtist)
        values.put(COLUMN_SONG_LYRIC,song.songLyric)
        values.put(COLUMN_SONG_ICOLLECTED,song.isCollected)
        values.put(COLUMN_SONG_ISCLASSIC,song.isClassic)
        values.put(COLUMN_SONG_LATITUDE,song.mapLatitude)
        values.put(COLUMN_SONG_LONGITUDE,song.mapLongitude)
        //values.put(COLUMN_SONG_LONGITUDENEW,song.mapLongitudeNew)


        val db = this.writableDatabase
        try {
            db.insert(SONG_TABLE_NAME,null,values)
            Toast.makeText(mCtx, "Song Added", Toast.LENGTH_SHORT).show()
        } catch (e : Exception)
        {
            Toast.makeText(mCtx, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun deleteSong(songID : Int) : Boolean {
        val qry = "Delete From $SONG_TABLE_NAME where $COLUMN_SONGID = $songID"
        val db = this.writableDatabase
        var result: Boolean = false
        try {
            val cursor = db.execSQL(qry)
            result = true

        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error Deleting")
        }
        return result
    }
    fun updadteSong(id: String, isColected : Int ){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_SONG_ICOLLECTED, isColected)
        try{
            db.update(SONG_TABLE_NAME,contentValues,"$COLUMN_SONGID = ?",arrayOf(id))
        }catch (e : Exception){

        }
    }





}
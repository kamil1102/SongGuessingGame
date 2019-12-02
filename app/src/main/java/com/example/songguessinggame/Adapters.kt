package com.example.songguessinggame

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import kotlinx.android.synthetic.main.lo_songs.view.*


class SongAdapter(mCtx : Context, val songs : ArrayList<Song>) : Adapter<SongAdapter.ViewHolder>(){
    val mCtx = mCtx
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val txtSongName = itemView.txtSongName
        val txtSongArtist = itemView.txtSongArtist
        //val txtSongText = itemView.txtSongText
        val btnUpdate = itemView.btnUpdate
        val btnDelete = itemView.btnDelete
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAdapter.ViewHolder {
      val v = LayoutInflater.from(parent.context).inflate(R.layout.lo_songs,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: SongAdapter.ViewHolder, position: Int) {
        val song : Song = songs[position]
        holder.txtSongName.text = song.songTitle
        holder.txtSongArtist.text = song.songArtist
       // holder.txtSongText.text = song.isCollected.toString()
        holder.btnDelete.setOnClickListener{
            val songName = song.songTitle

            if(MusicLibraryActivity.dbHandler.deleteSong(song.songID))
            {
                songs.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position,songs.size)
                Toast.makeText(mCtx,"Customer $songName Deleted",Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(mCtx,"CError Deleting",Toast.LENGTH_SHORT).show()
        }


    }

}





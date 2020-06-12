package com.ruchajoshi.musicapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.row_music.view.*

class MusicAdapter(val music: ArrayList<Int>) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    class MusicViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
     val musicimage: RoundedImageView = itemview.imageAlbumArt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.row_music,parent,false)
        return MusicViewHolder(view)
    }

    override fun getItemCount() = music.size

    override fun onBindViewHolder(holder:MusicViewHolder, position: Int) {
       holder.musicimage.setImageResource(music[position])
    }


}
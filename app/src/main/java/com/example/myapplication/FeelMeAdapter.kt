package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import com.example.myapplication.fragments.*
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

//used for user playlist and for search with recommendations

class FeelMeAdapter(var timer: Timer, val songs: ArrayList<String>, val posts: ArrayList<String>, val imageurl: ArrayList<String>, val activity: MainActivity) :  RecyclerView.Adapter<FeelMeAdapter.Viewholder>(){

    class Viewholder(itemView: View, activity: MainActivity) : RecyclerView.ViewHolder(itemView){
        val txt : TextView = itemView.findViewById(R.id.song_title)
        val image:  ImageView = itemView.findViewById(R.id.song_image)
        val view1= itemView
        val activity1 = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeelMeAdapter.Viewholder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.playlist_song, parent, false)
        if (activity.prevfrag is Playlist) {
            view.findViewById<Button>(R.id.button_favourites_playlistsong).visibility = View.VISIBLE
            view.findViewById<Button>(R.id.button_delete_playlistsong).visibility = View.VISIBLE
        }
        activity.feelMe = true
        return Viewholder(view , activity)
    }

    override fun getItemCount()=posts.size

    override fun onBindViewHolder(holder:FeelMeAdapter.Viewholder, position: Int) {
        Picasso.get().load(imageurl[position]).into(holder.image)
        holder.txt.text = posts[position]

        val view2 : CardView
        view2=holder.view1.findViewById((R.id.song_area))

        view2.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("song", posts[position])
                bundle.putString("image", imageurl[position])
                bundle.putString("songID", activity.moodPlaylist[position])
                //activity.bundleForPlayingSong.putBoolean("playlist", true)
                activity.firstTime = false
                activity.swipeUpBoolean = false
                activity.pos = 0
                activity.feelMe = true
                timer.cancel()
                val playing = Playing_now()
                playing.arguments = bundle
                activity.makeCurrentFragment(playing)
        }

    }
}
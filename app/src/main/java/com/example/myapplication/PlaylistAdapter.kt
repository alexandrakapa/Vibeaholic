package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import com.example.myapplication.fragments.Playing_now
import com.example.myapplication.fragments.Playlist
import com.google.android.gms.common.util.Strings
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_post.view.*

class PlaylistAdapter(val playlists: ArrayList<String>,val posts: ArrayList<String>, val imageurl: ArrayList<String>, val activity: MainActivity) :  RecyclerView.Adapter<PlaylistAdapter.Viewholder>(){

    class Viewholder(itemView: View, activity: MainActivity) : RecyclerView.ViewHolder(itemView){
        val txt : TextView = itemView.findViewById(R.id.firstName)
        val image : ImageView = itemView.findViewById(R.id.home_image)
        val view1= itemView
        val activity1 = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistAdapter.Viewholder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        return Viewholder(view , activity)
    }

    override fun getItemCount()=posts.size

    override fun onBindViewHolder(holder: PlaylistAdapter.Viewholder, position: Int) {
        val picasso = Picasso.get().load(imageurl[position]).into(holder.image)
        holder.txt.text = posts[position]

        val view1 : ImageView
        view1=holder.view1.findViewById((R.id.home_image))
        view1.setOnClickListener {
            val picasso = Picasso.get().load(imageurl[position]).into(holder.image)
            holder.txt.text = posts[position]

            val bundle = Bundle()
            bundle.putString("title", posts[position])
            bundle.putString("playlistID", playlists[position])
            val playlist = Playlist()
            playlist.arguments = bundle
            activity.makeCurrentFragment(playlist)

        }
    }
}
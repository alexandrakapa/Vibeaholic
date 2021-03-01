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
import com.example.myapplication.fragments.Add_Suggest_song_page
import com.example.myapplication.fragments.Playing_now
import com.example.myapplication.fragments.Playlist
import com.squareup.picasso.Picasso

class PostsAdapter(val songs: ArrayList<String>, val posts: ArrayList<String>, val imageurl: ArrayList<String>,  val activity: MainActivity) :  RecyclerView.Adapter<PostsAdapter.Viewholder>(){

    class Viewholder(itemView: View, activity: MainActivity) : RecyclerView.ViewHolder(itemView){
        val txt : TextView = itemView.findViewById(R.id.firstName)
        val image : ImageView =itemView.findViewById(R.id.home_image)
        val dj_txt : TextView = itemView.findViewById(R.id.firstName)
        val dj_image : ImageView =itemView.findViewById(R.id.home_image)
        val view1= itemView
        val activity1 = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapter.Viewholder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        return Viewholder(view, activity)

    }

    override fun getItemCount()=posts.size

    override fun onBindViewHolder(holder: PostsAdapter.Viewholder, position: Int) {
        Picasso.get().load(imageurl[position]).into(holder.image)
        holder.txt.text = posts[position]

        val view1 : ImageView
        view1=holder.view1.findViewById((R.id.home_image))
            view1.setOnClickListener {
                if (activity.onDj) {
                    val bundle = Bundle()
                    bundle.putString("song", posts[position])
                    bundle.putString("image", imageurl[position])
                    bundle.putString("songID", songs[position])
                    val playing = Add_Suggest_song_page()
                    playing.arguments = bundle
                    activity.makeCurrentFragment(playing)
                }
                else {
                    val bundle = Bundle()
                    bundle.putString("song", posts[position])
                    bundle.putString("image", imageurl[position])
                    bundle.putString("songID", songs[position])
                    activity.bundleForPlayingSong.putBoolean("playlist", false)
                    activity.firstTime = false
                    val playing = Playing_now()
                    playing.arguments = bundle
                    activity.makeCurrentFragment(playing)
                }
            }

    }
}
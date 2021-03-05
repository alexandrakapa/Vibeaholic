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

//used for user playlist and for search with recommendations

class DJ_Playlist_page_adapter(val songs: ArrayList<String>, val posts: ArrayList<String>, val imageurl: ArrayList<String>, val activity: MainActivity) :  RecyclerView.Adapter<DJ_Playlist_page_adapter.Viewholder>() {

    class Viewholder(itemView: View, activity: MainActivity) : RecyclerView.ViewHolder(itemView) {
        val txt: TextView = itemView.findViewById(R.id.song_title_dj_search)
        val image: ImageView = itemView.findViewById(R.id.song_image_dj_search)
        val view1 = itemView
        val activity1 = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DJ_Playlist_page_adapter.Viewholder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.dj_playlist_search_song, parent, false)
        return Viewholder(view, activity)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: DJ_Playlist_page_adapter.Viewholder, position: Int) {
        Picasso.get().load(imageurl[position]).into(holder.image)
        holder.txt.text = posts[position]
        val tv =holder.view1.findViewById<TextView>(R.id.song_title_dj_search);
        tv.isSelected = true;
        val but1 : Button
        val but2 : Button
        val but3 : Button
        but1=holder.view1.findViewById<Button>(R.id.button_addsuggest)
        but2=holder.view1.findViewById<Button>(R.id.button_favourites)
        but3=holder.view1.findViewById<Button>(R.id.button_cancel)

        but1.visibility = View.VISIBLE
        but2.visibility = View.VISIBLE
        but3.visibility = View.GONE

        val view2: CardView
        view2 = holder.view1.findViewById((R.id.song_area_dj_search))
        view2.setOnClickListener {
            if (activity.onDj) {
                val bundle = Bundle()
                bundle.putString("song", posts[position])
                bundle.putString("image", imageurl[position])
                bundle.putString("songID", songs[position])
                val playing = Add_Suggest_song_page()
                playing.arguments = bundle
                activity.makeCurrentFragment(playing)
            }
        }

        but3.setOnClickListener{
            // but3.text=""
            but1.visibility = View.VISIBLE
            but2.visibility = View.VISIBLE
            but3.visibility = View.GONE
            //but3.text="Cancel"
        }

        but1.setOnClickListener{
            but1.visibility = View.GONE
            but2.visibility = View.GONE
            but3.visibility = View.VISIBLE
            //but3.text="Cancel"
        }
    }
}
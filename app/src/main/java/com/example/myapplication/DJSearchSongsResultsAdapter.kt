package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import com.example.myapplication.fragments.Add_Suggest_song_page
import com.example.myapplication.fragments.Playing_now
import com.example.myapplication.fragments.Playlist

class DJSearchSongsResultsAdapter(val posts: ArrayList<String>, val activity: MainActivity) :  RecyclerView.Adapter<DJSearchSongsResultsAdapter.Viewholder>(){

    class Viewholder(itemView: View, activity: MainActivity) : RecyclerView.ViewHolder(itemView){
        val txt : TextView = itemView.findViewById(R.id.song_title_dj_search)
        val view1= itemView
        val activity1 = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DJSearchSongsResultsAdapter.Viewholder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.dj_playlist_search_song, parent, false)

       return Viewholder(view , activity)
    }

    override fun getItemCount()=posts.size



    override fun onBindViewHolder(holder:DJSearchSongsResultsAdapter.Viewholder, position: Int) {
        holder.txt.text = posts[position]


        val but1 : Button
        val but2 : Button
        val but3 : Button
        but1=holder.view1.findViewById<Button>(R.id.button_addsuggest)
        but2=holder.view1.findViewById<Button>(R.id.button_favourites)
        but3=holder.view1.findViewById<Button>(R.id.button_cancel)

        but1.visibility = View.VISIBLE
        but2.visibility = View.VISIBLE
        but3.visibility = View.GONE


        val view2 : CardView
        view2=holder.view1.findViewById<CardView>((R.id.song_area_dj_search))
        view2.setOnClickListener {
            val bundle = Bundle()
            //var details = Call.Details()
            bundle.putString("song", posts[position])
            val playing=Add_Suggest_song_page()
            playing.arguments=bundle

            activity.makeCurrentFragment(playing)
            //activity.findViewById<TextView>(R.id.song_title_playing).text = "posts[position]"


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
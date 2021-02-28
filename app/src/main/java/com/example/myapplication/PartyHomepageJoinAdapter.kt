package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import com.example.myapplication.fragments.Add_Suggest_song_page
import com.example.myapplication.fragments.Party_playing_now
import com.example.myapplication.fragments.Playing_now
import com.example.myapplication.fragments.Playlist

//used for user playlist and for search with recommendations

class PartyHomepageJoinAdapter(val posts: ArrayList<String>, val activity: MainActivity) :  RecyclerView.Adapter<PartyHomepageJoinAdapter.Viewholder>(){

    class Viewholder(itemView: View, activity: MainActivity) : RecyclerView.ViewHolder(itemView){
        val txt : TextView = itemView.findViewById(R.id.song_title_home_join)
        val view1= itemView
        val activity1 = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):PartyHomepageJoinAdapter.Viewholder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.party_homepage_join_song_view, parent, false)

        return Viewholder(view , activity)
    }

    override fun getItemCount()=posts.size



    override fun onBindViewHolder(holder:PartyHomepageJoinAdapter.Viewholder, position: Int) {
        holder.txt.text = posts[position]

        val view2 : CardView
        view2=holder.view1.findViewById((R.id.song_area_home_join))
        view2.setOnClickListener {
            val bundle = Bundle()
            //var details = Call.Details()
            bundle.putString("song", posts[position])

            val partyplaying=Party_playing_now()
            partyplaying.arguments=bundle

            activity.makeCurrentFragment(partyplaying)

        }
    }
}
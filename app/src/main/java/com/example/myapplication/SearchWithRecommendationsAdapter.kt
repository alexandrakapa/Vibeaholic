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
import com.example.myapplication.fragments.*

//used for user playlist and for search with recommendations

class SearchWithRecommendationsAdapter(val posts: ArrayList<String>, val activity: MainActivity) :  RecyclerView.Adapter<SearchWithRecommendationsAdapter.Viewholder>(){

    class Viewholder(itemView: View, activity: MainActivity) : RecyclerView.ViewHolder(itemView){
        val txt : TextView = itemView.findViewById(R.id.song_title_recs)
        val view1= itemView
        val activity1 = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchWithRecommendationsAdapter.Viewholder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.search_with_recommendations_view, parent, false)
        return Viewholder(view , activity)
    }

    override fun getItemCount()=posts.size



    override fun onBindViewHolder(holder:SearchWithRecommendationsAdapter.Viewholder, position: Int) {
        holder.txt.text = posts[position]

        val view2 : CardView
        view2=holder.view1.findViewById((R.id.song_area_recs))
        view2.setOnClickListener {
            val bundle = Bundle()
            //var details = Call.Details()
            bundle.putString("song", posts[position])

            val partyplaying=Add_Suggest_song_page()
            val partyplayingplaylist=DJ_search_playlist_list()

            partyplaying.arguments=bundle



            if (activity.prevfrag is Search_with_recommendations){
                activity.makeCurrentFragment(partyplaying)
            }
            else {
                    activity.makeCurrentFragment(partyplayingplaylist)
            }


            //activity.findViewById<TextView>(R.id.song_title_playing).text = "posts[position]"


        }
    }
}
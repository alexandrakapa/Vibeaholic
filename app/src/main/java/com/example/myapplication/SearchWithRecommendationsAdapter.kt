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
import com.squareup.picasso.Picasso

//used for user playlist and for search with recommendations

class SearchWithRecommendationsAdapter(val songs: ArrayList<String>, val posts: ArrayList<String>, val imageurl: ArrayList<String>, val activity: MainActivity) :  RecyclerView.Adapter<SearchWithRecommendationsAdapter.Viewholder>(){

    class Viewholder(itemView: View, activity: MainActivity) : RecyclerView.ViewHolder(itemView){
        val txt : TextView = itemView.findViewById(R.id.song_title_recs)
        val image : ImageView =itemView.findViewById(R.id.song_image_recs)
        val view1= itemView
        val activity1 = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchWithRecommendationsAdapter.Viewholder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.search_with_recommendations_view, parent, false)
        return Viewholder(view , activity)
    }

    override fun getItemCount()=posts.size



    override fun onBindViewHolder(holder:SearchWithRecommendationsAdapter.Viewholder, position: Int) {
        Picasso.get().load(imageurl[position]).into(holder.image)
        holder.txt.text = posts[position]

        val view2 : CardView
        view2=holder.view1.findViewById((R.id.song_area_recs))
        view2.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("song", posts[position])
            bundle.putString("image", imageurl[position])
            bundle.putString("songID", songs[position])

            val partyplaying=Add_Suggest_song_page()

            partyplaying.arguments=bundle

            activity.makeCurrentFragment(partyplaying)


            //activity.findViewById<TextView>(R.id.song_title_playing).text = "posts[position]"


        }
    }
}
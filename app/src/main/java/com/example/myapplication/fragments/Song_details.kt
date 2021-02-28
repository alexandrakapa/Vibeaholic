package com.example.myapplication.fragments

import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.PostsAdapter
import com.example.myapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Song_details.newInstance] factory method to
 * create an instance of this fragment.
 */
class Song_details : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_song_details, container, false)
        var detailsTitle = view.findViewById<TextView>(R.id.songName)
        var officialName= view.findViewById<TextView>(R.id.details_name)
        var artist= view.findViewById<TextView>(R.id.details_artist)
        var album= view.findViewById<TextView>(R.id.details_album)
        var genre= view.findViewById<TextView>(R.id.details_genre)
        var released= view.findViewById<TextView>(R.id.details_released)
        var image= view.findViewById<ImageView>(R.id.details_image)

        val deleteit: ArrayList<String> = ArrayList()
        val bundle = arguments
        val id = bundle?.getString("songID").toString()

        var songDetails: ArrayList<String> = ArrayList()
        var songsBelow:ArrayList<String> = ArrayList()
        var imgurl: ArrayList<String> = ArrayList()
        var database = FirebaseDatabase.getInstance().reference
        var getdata = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                detailsTitle.text = snapshot.child(id).child("Name").value.toString()
                officialName.text = snapshot.child(id).child("Name").value.toString()
                artist.text = snapshot.child(id).child("Artist").value.toString()
                var Album = snapshot.child(id).child("Album").value.toString()
                if (Album == "null")
                    album.text = "No album"
                else
                    album.text = Album
                genre.text = snapshot.child(id).child("Genre").value.toString()
                released.text = snapshot.child(id).child("Released").value.toString()
                var img = snapshot.child(id).child("ImageURL").value.toString()
                Picasso.get().load(img).into(image)


                var songRecomm : ArrayList<String> = ArrayList()
                for (i in 9..15) {
                    var s = "song"+i
                    if (s!=id) {
                        songRecomm.add(s)
                        var sname = snapshot.child(s).child("Name").value.toString()
                        var sartist = snapshot.child(s).child("Artist").value.toString()
                        songDetails.add("$sname - $sartist")
                        var image = snapshot.child(s).child("ImageURL").value.toString()
                        imgurl.add(image)
                    }
                }
                val mRecyclerViewSongDetails : RecyclerView
                mRecyclerViewSongDetails = view.findViewById(R.id.recyclerView_details)
                mRecyclerViewSongDetails.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
                mRecyclerViewSongDetails.adapter= PostsAdapter(songRecomm, songDetails, imgurl, activity as MainActivity)
            }
        }
        database.addValueEventListener(getdata)


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Song_details.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Song_details().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
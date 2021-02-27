package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.Playlist_page_adapter
import com.example.myapplication.PostsAdapter
import com.example.myapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Playlist.newInstance] factory method to
 * create an instance of this fragment.
 */
class Playlist : Fragment() {
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

        val view = inflater.inflate(R.layout.fragment_playlist, container, false)
        val sngtxt=view.findViewById<TextView>(R.id.playlist_title)
        val bundle=arguments
        val title = bundle?.getString("title")
        val playlistID = bundle?.getString("playlistID").toString()
        sngtxt.text = title

        val posts: ArrayList<String> = ArrayList()
        val imageurl: ArrayList<String> = ArrayList()

        var database = FirebaseDatabase.getInstance().reference

        var getdata = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val songs: ArrayList<String> = ArrayList()
                for (i in snapshot.child(playlistID).child("SongArray").children ) {
                    songs.add(i.value.toString())
                }

                for (i in songs) {
                    var songName = snapshot.child(i).child("Name").value.toString()
                    var songArtist = snapshot.child(i).child("Artist").value.toString()
                    var caption: String = "$songName - $songArtist"
                    posts.add(caption)
                    var image = snapshot.child(i).child("ImageURL").value.toString()
                    imageurl.add(image)
                }

                val mRecyclerView: RecyclerView
                mRecyclerView = view.findViewById(R.id.recyclerView_playlist)
                mRecyclerView.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.VERTICAL, false)
                mRecyclerView.adapter= Playlist_page_adapter(songs, posts, imageurl, activity as MainActivity)
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
         * @return A new instance of fragment Playlist.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Playlist().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
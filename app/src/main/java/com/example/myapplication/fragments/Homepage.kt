package com.example.myapplication.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.PlaylistAdapter
import com.example.myapplication.PostsAdapter
import com.example.myapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Homepage.newInstance] factory method to
 * create an instance of this fragment.
 */
class Homepage : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)

        if ((activity as MainActivity).firstTime == true) {
            (activity as MainActivity).bundleForPlayingSong.putString("song", "Physical - Dua Lipa")
            (activity as MainActivity).bundleForPlayingSong.putString("image", "https://firebasestorage.googleapis.com/v0/b/hci-vibeaholic.appspot.com/o/Dua_Lipa_Physical.jpg?alt=media&token=469bb1a0-7603-4bb6-b6cd-affae2158962")
            (activity as MainActivity).bundleForPlayingSong.putString("songID", "song9")
        }

        val posts: ArrayList<String> = ArrayList()
        val posts2: ArrayList<String> = ArrayList()
        val posts3: ArrayList<String> = ArrayList()
        val posts4: ArrayList<String> = ArrayList()
        val imageurl: ArrayList<String> = ArrayList()
        val imageurl2: ArrayList<String> = ArrayList()
        val imageurl3: ArrayList<String> = ArrayList()
        val imageurl4: ArrayList<String> = ArrayList()

        var database = FirebaseDatabase.getInstance().reference

        var getdata = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var recently: ArrayList<String> = ArrayList()
                for (i in snapshot.child("user1").child("Playlists").child("1").child("SongArray").children){
                    recently.add(i.value.toString())
                }

                for (i in recently) {
                    var songName= snapshot.child(i).child("Name").value.toString()
                    var songArtist = snapshot.child(i).child("Artist").value.toString()
                    var caption : String = "$songName - $songArtist"
                    posts.add(caption)
                    var image = snapshot.child(i).child("ImageURL").value.toString()
                    imageurl.add(image)
                }

                val mRecyclerView: RecyclerView
                mRecyclerView = view.findViewById(R.id.recyclerView)
                mRecyclerView.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
                mRecyclerView.adapter= PostsAdapter(recently, posts, imageurl,  activity as MainActivity)


                var suggested: ArrayList<String> = ArrayList()
                var insertions: List<String> = Arrays.asList("playlist1", "playlist2", "playlist5", "playlist3")
                suggested.addAll(insertions)

                for (i in suggested) {
                    var PlaylistsName = snapshot.child(i).child("PlaylistName").value.toString()
                    posts2.add(PlaylistsName)
                    var firstSong = snapshot.child(i).child("SongArray").child("0").value.toString()
                    var image =  snapshot.child(firstSong).child("ImageURL").value.toString()
                    imageurl2.add(image)
                }

                val mRecyclerView2: RecyclerView
                mRecyclerView2 = view.findViewById(R.id.recyclerView2)
                mRecyclerView2.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
                mRecyclerView2.adapter= PlaylistAdapter(suggested, posts2, imageurl2, activity as MainActivity)


                var suggestedsongs: ArrayList<String> = ArrayList()
                var help: List<String> = Arrays.asList("song21", "song12", "song4", "song10", "song2", "song1")
                suggestedsongs.addAll(help)

                for (i in suggestedsongs) {
                    var songName= snapshot.child(i).child("Name").value.toString()
                    var songArtist = snapshot.child(i).child("Artist").value.toString()
                    var caption : String = "$songName - $songArtist"
                    posts3.add(caption)
                    var image = snapshot.child(i).child("ImageURL").value.toString()
                    imageurl3.add(image)
                }

                val mRecyclerView3: RecyclerView
                mRecyclerView3 = view.findViewById(R.id.recyclerView3)
                mRecyclerView3.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
                mRecyclerView3.adapter= PostsAdapter(suggestedsongs, posts3, imageurl3, activity as MainActivity)


                var trending : ArrayList<String> = ArrayList()
                var input: List<String> = Arrays.asList("song11", "song17", "song13", "song9")
                trending.addAll(input)

                for (i in trending) {
                    var songName= snapshot.child(i).child("Name").value.toString()
                    var songArtist = snapshot.child(i).child("Artist").value.toString()
                    var caption : String = "$songName - $songArtist"
                    posts4.add(caption)
                    var image = snapshot.child(i).child("ImageURL").value.toString()
                    imageurl4.add(image)
                }

                val mRecyclerView4: RecyclerView
                mRecyclerView4 = view.findViewById(R.id.recyclerView4)
                mRecyclerView4.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
                mRecyclerView4.adapter= PostsAdapter(trending, posts4, imageurl4, activity as MainActivity)
            }
        }

        database.addValueEventListener(getdata)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /*
        view.findViewById<ImageView>(R.id.home_image5).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playlist())
        }

        view.findViewById<ImageView>(R.id.home_image6).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playlist())
        }
        view.findViewById<ImageView>(R.id.home_image7).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playlist())
        }
        view.findViewById<ImageView>(R.id.home_image8).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playlist())
        }

        view.findViewById<ImageView>(R.id.home_image1).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }
        view.findViewById<ImageView>(R.id.home_image2).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }
        view.findViewById<ImageView>(R.id.home_image3).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }
        view.findViewById<ImageView>(R.id.home_image4).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }
        view.findViewById<ImageView>(R.id.home_image9).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }
        view.findViewById<ImageView>(R.id.home_image10).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }
        view.findViewById<ImageView>(R.id.home_image11).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }
        view.findViewById<ImageView>(R.id.home_image12).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }

        view.findViewById<ImageView>(R.id.home_image13).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }
        view.findViewById<ImageView>(R.id.home_image14).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }
        view.findViewById<ImageView>(R.id.home_image15).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }
        view.findViewById<ImageView>(R.id.home_image16).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Playing_now())
        }

        */
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Homepage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Homepage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
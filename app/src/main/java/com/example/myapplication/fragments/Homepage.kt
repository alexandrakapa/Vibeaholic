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

        val posts: ArrayList<String> = ArrayList()
        val imageurl: ArrayList<String> = ArrayList()

        var database = FirebaseDatabase.getInstance().reference
        var getdata = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                //Log.d("mytag", "SEE HERE")
                val recently: ArrayList<String> = ArrayList()
                for (i in snapshot.child("user1").child("Playlists").child("1").child("SongArray").children){
                    recently.add(i.value.toString())
                    //Log.d("mytag", i.getValue().toString());
                }
                var j=0
                for (i in recently) {
                    var songName= snapshot.child(i).child("Name").value.toString()
                    var songArtist = snapshot.child(i).child("Artist").value.toString()
                    var caption : String = "$songName - $songArtist"
                    var tr = caption.substring(0,4)
                    posts.add(tr)
                    var image = snapshot.child(i).child("ImageURL").value.toString()
                    imageurl.add(image)
                    //Log.d("mytag", posts[j])
                    j++

                }
                val mRecyclerView: RecyclerView
                mRecyclerView = view.findViewById(R.id.recyclerView)
                mRecyclerView.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
                mRecyclerView.adapter= PostsAdapter(posts, activity as MainActivity)
            }
        }
        database.addValueEventListener(getdata)

        //val ref = (activity as MainActivity).posts
        //Log.d("mytag", ref[0])
        /*val posts: ArrayList<String> = ArrayList()
        for (i in 1..100){
            posts.add("Song # $i")
        }*/
        //Log.d("mytag", "SEE HERE")
        /*val mRecyclerView: RecyclerView
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
        mRecyclerView.adapter= PostsAdapter(ref, activity as MainActivity)*/

        val playlists: ArrayList<String> = ArrayList()
        for (i in 1..100){
            playlists.add("Playlist # $i")
        }
        val mRecyclerView2: RecyclerView
        mRecyclerView2 = view.findViewById(R.id.recyclerView2)
        mRecyclerView2.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
        mRecyclerView2.adapter= PlaylistAdapter(playlists, activity as MainActivity)

        val playlists2: ArrayList<String> = ArrayList()
        for (i in 1..100){
            playlists2.add("Song # $i")
        }
        val mRecyclerView3: RecyclerView
        mRecyclerView3 = view.findViewById(R.id.recyclerView3)
        mRecyclerView3.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
        mRecyclerView3.adapter= PostsAdapter(playlists2, activity as MainActivity)

        val playlists3: ArrayList<String> = ArrayList()
        for (i in 1..100){
            playlists3.add("Song # $i")
        }
        val mRecyclerView4: RecyclerView
        mRecyclerView4 = view.findViewById(R.id.recyclerView4)
        mRecyclerView4.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
        mRecyclerView4.adapter= PostsAdapter(playlists3, activity as MainActivity)


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
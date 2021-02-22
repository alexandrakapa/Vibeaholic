package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.PlaylistAdapter
import com.example.myapplication.PostsAdapter
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_homepage.*
import java.util.ArrayList

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
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)

        val posts: ArrayList<String> = ArrayList()
        for (i in 1..100){
            posts.add("Song # $i")
        }
        val mRecyclerView: RecyclerView
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
        mRecyclerView.adapter= PostsAdapter(posts, activity as MainActivity)

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
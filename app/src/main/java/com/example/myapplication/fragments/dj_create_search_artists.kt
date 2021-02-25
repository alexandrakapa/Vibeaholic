package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DJSearchSongsResultsAdapter
import com.example.myapplication.MainActivity
import com.example.myapplication.Playlist_page_adapter
import com.example.myapplication.R
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [dj_create_search_artists.newInstance] factory method to
 * create an instance of this fragment.
 */
class dj_create_search_artists : Fragment() {
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

        val view = inflater.inflate(R.layout.fragment_dj_create_search_artists, container, false)
        val posts: ArrayList<String> = ArrayList() //this will change
        for (i in 1..100){
            posts.add("Song # $i")
        }
        val mRecyclerView: RecyclerView
        mRecyclerView = view.findViewById(R.id.recyclerView_results_artists_dj)
        mRecyclerView.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.VERTICAL, false)
        mRecyclerView.adapter= DJSearchSongsResultsAdapter(posts, activity as MainActivity)
        // Inflate the layout for this fragment

        val editText = view.findViewById<EditText>(R.id.searchbar_artists_dj)

        editText.setText((activity as MainActivity).searchtext)
        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.song_button2_dj).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(dj_create_search_songs())
        }

        view.findViewById<Button>(R.id.artists_button2_dj).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(dj_create_search_artists())
        }

        view.findViewById<Button>(R.id.playlists_button2_dj).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(dj_create_search_playlists())
        }

        val showButton = view.findViewById<Button>(R.id.search_icon_artists_dj)
        val editText = view.findViewById<EditText>(R.id.searchbar_artists_dj)

        showButton.setOnClickListener {

            // Getting the user input
            val txt = editText.text

            // (activity as MainActivity).print((activity as MainActivity).searchtext)

            (activity as MainActivity).searchtext = txt.toString()
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment dj_create_search_artists.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            dj_create_search_artists().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
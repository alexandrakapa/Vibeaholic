package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.Playlist_page_adapter
import com.example.myapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Party_playlist.newInstance] factory method to
 * create an instance of this fragment.
 */
class Party_playlist : Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_party_playlist, container, false)

        val posts: ArrayList<String> = ArrayList() //this will change
        for (i in 1..100){
            posts.add("Song # $i")
        }
        val mRecyclerViewParty: RecyclerView
        mRecyclerViewParty = view.findViewById(R.id.recyclerView_party_playlist)
        mRecyclerViewParty.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.VERTICAL, false)
        mRecyclerViewParty.adapter= Playlist_page_adapter(posts, activity as MainActivity)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<FloatingActionButton>(R.id.pending_suggestions).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Suggestions_page())
        }

        view.findViewById<Button>(R.id.addition).setOnClickListener{
            (activity as MainActivity).makeCurrentFragment(Search_with_recommendations())
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Party_playlist.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Party_playlist().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
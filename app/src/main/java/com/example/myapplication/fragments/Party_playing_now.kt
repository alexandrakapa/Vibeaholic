package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.myapplication.MainActivity

import com.example.myapplication.R
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Party_playing_now.newInstance] factory method to
 * create an instance of this fragment.
 */
class Party_playing_now : Fragment() {
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

        val vw=inflater.inflate(R.layout.fragment_party_playing_now, container, false)

        val myTitle = vw.findViewById<TextView>(R.id.playing_now_title)
        if ((activity as MainActivity).onCreate || (activity as MainActivity).JoinerPlayingNow) {
            myTitle.text = "Playing now in party:"
            (activity as MainActivity).JoinerPlayingNow = false
        }
        else {
            myTitle.text = ""
        }

        val bundle=arguments
        val sngtxt=vw.findViewById<TextView>(R.id.song_title_playing_party)
        val  img = vw.findViewById<ImageView>(R.id.imageView_party)

        val url = bundle?.getString("image")
        val song = bundle?.getString("song")
        //val id = bundle?.getString("songID")
        sngtxt.text = song
        Picasso.get().load(url).into(img)

        //(activity as MainActivity).bundleForPlayingSong.putString("partySongID", id)

        return vw
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.button5_party).setOnClickListener {
            val b=arguments
            val songid = b?.getString("songID")
            var bundle = Bundle()

            bundle.putString("songID", songid)
            val details = Song_details()
            details.arguments = bundle

            (activity as MainActivity).makeCurrentFragment(details)
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Party_playing_now.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Party_playing_now().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
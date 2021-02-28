package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Playing_now.newInstance] factory method to
 * create an instance of this fragment.
 */


class Playing_now : Fragment() {

    lateinit var detector: GestureDetectorCompat
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

        val vw=inflater.inflate(R.layout.fragment_playing_now, container, false)
        val bundle=arguments
        val sngtxt=vw.findViewById<TextView>(R.id.song_title_playing)
        val img = vw.findViewById<ImageView>(R.id.song_Image)
        val url = bundle?.getString("image")
        val song = bundle?.getString("song")
        val id = bundle?.getString("songID")
        Picasso.get().load(url).into(img)
        sngtxt.text = song
        val FromPlaylist= (activity as MainActivity).bundleForPlayingSong?.getBoolean("playlist")
        (activity as MainActivity).bundleForPlayingSong.putString("song", song)
        (activity as MainActivity).bundleForPlayingSong.putString("image", url)
        (activity as MainActivity).bundleForPlayingSong.putString("songID", id)

        return vw
    }


    public fun onTouchEvent(event: MotionEvent?): Boolean {
        return detector.onTouchEvent(event)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.button5).setOnClickListener {
            val bundle=arguments
            val id = bundle?.getString("songID")
            val newbundle = Bundle()
            newbundle.putString("songID", id)
            val details = Song_details()
            details.arguments = newbundle
            (activity as MainActivity).makeCurrentFragment(details)
        }

        view.findViewById<Button>(R.id.play_button).setOnClickListener {
            if((activity as MainActivity).onDj)
            Toast.makeText(activity, "You can't hear a song on DJ mode!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Playing_now.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                Playing_now().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
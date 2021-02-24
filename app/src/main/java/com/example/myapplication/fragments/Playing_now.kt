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
        detector = GestureDetectorCompat(activity as MainActivity, GestureListener())
        val vw=inflater.inflate(R.layout.fragment_playing_now, container, false)
        val bundle=arguments
        val sngtxt=vw.findViewById<TextView>(R.id.song_title_playing)
        sngtxt.text = bundle?.getString("song")
       // sngtxt.text="Hello"
        return vw
    }


    public fun onTouchEvent(event: MotionEvent?): Boolean {
        return detector.onTouchEvent(event)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.button5).setOnClickListener {
            (activity as MainActivity).makeCurrentFragment(Song_details())
        }

        view.findViewById<Button>(R.id.play_button).setOnClickListener {
            if((activity as MainActivity).onDj)
            Toast.makeText(activity, "You can't hear a song on DJ mode!", Toast.LENGTH_SHORT).show()
        }
    }
/*

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myIcon = resources.getDrawable(R.drawable.ic_dj)
        val myIcon2 = resources.getDrawable(R.drawable.ic_home)
        view.findViewById<Button>(R.id.play_button).setOnClickListener {
            if (view.findViewById<Button>(R.id.play_button).background == myIcon2)
            {
                view.findViewById<Button>(R.id.play_button).background = myIcon
                //view.findViewById<Button>(R.id.play_button).setBackgroundColor(17170451)
                view.findViewById<Button>(R.id.play_button).setBackgroundColor(Color.CYAN);

            }
            else {
                view.findViewById<Button>(R.id.play_button).background = myIcon2
                view.findViewById<Button>(R.id.play_button).setBackgroundColor(Color.GRAY)

            }
        }
    }
*/
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

    inner class GestureListener : GestureDetector.SimpleOnGestureListener(){

        private val SWIPE_THESHOLD = 100
        private val SWIPE_VELOVITY_THRESHOLD = 100

        override fun onFling(
                downEvent: MotionEvent?,
                moveEvent: MotionEvent?,
                velocityX: Float,
                velocityY: Float
        ): Boolean {

            var diffX=moveEvent?.x?.minus(downEvent!!.x) ?: 0.0F
            var diffY=moveEvent?.y?.minus(downEvent!!.y) ?: 0.0F

            return if (Math.abs(diffX)>Math.abs(diffY)){
                //left or right swipe
                if (Math.abs(diffX)>SWIPE_THESHOLD && Math.abs(velocityX)>SWIPE_VELOVITY_THRESHOLD){
                    if (diffX>0) {
                        //right swipe
                        this@Playing_now.onSwipeRight()
                    }
                    else {
                        //right swipe
                        this@Playing_now.onSwipeLeft()
                    }
                    true
                }
                else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            }
            else{
                //up or down swipe
                if (Math.abs(diffY)>SWIPE_THESHOLD && Math.abs(velocityY)>SWIPE_VELOVITY_THRESHOLD){
                    if (diffY>0) {
                        //swipe down
                        this@Playing_now.onSwipeTop()
                    }
                    else {
                        //swipe up
                        this@Playing_now.onSwipeBottom()
                    }
                    true
                }
                else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }

            }


        }
    }
    private fun onSwipeBottom() {
        Toast.makeText(activity as MainActivity, "Bottom swipe", Toast.LENGTH_LONG).show()
        (activity as MainActivity).makeCurrentFragment(Playlist())
    }

    private fun onSwipeTop() {
        Toast.makeText(activity as MainActivity, "Top swipe", Toast.LENGTH_LONG).show()
    }

    private fun onSwipeLeft() {
        Toast.makeText(activity as MainActivity, "Left swipe", Toast.LENGTH_LONG).show()
    }

    private fun onSwipeRight() {
        Toast.makeText(activity as MainActivity, "Right swipe", Toast.LENGTH_LONG).show()
    }


}
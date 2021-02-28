package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.search_results.*

class MainActivity : AppCompatActivity(){
    val REQUEST_IMAGE_CAPTURE = 1
    var isItFirstTime = true
    var cameraOn = false
    var micOn = false
    var smartCon = false
    internal lateinit var myDialog : Dialog
    internal lateinit var txt : TextView
    internal lateinit var btnSwitch : Switch
    internal lateinit var btnSwitch2 : Switch
    internal lateinit var btnSwitch3 : Switch
    internal lateinit var btnSwitch4 : Switch
    internal lateinit var btnmenu : Button
    var onDj = false
    var onCreate=false
    var ismenuopen=false
    var isUserDJ = false
    lateinit var prevfrag : Fragment

    var searchtext = "Search song here"
    var bundleForPlayingSong = Bundle()


    private lateinit var detector: GestureDetectorCompat

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnmenu=findViewById<View>(R.id.homepage_menu) as Button


        btnSwitch = findViewById<View>(R.id.switch1) as Switch
        btnSwitch.setOnClickListener {
            if(btnSwitch.isChecked) {
                if (isItFirstTime) {
                    isItFirstTime = false
                    ShowDialog()
                }
                else {
                    if (cameraOn) {
                        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)

                        Toast.makeText(this@MainActivity, "Camera is on", Toast.LENGTH_SHORT).show()
                    }
                    else Toast.makeText(this@MainActivity, "Camera is off", Toast.LENGTH_SHORT).show()
                    if (micOn) Toast.makeText(this@MainActivity, "Microphone is on", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(this@MainActivity, "Microphone is off", Toast.LENGTH_SHORT).show()
                    if (smartCon) Toast.makeText(this@MainActivity, "Smart-watch is connected", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(this@MainActivity, "Smart-watch is not connected", Toast.LENGTH_SHORT).show()
                }
            }
        }


        val homepage=Homepage()
        val search=Search()
        val playing=Playing_now()
        val partyPlaying = Party_playing_now()
        val profile=Profile()
        val dj=DJ()
        val searchWithRecommendations = Search_with_recommendations()
        val partyPlaylistSuggestion = Party_playlist_suggestion()
        val partyPlaylist = Party_playlist()

        makeCurrentFragment(homepage)


        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.ic_home -> {
                    makeCurrentFragment(homepage)
                }
                R.id.ic_search -> {
                    if (!onDj) makeCurrentFragment(search)
                    else makeCurrentFragment(searchWithRecommendations)
                }
                R.id.ic_play_now -> {
                    if (!onDj) {
                        val playingnow = Playing_now()
                        playingnow.arguments = bundleForPlayingSong
                        makeCurrentFragment(playingnow)
                    }
                    else makeCurrentFragment(partyPlaying)
                }
                R.id.ic_profile -> makeCurrentFragment(profile)
                R.id.ic_dj-> {
                    if (!onDj) makeCurrentFragment(dj)
                    else {
                        if (isUserDJ) makeCurrentFragment(partyPlaylist)
                        else makeCurrentFragment(partyPlaylistSuggestion)
                    }
                }
            }
            true
        }

    detector = GestureDetectorCompat(this, GestureListener())

       // val posts: ArrayList<String> = ArrayList()
       // for (i in 1..100){
       //     posts.add("Post # $i")
       // }
      // val mRecyclerView: RecyclerView

       // val view : View
        //view = View.inflate(R.layout.fragment_homepage, this, false)
        //mRecyclerView = view.findViewById(R.id.recyclerView)
      // mRecyclerView = findViewById<RecyclerView> (R.id.recyclerView);
        //mRecyclerView.layoutManager = LinearLayoutManager(this)
        //mRecyclerView.adapter= PostsAdapter(posts)
        //recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter=PostsAdapter(posts)
        findViewById<Button>(R.id.homepage_menu).setOnClickListener {
            if (!ismenuopen){
                ismenuopen=true
                openmenu()
            }
            else{
                ismenuopen=false
                makeCurrentFragment(prevfrag)
            }
        }


    }

   // @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun openmenu( ) {
       if (!onDj) {
           val frag = side_menu()
           //frag.enterTransition = android.R.transition.slide_bottom;
           supportFragmentManager.beginTransaction()
               .add(R.id.fl_wrapper, frag)
               .commit()
       }
       else {
           val frag = Side_menu_dj_mode()
           //frag.enterTransition = android.R.transition.slide_bottom;
           supportFragmentManager.beginTransaction()
               .add(R.id.fl_wrapper, frag)
               .commit()
       }
    }

/*
     fun setUpTabs() {
        val adapter1 = ViewPagerAdapter(supportFragmentManager)
        adapter1.addFragment(search_songs(), "Songs")
        adapter1.addFragment(search_artists(), "Artists")
        adapter1.addFragment(search_playlists(), "Playlists")
       viewpager.adapter=adapter1
        tablayout.setupWithViewPager(viewpager)


    }


 */

    fun ShowDialog() {
        myDialog = Dialog(this)
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        myDialog.setContentView(R.layout.dialog_activity)
        myDialog.setTitle("My First Dialog box")
        btnSwitch2 = myDialog.findViewById<View>(R.id.switch2) as Switch
        btnSwitch3 = myDialog.findViewById<View>(R.id.switch3) as Switch
        btnSwitch4 = myDialog.findViewById<View>(R.id.switch4) as Switch
        btnSwitch2.setOnClickListener {
            if (btnSwitch2.isChecked) {
                btnSwitch2.text = "Yes"
                cameraOn = true
            }
            else {
                btnSwitch2.text = "No"
            }
        }
        btnSwitch3.setOnClickListener {
            if (btnSwitch3.isChecked) {
                btnSwitch3.text = "Yes"
                micOn = true
            }
            else {
                btnSwitch3.text = "No"
            }
        }
        btnSwitch4.setOnClickListener {
            if (btnSwitch4.isChecked) {
                btnSwitch4.text = "Yes"
                smartCon = true
            }
            else {
                btnSwitch4.text = "No"
            }
        }
        txt = myDialog.findViewById<View>(R.id.button_go) as TextView
        txt.isEnabled = true
        txt.setOnClickListener{
            if(btnSwitch2.isChecked) {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)

                Toast.makeText(this@MainActivity, "Camera is on", Toast.LENGTH_SHORT).show()

           }
            else {
                Toast.makeText(this@MainActivity, "Camera is off", Toast.LENGTH_SHORT).show()
            }
            if(btnSwitch3.isChecked) {
                Toast.makeText(applicationContext, "Microphone is on", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this@MainActivity, "Microphone is off", Toast.LENGTH_SHORT).show()
            }
            if(btnSwitch4.isChecked) {
                Toast.makeText(applicationContext, "Smart-watch is connected", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this@MainActivity, "Smart-watch is not connected", Toast.LENGTH_SHORT).show()
            }
                myDialog.cancel()
        }
        myDialog.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

       // if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //val imageBitmap = data!!.extras!!.get("data") as Bitmap
            //findViewById<ImageView>(R.id.imageView).setImageBitmap(imageBitmap)
       // }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (detector.onTouchEvent(event)){
            true
        }
        else {
            super.onTouchEvent(event)
        }
    }

    public fun makeCurrentFragment(fragment: Fragment) {

        prevfrag = fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
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
                        this@MainActivity.onSwipeRight()
                    }
                    else {
                        //right swipe
                        this@MainActivity.onSwipeLeft()
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
                        this@MainActivity.onSwipeTop()
                    }
                    else {
                        //swipe up
                        this@MainActivity.onSwipeBottom()
                    }
                    true
                }
                else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }

            }


        }
    }


    fun print (what : String){
        Toast.makeText(this, what, Toast.LENGTH_LONG).show()
    }


    private fun onSwipeBottom() {
        Toast.makeText(this, "Bottom swipe", Toast.LENGTH_LONG).show()
        if (prevfrag !is Playing_now && prevfrag !is Party_playing_now) {
            return
        }
        if (!onDj) {
            makeCurrentFragment(Playlist())
        }
        else if (onDj && onCreate) {
            makeCurrentFragment(Party_playlist())
        }
        else {
            makeCurrentFragment(Party_playlist_suggestion())
        }
    }

    private fun onSwipeTop() {
        Toast.makeText(this, "Top swipe", Toast.LENGTH_LONG).show()
    }

    private fun onSwipeLeft() {
        Toast.makeText(this, "Left swipe", Toast.LENGTH_LONG).show()
    }

    private fun onSwipeRight() {
        Toast.makeText(this, "Right swipe", Toast.LENGTH_LONG).show()
    }

}
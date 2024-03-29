package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.fragments.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    var isItFirstTime = true
    var cameraOn = false
    var micOn = false
    var smartCon = false
    internal lateinit var myDialog: Dialog
    internal lateinit var txt: TextView
    internal lateinit var btnSwitch: Switch
    internal lateinit var btnSwitch2: Switch
    internal lateinit var btnSwitch3: Switch
    internal lateinit var btnSwitch4: Switch
    internal lateinit var btnmenu: Button
    var onDj = false
    var onCreate = false
    var ismenuopen = false
    var isUserDJ = false
    lateinit var prevfrag: Fragment
    //lateinit var prevprevfrag : Fragment
    //lateinit var prevprevprevfrag: Fragment
    var fragmentStack: Stack<Fragment> = Stack()
    //var first = true
    //var second = true
    //var third = true
    var inplayingnow=false
    var fromBackButton = false
    var fromMenuBack = false
    var firstBack = true
    var moodArray:List<String> = listOf<String>("Relaxed", "Neutral", "Happy", "Sad")
    var moodPlaylist : ArrayList<String> = ArrayList()
    var pos = 0
    var feelMe = false

    var searchtext = "Search song here"
    var bundleForPlayingSong = Bundle()
    var bundleForPlayingSongParty = Bundle()
    var firstTime = true
    var swipeUpBoolean = false
    var JoinerPlayingNow = false

    fun CreateTimer(): Timer {
        val timer = Timer("timer", true)
        return timer
    }

    private lateinit var detector: GestureDetectorCompat

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnmenu = findViewById<View>(R.id.homepage_menu) as Button


        btnSwitch = findViewById<View>(R.id.switch1) as Switch
        btnSwitch.setOnClickListener {
            if (btnSwitch.isChecked) {
                feelMe = true
                if (isItFirstTime) {
                    isItFirstTime = false
                    ShowDialog()
                } else {
                    if (cameraOn) {
                        //val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        //startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)

                        Toast.makeText(this@MainActivity, "Camera is on", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(this@MainActivity, "Camera is off", Toast.LENGTH_SHORT).show()
                    if (micOn) Toast.makeText(this@MainActivity, "Microphone is on", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(this@MainActivity, "Microphone is off", Toast.LENGTH_SHORT).show()
                    if (smartCon) Toast.makeText(this@MainActivity, "Smart-watch is connected", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(this@MainActivity, "Smart-watch is not connected", Toast.LENGTH_SHORT).show()
                }
            }
            if (!btnSwitch.isChecked) {
                feelMe = false
                Toast.makeText(this@MainActivity, "Camera is off", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, "Microphone is off", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, "Smart-watch is not connected", Toast.LENGTH_SHORT).show()
            }
        }


        val homepage = Homepage()
        val search = Search()
        val profile = Profile()
        val dj = DJ()
        val searchWithRecommendations = Search_with_recommendations()
        val partyPlaylistSuggestion = Party_playlist_suggestion()
        val partyPlaylist = Party_playlist()

        makeCurrentFragment(homepage)


        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    makeCurrentFragment(homepage)
                    inplayingnow=false
                }
                R.id.ic_search -> {
                    if (!onDj) makeCurrentFragment(search)
                    else makeCurrentFragment(searchWithRecommendations)
                    inplayingnow=false
                }
                R.id.ic_play_now -> {
                    inplayingnow=true
                    if (firstTime || (onDj && !onCreate)) {
                        firstTime = false
                        if (onDj) {
                            var bundle = Bundle()
                            bundle.putString("song", "Physical - Dua Lipa")
                            bundle.putString("image", "https://firebasestorage.googleapis.com/v0/b/hci-vibeaholic.appspot.com/o/Dua_Lipa_Physical.jpg?alt=media&token=469bb1a0-7603-4bb6-b6cd-affae2158962")
                            bundle.putString("songID", "song9")
                            bundleForPlayingSongParty.putString("song", "Physical - Dua Lipa")
                            bundleForPlayingSongParty.putString("image", "https://firebasestorage.googleapis.com/v0/b/hci-vibeaholic.appspot.com/o/Dua_Lipa_Physical.jpg?alt=media&token=469bb1a0-7603-4bb6-b6cd-affae2158962")
                            bundleForPlayingSongParty.putString("songID", "song9")
                            if (!onCreate) {
                                JoinerPlayingNow = true
                            }
                            val playingnow = Party_playing_now()
                            playingnow.arguments = bundle
                            makeCurrentFragment(playingnow)
                        } else {
                            val playingnow = Playing_now()
                            playingnow.arguments = bundleForPlayingSong
                            makeCurrentFragment(playingnow)
                        }
                    } else {
                        if (!onDj) {
                            val playingnow = Playing_now()
                            playingnow.arguments = bundleForPlayingSong
                            makeCurrentFragment(playingnow)
                        } else {
                            val partyPlaying = Party_playing_now()
                            partyPlaying.arguments = bundleForPlayingSongParty
                            makeCurrentFragment(partyPlaying)
                        }
                    }
                }
                R.id.ic_profile -> {
                    makeCurrentFragment(profile)
                    inplayingnow=false
                }
                R.id.ic_dj -> {
                    inplayingnow=false
                    if (!onDj) makeCurrentFragment(dj)
                    else {
                        if (onCreate)
                            makeCurrentFragment(partyPlaylist)
                        else
                            makeCurrentFragment(partyPlaylistSuggestion)
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
            if (!ismenuopen) {
                ismenuopen = true
                openmenu()
            } else {
                ismenuopen = false
                makeCurrentFragment(prevfrag)
            }
        }


    }

    // @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun openmenu() {
        if (!onDj) {
            val frag = side_menu()
            //frag.enterTransition = android.R.transition.slide_bottom;
            supportFragmentManager.beginTransaction()
                    .add(R.id.fl_wrapper, frag)
                    .commit()
        } else {
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
            } else {
                btnSwitch2.text = "No"
            }
        }
        btnSwitch3.setOnClickListener {
            if (btnSwitch3.isChecked) {
                btnSwitch3.text = "Yes"
                micOn = true
            } else {
                btnSwitch3.text = "No"
            }
        }
        btnSwitch4.setOnClickListener {
            if (btnSwitch4.isChecked) {
                btnSwitch4.text = "Yes"
                smartCon = true
            } else {
                btnSwitch4.text = "No"
            }
        }
        txt = myDialog.findViewById<View>(R.id.button_go) as TextView
        txt.isEnabled = true
        txt.setOnClickListener {
            if (btnSwitch2.isChecked) {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)

                Toast.makeText(this@MainActivity, "Camera is on", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this@MainActivity, "Camera is off", Toast.LENGTH_SHORT).show()
            }
            if (btnSwitch3.isChecked) {
                Toast.makeText(applicationContext, "Microphone is on", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity, "Microphone is off", Toast.LENGTH_SHORT).show()
            }
            if (btnSwitch4.isChecked) {
                Toast.makeText(applicationContext, "Smart-watch is connected", Toast.LENGTH_LONG).show()
            } else {
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
        return if (detector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    public fun makeCurrentFragment(fragment: Fragment) {
        /*if (!first && !second) {
            prevprevprevfrag=prevprevfrag
            prevprevfrag = prevfrag
            third=false
           // Toast.makeText(this, "yo", Toast.LENGTH_SHORT).show()
        }
        else if (first and second){
            first = false
        }
        else{
            second=false
            prevprevfrag = prevfrag
        }*/

        if (fromBackButton) {
            fromBackButton = false
            firstBack = false
            if (!onDj && fragment is Homepage) {
                if (prevfrag != fragment)
                    fragmentStack.push(fragment)
            }
            else if (onDj && onCreate && fragment is Party_playlist) {
                if (prevfrag != fragment)
                    fragmentStack.push(fragment)
            }
            else if (onDj && !onCreate && fragment is Party_playlist_suggestion){
                if (prevfrag != fragment)
                    fragmentStack.push(fragment)
            }
            else {
                if (fragmentStack.empty())
                    fragmentStack.push(fragment)
            }
        }
        else {
            firstBack = true
            if (fragment is Homepage && !onDj) {
                while (!fragmentStack.empty()) {
                    fragmentStack.pop()
                }
            }
            if (fragment is Party_playlist && onDj && onCreate) {
                while (!fragmentStack.empty()) {
                    fragmentStack.pop()
                }
            }
            if (fragment is Party_playlist_suggestion && onDj && !onCreate) {
                while (!fragmentStack.empty()) {
                    fragmentStack.pop()
                }
            }


            if (fragment is side_menu
                    || fragment is Side_menu_dj_mode
                    || fragment is DJ
                    || fragment is Profile
                    || fragment is Search
                    || fragment is Search_with_recommendations
                    || fragment is Enter_event_code
                    || fragment is Party_spec
                    ||(fragment is Party_playing_now && inplayingnow)
                    ||(fragment is Playing_now && inplayingnow)
                    ||(fragment is Playlist && feelMe)
                    //|| fragment is search_artists
                    //|| fragment is search_playlists
                    //|| fragment is dj_create_search_artists
                    //|| fragment is dj_create_search_playlists
            ) {}
            else if (fromMenuBack) {
                fromMenuBack = false
            }
            else {
                fragmentStack.push(fragment)
            }
        }

        prevfrag = fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_THESHOLD = 100
        private val SWIPE_VELOVITY_THRESHOLD = 100

        override fun onFling(
                downEvent: MotionEvent?,
                moveEvent: MotionEvent?,
                velocityX: Float,
                velocityY: Float
        ): Boolean {

            var diffX = moveEvent?.x?.minus(downEvent!!.x) ?: 0.0F
            var diffY = moveEvent?.y?.minus(downEvent!!.y) ?: 0.0F

            return if (Math.abs(diffX) > Math.abs(diffY)) {
                //left or right swipe
                if (Math.abs(diffX) > SWIPE_THESHOLD && Math.abs(velocityX) > SWIPE_VELOVITY_THRESHOLD) {
                    if (diffX > 0) {
                        //right swipe
                        //   this@MainActivity.onSwipeRight()
                    } else {
                        //right swipe
                        //  this@MainActivity.onSwipeLeft()
                    }
                    true
                } else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            } else {
                //up or down swipe
                if (Math.abs(diffY) > SWIPE_THESHOLD && Math.abs(velocityY) > SWIPE_VELOVITY_THRESHOLD) {
                    if (diffY > 0) {
                        //swipe down
                        //   this@MainActivity.onSwipeTop()
                    } else {
                        //swipe up
                        this@MainActivity.onSwipeBottom()
                    }
                    true
                } else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }

            }


        }
    }


    fun print(what: String) {
        Toast.makeText(this, what, Toast.LENGTH_LONG).show()
    }


    private fun onSwipeBottom() {
        //Toast.makeText(this, "Bottom swipe", Toast.LENGTH_LONG).show()
        if (prevfrag !is Playing_now && prevfrag !is Party_playing_now) {
            return
        }
        if (!onDj) {
            var bundle = Bundle()
            var checker = bundleForPlayingSong.getBoolean("playlist")
            var FromSearch = bundleForPlayingSong.getBoolean("fromSearch")
            var songId = bundleForPlayingSong.getString("songID").toString()
            var playId: String = String()


            if (checker && !FromSearch) {
                playId = bundleForPlayingSong.getString("playlistID").toString()
                var ref = FirebaseDatabase.getInstance().reference

                var getsongs = object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var title = snapshot.child(playId).child("PlaylistName").value.toString()
                        bundle.putString("title", title)

                        bundle.putString("playlistID", playId)
                        bundle.putString("playingNowSong", songId)
                        swipeUpBoolean = true

                        val pl = Playlist()
                        pl.arguments = bundle

                        makeCurrentFragment(pl)
                    }
                }
                ref.addValueEventListener(getsongs)

            } else {
                var database = FirebaseDatabase.getInstance().reference

                var getPlaylistid = object : ValueEventListener {

                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {

                        for (i in snapshot.children) {
                            if ((i.key.toString()).contains("playlist")) {
                                for (j in i.child("SongArray").children) {
                                    if (j.value.toString() == songId) {
                                        playId = i.key.toString()
                                        break
                                    }
                                }
                            }
                            if (!playId.isEmpty()) {
                                break
                            }
                        }

                        if (playId.isEmpty()) {
                            playId = "playlist3"
                        }

                        var title = snapshot.child(playId).child("PlaylistName").value.toString()
                        bundle.putString("title", title)
                        bundle.putString("playlistID", playId)
                        bundle.putString("playingNowSong", songId)
                        swipeUpBoolean = true

                        val pl = Playlist()
                        pl.arguments = bundle

                        makeCurrentFragment(pl)
                    }
                }
                database.addValueEventListener(getPlaylistid)
            }
        } else if (onDj && onCreate) {
            makeCurrentFragment(Party_playlist())
        } else {
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


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //do your stuff
            if ((prevfrag is Homepage && !onDj && !ismenuopen) || (prevfrag is Party_playlist_suggestion && onDj && !onCreate && !ismenuopen) || (prevfrag is Party_playlist && onDj && onCreate && !ismenuopen))
                return super.onKeyDown(keyCode, event)

            if (fragmentStack.empty())
                return super.onKeyDown(keyCode, event)

            if (ismenuopen){
                ismenuopen=false
                fromMenuBack = true
                makeCurrentFragment(prevfrag)
                return true
            }
            else {
                if (firstBack) {
                    if (prevfrag is side_menu
                            || prevfrag is Side_menu_dj_mode
                            || prevfrag is Party_playlist_suggestion
                            || prevfrag is DJ
                            || prevfrag is Profile
                            || prevfrag is Search
                            || prevfrag is Search_with_recommendations
                            || prevfrag is Enter_event_code
                            || prevfrag is Party_spec
                            ||(prevfrag is Party_playing_now && inplayingnow)
                            ||(prevfrag is Playing_now && inplayingnow)
                            ||(prevfrag is Playlist && feelMe)
                    //|| fragment is search_artists
                    //|| fragment is search_playlists
                    //|| fragment is dj_create_search_artists
                    //|| fragment is dj_create_search_playlists
                    ) {}
                    else {
                        var home = fragmentStack.pop()
                        if (fragmentStack.empty()) {
                            fromBackButton = true
                            makeCurrentFragment(home)
                            return true
                        }
                    }
                }
                    var prev = fragmentStack.pop()
                    fromBackButton = true
                    makeCurrentFragment(prev)
                    return true

            }
            /*if (prevfrag is Homepage || prevfrag is Party_playlist || prevfrag is Party_playlist_suggestion || prevfrag is DJ || prevfrag is Profile || prevfrag is Search || (prevfrag is Party_playing_now && inplayingnow) || ( prevfrag is Playing_now && inplayingnow) || prevfrag is Search_with_recommendations)
                return super.onKeyDown(keyCode, event)

            if (!first and !second and !third) {
                if ( prevfrag != prevprevprevfrag) {
                    makeCurrentFragment(prevprevfrag)
                    return true
                }
            }*/
        }
            return super.onKeyDown(keyCode, event)
    }




}
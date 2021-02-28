package com.example.myapplication.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.LocaleDateTimeFormat
import com.example.myapplication.MainActivity
import com.example.myapplication.PlaylistAdapter
import com.example.myapplication.PostsAdapter
import com.example.myapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
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

val deleteit: ArrayList<String> = ArrayList()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        //we assume user1 is logged in
        val user: String = "user1"

        val title= view.findViewById<TextView>(R.id.PersonName)
        val email= view.findViewById<TextView>(R.id.profile_email)
        val birthday= view.findViewById<TextView>(R.id.profile_birthday)
        val fb= view.findViewById<TextView>(R.id.profile_fb)
        val insta= view.findViewById<TextView>(R.id.profile_insta)
        val photo= view.findViewById<ImageView>(R.id.profile_image)

        val emotions = listOf<String>("Happy", "Sad", "Relaxed", "Stressed", "Neutral")

        val chartWeek = view.findViewById<AnyChartView>(R.id.mood_stats_1)
        var pieWeek = AnyChart.pie()
        val Week = listOf<Int>(1, 0, 2, 0, 1)
        val dataentry: ArrayList<DataEntry> = ArrayList()

        for (i in 0..4) {
            var m = ValueDataEntry(emotions[i], Week[i])
            dataentry.add(m)
        }
        pieWeek.data(dataentry)
        chartWeek.setChart(pieWeek)

        val chartMonth= view.findViewById<AnyChartView>(R.id.mood_stats_2)
        var pieMonth = AnyChart.pie()
        val Month = listOf<Int>(1, 1, 2, 0, 1)
        val dataentry2: ArrayList<DataEntry> = ArrayList()

        for (i in 0..4) {
            var m = ValueDataEntry(emotions[i], Month[i])
            dataentry2.add(m)
        }
        pieMonth.data(dataentry2)
        chartMonth.setChart(pieMonth)

        val standardplaylists: ArrayList<String> = ArrayList()
        val imageurl1: ArrayList<String> = ArrayList()
        val playlistid1: ArrayList<String> = ArrayList()
        val playlists2: ArrayList<String> = ArrayList()
        val imageurl2: ArrayList<String> = ArrayList()
        val playlistid2: ArrayList<String> = ArrayList()

        var database = FirebaseDatabase.getInstance().reference

        var getdata = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                var FirstName = snapshot.child(user).child("Name").value.toString()
                var LastName = snapshot.child(user).child("LastName").value.toString()
                var FullName = FirstName + " " + LastName
                var emaildata = snapshot.child(user).child("Email").value.toString()
                var birthdaydata = snapshot.child(user).child("Birthday").value.toString()
                var facebook = snapshot.child(user).child("Facebook").value.toString()
                var instagram = snapshot.child(user).child("Instagram").value.toString()
                var picture = snapshot.child(user).child("Photo").value.toString()
                title.text = FullName
                email.text = emaildata
                birthday.text = birthdaydata
                fb.text = facebook
                insta.text = instagram
                Picasso.get().load(picture).into(photo)

                for (i in snapshot.child(user).child("Playlists").children) {
                    var name= i.child("PlaylistName").value.toString()
                    standardplaylists.add(name)
                    var id = "$user/Playlists/" + i.key.toString()
                    playlistid1.add(id)
                    for (j in i.child("SongArray").children) {
                        var song = j.value.toString()
                        var image = snapshot.child(song).child("ImageURL").value.toString()
                        imageurl1.add(image)
                        break
                    }
                }

                val mRecyclerView: RecyclerView
                mRecyclerView = view.findViewById(R.id.standard_playlists_profile)
                mRecyclerView.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
                mRecyclerView.adapter = PlaylistAdapter(playlistid1, standardplaylists, imageurl1, activity as MainActivity)


                for (i in snapshot.child(user).child("SavedPlaylists").children) {
                    var id = i.value.toString()
                    playlistid2.add(id)
                }
                for (i in playlistid2) {
                    var PlaylistsName = snapshot.child(i).child("PlaylistName").value.toString()
                    playlists2.add(PlaylistsName)
                    for (j in snapshot.child(i).child("SongArray").children) {
                        var song = j.value.toString()
                        var image = snapshot.child(song).child("ImageURL").value.toString()
                        imageurl2.add(image)
                        break
                    }
                }

                val mRecyclerView2: RecyclerView
                mRecyclerView2 = view.findViewById(R.id.recyclerView_profile)
                mRecyclerView2.layoutManager = LinearLayoutManager(activity as MainActivity, RecyclerView.HORIZONTAL, false)
                mRecyclerView2.adapter= PlaylistAdapter(playlistid2, playlists2, imageurl2, activity as MainActivity)

            }


        }

        database.addValueEventListener(getdata)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
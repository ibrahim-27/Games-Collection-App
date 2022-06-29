package com.example.booksapp

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.booksapp.adapter.gameAdapter
import com.example.booksapp.databinding.ActivityMainBinding
import com.example.booksapp.models.Game
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

/** Global Variables **/
lateinit var currGame:Game
var gameList = ArrayList<Game>()
var favList = ArrayList<Game>()
lateinit var adapter:gameAdapter

class MainActivity : AppCompatActivity(), gameAdapter.OnItemClick {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter = gameAdapter(gameList, this, this)

        val database = Firebase.database
        var myRef = database.getReference("Games")

        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var test:String? = ""
                for(value in snapshot.children){
                    var developer = value.child("developer").getValue<String>()
                    var freetogame_profile_url = value.child("freetogame_profile_url").getValue<String>()
                    var game_url = value.child("game_url").getValue<String>()
                    var genre = value.child("genre").getValue<String>()
                    var id = value.child("id").getValue<Long>()
                    var platform = value.child("platform").getValue<String>()
                    var publisher = value.child("publisher").getValue<String>()
                    var release_date = value.child("release_date").getValue<String>()
                    var short_description = value.child("short_description").getValue<String>()
                    var thumbnail = value.child("thumbnail").getValue<String>()
                    var title = value.child("title").getValue<String>()
                    gameList.add(
                        Game(
                            developer,
                            freetogame_profile_url,
                            game_url,
                            genre,
                            id,
                            platform,
                            publisher,
                            release_date,
                            short_description,
                            thumbnail,
                            title,
                            false
                        )
                    )
                }

                binding.recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })


        //Toast.makeText(this, gameList.size.toString(), Toast.LENGTH_SHORT).show()


        binding.recyclerView.adapter = adapter

        /** For linear layout **/
        /* var linearLayout = LinearLayoutManager(this)
           binding.recyclerView.layoutManager = linearLayout */

        /** For grid layout **/
        var gridLayout = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = gridLayout

//        binding.recyclerView.setOnClickListener()
//        {
//            val i = Intent(this@MainActivity, GameDeatil::class.java)
//            this@MainActivity.startActivity(i)
//        }

    }

    override fun onItemClick(pos: Int) {
        currGame = gameList.get(pos)

        val i = Intent(this, GameDeatil::class.java)
        this.startActivity(i)
        //Toast.makeText(this, currGame.title, Toast.LENGTH_SHORT).show()
    }

    override fun onFavClick(currBtn: CheckBox, pos: Int) {
        if(currBtn.isChecked){
            favList.add(gameList.get(pos))
            gameList.get(pos).isFav = true
            currBtn.setButtonDrawable(R.drawable.filled_heart)
            //Toast.makeText(this, pos.toString(), Toast.LENGTH_SHORT).show()
        }
        else{
            favList.remove(gameList.get(pos))
            gameList.get(pos).isFav = false
            currBtn.setButtonDrawable(R.drawable.heart)
            //Toast.makeText(this, pos.toString(), Toast.LENGTH_SHORT).show()
        }


    }
}
package com.example.booksapp

import android.content.Intent
import android.content.SharedPreferences
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

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
        supportActionBar?.hide()

        /** Getting data from firebase **/
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
                loadData()
                binding.recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })

        adapter = gameAdapter(gameList, this, this)
        binding.recyclerView.adapter = adapter

        /** For grid layout **/
        var gridLayout = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = gridLayout


        binding.showFav.setOnClickListener()
        {
            if (binding.showFav.isChecked)
            {
                adapter.games = favList
                binding.recyclerView.adapter = adapter
            }
            else
            {
                adapter.games = gameList
                binding.recyclerView.adapter = adapter
            }
        }

    }

    /** Functions to handle recyclerview events **/
    // When an item is clicked
    override fun onItemClick(pos: Int) {
        currGame = gameList.get(pos)

        val i = Intent(this, GameDeatil::class.java)
        this.startActivity(i)
    }


    // When the favourite button is clicked
    override fun onFavClick(currBtn: CheckBox, pos: Int) {
        if(currBtn.isChecked){
            gameList.get(pos).isFav = true
            favList.add(gameList.get(pos))
            currBtn.setButtonDrawable(R.drawable.filled_heart)
            saveData()
        }
        else{

            // if the button is clicked when only favourites are shown
            if(binding.showFav.isChecked)
            {
                favList.get(pos).isFav = false
                for (i in 0..gameList.size - 1)
                {
                    if(favList[pos].id == gameList[i].id){
                        gameList[i].isFav = false
                        break
                    }
                }

                favList.removeAt(pos)
                binding.recyclerView.adapter = adapter
            }
            else{
                // removing the game from favourites
                for(i in 0..favList.size-1)
                {
                    if(favList[i].id == gameList[pos].id){
                        favList.removeAt(i)
                        break
                    }
                }
                gameList.get(pos).isFav = false
            }

            currBtn.setButtonDrawable(R.drawable.heart)
            saveData()
        }

    }

    /** Functions related to SharedPreference **/
    // Loading data from SharedPreference - loading the favList items
    fun loadData()
    {
        var sp = applicationContext.getSharedPreferences("DATA", MODE_PRIVATE)
        var gson = Gson()
        var json:String? = sp.getString("FavList", "")
        var type: Type = object: TypeToken<ArrayList<Game>>() {}.type
        if (json != null) {
            if(json.isEmpty())
                return
        }
        favList = gson.fromJson(json, type)

        // setting all fav item's isFav true
        for (i in gameList)
        {
            for (j in favList)
            {
                if(i.id == j.id)
                    i.isFav = true
            }
        }
    }

    // Saving data in SharedPreference - saving the favList items
    fun saveData()
    {
        var sp = applicationContext.getSharedPreferences("DATA", MODE_PRIVATE)
        var editor = sp.edit()
        editor.clear()
        var gson = Gson()
        var json:String = gson.toJson(favList)
        editor.putString("FavList", json)
        editor.apply()
    }
}
package com.example.booksapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


class GameDeatil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_deatil)
        supportActionBar?.hide()
        //Toast.makeText(this, currGame.developer, Toast.LENGTH_SHORT).show()
        var img = findViewById<ImageView>(R.id.gameImg)
        var title = findViewById<TextView>(R.id.tvGameTitle)
        var releaseDate = findViewById<TextView>(R.id.tvReleaseDate)
        var dev = findViewById<TextView>(R.id.tvDeveloper)
        var publisher = findViewById<TextView>(R.id.tvPublisher)
        var desc = findViewById<TextView>(R.id.tvDesc)
        var linkBtn = findViewById<Button>(R.id.button)

        Glide.with(this).load(currGame.thumbnail).into(img);
        title.setText(currGame.title)
        releaseDate.setText(currGame.release_date)
        dev.setText(currGame.developer)
        publisher.setText(currGame.publisher)
        desc.setText("\"" + currGame.short_description + "\"")

        linkBtn.setOnClickListener()
        {
            val uri: Uri = Uri.parse(currGame.game_url) // missing 'http://' will cause crashed

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}
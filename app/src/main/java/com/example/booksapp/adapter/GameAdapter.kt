package com.example.booksapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksapp.GameDeatil
import com.example.booksapp.R
import com.example.booksapp.currGame
import com.example.booksapp.models.Game

class gameAdapter(var games: ArrayList<Game>, var context: Context) : RecyclerView.Adapter<gameAdapter.viewHolder>() {

    /** View Holder class **/
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img:ImageView = itemView.findViewById(R.id.bookImage)
        val name:TextView = itemView.findViewById(R.id.bookName)
    }

    /** Adapter class functions **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.game_sample, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var game:Game = games.get(position)

        Glide.with(context).load(game.thumbnail).into(holder.img);
        holder.name.setText(game.title.toString())

        holder.img.setOnClickListener()
        {
            //Toast.makeText(context, game.developer, Toast.LENGTH_SHORT).show()
            currGame = games.get(position)

            val i = Intent(context, GameDeatil::class.java)
            context.startActivity(i)
        }


    }

    override fun getItemCount(): Int {
        return games.size
    }


}
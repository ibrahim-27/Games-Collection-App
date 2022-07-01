package com.example.booksapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksapp.GameDeatil
import com.example.booksapp.R
import com.example.booksapp.currGame
import com.example.booksapp.models.Game

class gameAdapter(var games: ArrayList<Game>, var context: Context, val onItemClick: OnItemClick) : RecyclerView.Adapter<gameAdapter.viewHolder>() {

    /** View Holder class **/
    class viewHolder(itemView: View, onItemClick: OnItemClick) : RecyclerView.ViewHolder(itemView) {
        val img:ImageView = itemView.findViewById(R.id.bookImage)
        val name:TextView = itemView.findViewById(R.id.bookName)
        val btn: CheckBox = itemView.findViewById(R.id.favBtn)

        init {
            /** When an item is clicked on recycler view **/
            itemView.setOnClickListener(){onItemClick.onItemClick(adapterPosition)}
            /** When fav button is clicked on recycler view **/
            btn.setOnClickListener(){onItemClick.onFavClick(this.btn, adapterPosition)}
        }
    }

    /** Adapter class functions **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var view: View = LayoutInflater.from(context).inflate(R.layout.game_sample, parent, false)
        return viewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        var game:Game = games.get(position)

        Glide.with(context).load(game.thumbnail).into(holder.img);
        holder.name.setText(game.title.toString())

        /** As the recycler view uses the same view but different data,
            so the isFav attribute should be cheked **/
        if(game.isFav)
        {
            holder.btn.setButtonDrawable(R.drawable.filled_heart)
            holder.btn.isChecked = true
        }
        else
        { holder.btn.setButtonDrawable(R.drawable.heart) }

    }

    override fun getItemCount(): Int {
        return games.size
    }

    /** Interface for onclick functions related to recyclerView**/
    interface OnItemClick
    {
        fun onItemClick(pos:Int)
        fun onFavClick(currBtn:CheckBox, pos:Int)
    }

}
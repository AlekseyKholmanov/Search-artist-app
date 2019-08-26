package ru.aholmanov.search_artist_app.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.aholmanov.search_artist_app.R
import ru.aholmanov.search_artist_app.model.Artist

class ArtistAdapter(private var artists: MutableList<Artist>, val callback: ArtistAdapterCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_artist, parent, false)
        val holder = ArtistViewHolder(view)
        view.setOnClickListener {
            if ( holder.adapterPosition!= RecyclerView.NO_POSITION) {
                callback.artistClicked(artists[holder.adapterPosition])
            }
        }
        return holder
    }

    override fun getItemCount(): Int = artists.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArtistViewHolder)
            holder.bind(artists[position])
    }

    class ArtistViewHolder constructor(v: View) : RecyclerView.ViewHolder(v) {
        private val artistName: TextView = v.findViewById(R.id.item_artist_name)
        fun bind(artist: Artist) {
            artistName.text = artist.name
        }
    }

    interface ArtistAdapterCallback {
        fun artistClicked(item: Artist)
    }
}

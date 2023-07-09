package com.poncegamez.ricknmortyapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poncegamez.ricknmortyapp.databinding.CardViewCharactersBinding
import com.poncegamez.ricknmortyapp.models.Characters
import com.squareup.picasso.Picasso


class ListAdapter(private val onItemClicked: (Characters) -> Unit) :
    RecyclerView.Adapter<ListAdapter.CharactersViewHolder>() {

    private val characterItemList: ArrayList<Characters> = arrayListOf()

    class CharactersViewHolder(private val binding: CardViewCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(characters: Characters) {
            binding.idContentTextView.text = characters.id.toString()
            binding.nameContentTextView.text = characters.name
            binding.specieContentTextView.text = characters.species
            val picasso = Picasso.get()
            picasso.isLoggingEnabled = true
            picasso.load(characters.image).into(binding.pictureImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding =
            CardViewCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return characterItemList.size
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val characters = characterItemList[position]
        holder.bind(characters)
        holder.itemView.setOnClickListener {
            onItemClicked(characters)
        }
    }

    fun appendItems(newItems: List<Characters>) {
        characterItemList.clear()
        characterItemList.addAll(newItems)
        notifyDataSetChanged() //revisar
    }

}
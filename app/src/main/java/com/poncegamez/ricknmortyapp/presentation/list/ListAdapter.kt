package com.poncegamez.ricknmortyapp.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.poncegamez.ricknmortyapp.databinding.CardViewCharactersBinding
import com.poncegamez.ricknmortyapp.models.Characters
import com.squareup.picasso.Picasso


class ListAdapter :
    PagingDataAdapter<Characters, ListAdapter.CharactersViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Characters>() {
            override fun areItemsTheSame(oldItem: Characters, newItem: Characters): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Characters, newItem: Characters): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class CharactersViewHolder(private val binding: CardViewCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(characters: Characters) {
            binding.idContentTextView.text = characters.id.toString()
            binding.nameContentTextView.text = characters.name
            binding.specieContentTextView.text = characters.species
            Picasso.get().load(characters.image).into(binding.pictureImageView)
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(characters)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding =
            CardViewCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    private var onItemClickListener: ((Characters) -> Unit)? = null

    fun setOnItemClickListener(listener: (Characters) -> Unit) {
        onItemClickListener = listener
    }
}
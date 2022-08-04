package com.example.thestarwarsapi.presentation.item_adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thestarwarsapi.databinding.CharacterItemBinding
import com.example.thestarwarsapi.domain.model.Character

class CharacterAdapter : ListAdapter<Character, CharacterViewHolder>(DiffUtilItemCallback()) {

    var onItemClicked: ((Character) -> Unit)? = null
    var onItemLongClicked: ((Character) -> Unit)? = null
    var onReachEndListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CharacterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = currentList[position]
        with(holder) {
            binding.tvName.text = character.name
            itemView.setOnClickListener {
                onItemClicked?.invoke(character)
            }
            itemView.setOnLongClickListener {
                onItemLongClicked?.invoke(character)
                true
            }
            if (position == currentList.size - 2) {
                onReachEndListener?.invoke()
            }
        }

    }

}
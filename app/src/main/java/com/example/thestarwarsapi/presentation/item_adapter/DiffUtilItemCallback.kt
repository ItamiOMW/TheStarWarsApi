package com.example.thestarwarsapi.presentation.item_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.thestarwarsapi.domain.model.Character

class DiffUtilItemCallback : DiffUtil.ItemCallback<Character>() {

    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

}
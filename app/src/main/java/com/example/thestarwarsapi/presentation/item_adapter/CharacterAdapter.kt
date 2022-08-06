package com.example.thestarwarsapi.presentation.item_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.ListAdapter
import com.example.thestarwarsapi.databinding.CharacterItemBinding
import com.example.thestarwarsapi.domain.model.Character
import java.util.*


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
            setAnimation(itemView, position)
            if (position == currentList.size - 2) {
                onReachEndListener?.invoke()
            }
        }
    }

    private var lastPosition = -1

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val anim = ScaleAnimation(
                0.0f,
                1.0f,
                0.0f,
                1.0f,
                Animation.INFINITE,
                0.5f,
                Animation.INFINITE,
                0.5f
            )
            anim.duration =
                Random().nextInt(501).toLong()
            viewToAnimate.startAnimation(anim)
            lastPosition = position
        }
    }

}
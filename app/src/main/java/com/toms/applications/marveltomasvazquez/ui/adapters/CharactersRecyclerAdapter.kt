package com.toms.applications.marveltomasvazquez.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.toms.applications.marveltomasvazquez.databinding.RecyclerItemCharacterBinding
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem as Character

/**
 * To inflate the items into the recycler
 * Adapter used on each recycler (Search, Home and Favorite Fragments)
 */
class CharactersRecyclerAdapter (private val clickListener: Listener):
    ListAdapter<Character, CharactersRecyclerAdapter.ViewHolder>(ClassDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it,clickListener)
        }
    }

    class ViewHolder private constructor(val binding: RecyclerItemCharacterBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Character, clickListener: Listener) {
            val res = itemView.context
            binding.clickListener = clickListener
            binding.character = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater =  LayoutInflater.from(parent.context)
                val binding = RecyclerItemCharacterBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

    class ClassDiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }
}

class Listener(val clickListener: (character: Character) -> Unit){
    fun onClick(character: Character) = clickListener(character)
}
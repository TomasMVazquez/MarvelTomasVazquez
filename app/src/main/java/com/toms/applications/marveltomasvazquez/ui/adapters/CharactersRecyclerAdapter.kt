package com.toms.applications.marveltomasvazquez.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.databinding.RecyclerItemCharacterBinding

/**
 * To inflate the items into the recycler
 * Adapter used on each recycler (Search, Home and Favorite Fragments)
 */
class CharactersRecyclerAdapter(private val clickListener: Listener) :
    ListAdapter<MyCharacter, CharactersRecyclerAdapter.ViewHolder>(ClassDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.startAnimation()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.clearAnimation()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it, clickListener)
        }
    }

    class ViewHolder private constructor(val binding: RecyclerItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MyCharacter, listener: Listener) {
            val res = itemView.context
            with(binding) {
                clickListener = listener
                character = item
                executePendingBindings()
            }
        }

        fun startAnimation() {
            binding.constraintLayout.transitionToEnd()
        }

        fun clearAnimation() {
            binding.constraintLayout.progress = 0F
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerItemCharacterBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(
                    binding
                )
            }
        }
    }

    class ClassDiffCallback : DiffUtil.ItemCallback<MyCharacter>() {
        override fun areItemsTheSame(oldItem: MyCharacter, newItem: MyCharacter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyCharacter, newItem: MyCharacter): Boolean {
            return oldItem == newItem
        }

    }
}

class Listener(val clickListener: (character: MyCharacter) -> Unit) {
    fun onClick(character: MyCharacter) = clickListener(character)
}
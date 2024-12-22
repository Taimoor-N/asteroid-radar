package com.udacity.asteroidradar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.databinding.ListItemAsteroidBinding

class AsteroidAdapter(val clickListener: AsteroidClickListener) : ListAdapter<Asteroid, AsteroidAdapter.ViewHolder>(
    AsteroidDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    class ViewHolder private constructor(private val binding: ListItemAsteroidBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: AsteroidClickListener, item: Asteroid) {
            binding.asteroid = item
            binding.clickListener = clickListener
            setDynamicLayoutContent(item)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemAsteroidBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        private fun setDynamicLayoutContent(asteroid: Asteroid) {
            if (asteroid.isPotentiallyHazardous) {
                binding.ivListItemAsteroidHazardous.contentDescription = this.itemView.context.getString(R.string.ContentDescription_Hazardous_Emoji)
            } else {
                binding.ivListItemAsteroidHazardous.contentDescription = this.itemView.context.getString(R.string.ContentDescription_Safe_Emoji)
            }
        }
    }

}

class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}

class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}
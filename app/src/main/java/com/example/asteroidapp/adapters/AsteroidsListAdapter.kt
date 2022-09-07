package com.example.asteroidapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidapp.dataBase.AsteroidData
import com.example.asteroidapp.databinding.AsteroidItemLayoutBinding

class AsteroidsListAdapter(private val clickListener: OnClickListener): ListAdapter<AsteroidData, AsteroidsListAdapter.ViewHolder>(AsteroidCallBack) {

    companion object AsteroidCallBack: DiffUtil.ItemCallback<AsteroidData>() {
        override fun areItemsTheSame(oldItem: AsteroidData, newItem: AsteroidData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AsteroidData, newItem: AsteroidData): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(private val binding: AsteroidItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(asteroid: AsteroidData) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AsteroidItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid)
        holder.itemView.setOnClickListener {
            clickListener.onClick(asteroid)
        }
    }
}

class OnClickListener(val clickListener: (asteroid: AsteroidData) ->Unit) {
    fun onClick(asteroid: AsteroidData) = clickListener(asteroid)
}
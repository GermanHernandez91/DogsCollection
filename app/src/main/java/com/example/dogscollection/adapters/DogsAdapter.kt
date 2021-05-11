package com.example.dogscollection.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dogscollection.databinding.DogRowLayoutBinding
import com.example.dogscollection.models.Dogs
import com.example.dogscollection.utils.ListDiffUtil

class DogsAdapter : RecyclerView.Adapter<DogsAdapter.DogsViewHolder>() {

    private var dogs = emptyList<String>()

    class DogsViewHolder(
        private val binding: DogRowLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(dogImage: String) {
            binding.dogImage = dogImage
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): DogsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DogRowLayoutBinding.inflate(layoutInflater, parent, false)
                return DogsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        return DogsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        val dogItem = dogs[position]
        holder.bind(dogItem)
    }

    override fun getItemCount(): Int {
        return dogs.size
    }

    fun setData(newData: Dogs) {
        val dogsListString = newData.message
        val listDiffUtil = ListDiffUtil(dogs, dogsListString)
        val diffUtilResult = DiffUtil.calculateDiff(listDiffUtil)
        dogs = dogsListString
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
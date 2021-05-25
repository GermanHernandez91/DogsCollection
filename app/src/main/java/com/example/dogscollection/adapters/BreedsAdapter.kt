package com.example.dogscollection.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dogscollection.databinding.BreedRowLayoutBinding
import com.example.dogscollection.models.Breeds
import com.example.dogscollection.utils.ListDiffUtil

class BreedsAdapter : RecyclerView.Adapter<BreedsAdapter.BreedsViewHolder>() {

    private var breeds = emptyList<String>()

    class BreedsViewHolder(
        private val binding: BreedRowLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(breed: String) {
            binding.breed = breed
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): BreedsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BreedRowLayoutBinding.inflate(layoutInflater, parent, false)
                return BreedsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsViewHolder {
        return BreedsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BreedsViewHolder, position: Int) {
        val breedItem = breeds[position]
        holder.bind(breedItem)
    }

    override fun getItemCount(): Int {
        return breeds.size
    }

    fun setData(newData: Breeds) {
        val breedsListString = newData.breedItem.keys.toTypedArray().toList()
        val listDiffUtil = ListDiffUtil(breeds, breedsListString)
        val diffUtilResult = DiffUtil.calculateDiff(listDiffUtil)
        breeds = breedsListString
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
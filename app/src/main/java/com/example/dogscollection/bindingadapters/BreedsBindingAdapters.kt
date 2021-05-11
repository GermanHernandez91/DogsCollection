package com.example.dogscollection.bindingadapters

import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.dogscollection.ui.fragments.breeds.BreedsListFragmentDirections
import java.lang.Exception

class BreedsBindingAdapters {

    companion object {

        @BindingAdapter("onBreedClickListener")
        @JvmStatic
        fun onBreedClickListener(breedRowLayout: ConstraintLayout, breed: String) {
            breedRowLayout.setOnClickListener {
                try {
                    val action = BreedsListFragmentDirections.actionBreedsListFragmentToDogsListFragment(breed)
                    breedRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onBreedClickListener", e.toString())
                }
            }
        }
    }
}
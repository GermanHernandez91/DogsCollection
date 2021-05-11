package com.example.dogscollection.ui.fragments.breeds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogscollection.adapters.BreedsAdapter
import com.example.dogscollection.databinding.FragmentBreedsListBinding
import com.example.dogscollection.utils.NetworkResult
import kotlinx.coroutines.launch

class BreedsListFragment : Fragment() {

    private var _binding: FragmentBreedsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BreedsListViewModel

    private val mAdapter by lazy { BreedsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(BreedsListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBreedsListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupRecyclerView()

        lifecycleScope.launch {
            requestApiData()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.breedsRecyclerView.adapter = mAdapter
        binding.breedsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun requestApiData() {
        viewModel.getBreedsList()
        viewModel.breeds.observe(viewLifecycleOwner) { response ->
            when(response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun showShimmerEffect() {
        binding.breedsRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.breedsRecyclerView.hideShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
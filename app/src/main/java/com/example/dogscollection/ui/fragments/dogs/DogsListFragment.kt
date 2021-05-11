package com.example.dogscollection.ui.fragments.dogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogscollection.R
import com.example.dogscollection.adapters.BreedsAdapter
import com.example.dogscollection.adapters.DogsAdapter
import com.example.dogscollection.databinding.FragmentBreedsListBinding
import com.example.dogscollection.databinding.FragmentDogsListBinding
import com.example.dogscollection.ui.fragments.breeds.BreedsListViewModel
import com.example.dogscollection.utils.NetworkResult
import kotlinx.coroutines.launch

class DogsListFragment : Fragment() {

    private val args by navArgs<DogsListFragmentArgs>()

    private var _binding: FragmentDogsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DogsListViewModel

    private val mAdapter by lazy { DogsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DogsListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDogsListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerView()

        lifecycleScope.launch {
            requestApiData()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.dogsRecyclerView.adapter = mAdapter
        binding.dogsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun requestApiData() {
        viewModel.getDogsList(args.breed)
        viewModel.dogs.observe(viewLifecycleOwner) { response ->
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
        binding.dogsRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.dogsRecyclerView.hideShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
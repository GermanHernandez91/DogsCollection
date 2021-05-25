package com.example.dogscollection.ui.fragments.breeds

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogscollection.R
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

        setupRecyclerView()
        setHasOptionsMenu(true)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.main_menu) {
            displayInfoAlertDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayInfoAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage("German Hernandez del Rosario").setCancelable(true)

        val alert = dialogBuilder.create()
        alert.setTitle("Created By")
        alert.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
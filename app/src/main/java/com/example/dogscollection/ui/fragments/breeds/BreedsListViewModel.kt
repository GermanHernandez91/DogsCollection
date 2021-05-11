package com.example.dogscollection.ui.fragments.breeds

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dogscollection.data.Repository
import com.example.dogscollection.models.Breeds
import com.example.dogscollection.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BreedsListViewModel @Inject constructor(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    private val _breeds = MutableLiveData<NetworkResult<Breeds>>()
    val breeds: LiveData<NetworkResult<Breeds>> get() = _breeds

    fun getBreedsList() = viewModelScope.launch {
        getBreedsSafeCall()
    }

    private suspend fun getBreedsSafeCall() {
        _breeds.value = NetworkResult.Loading()
        try {
            val response = repository.remote.getBreedsList()
            _breeds.value = handleResponse(response)
        } catch (e: Exception) {
            _breeds.value = NetworkResult.Error("Breeds not found")
        }
    }

    private fun handleResponse(response: Response<Breeds>): NetworkResult<Breeds> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.isSuccessful -> {
                NetworkResult.Success(response.body()!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }
}
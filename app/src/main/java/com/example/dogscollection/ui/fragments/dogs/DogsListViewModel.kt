package com.example.dogscollection.ui.fragments.dogs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dogscollection.data.Repository
import com.example.dogscollection.models.Dogs
import com.example.dogscollection.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DogsListViewModel @Inject constructor(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    private val _dogs = MutableLiveData<NetworkResult<Dogs>>()
    val dogs: LiveData<NetworkResult<Dogs>> get() = _dogs

    fun getDogsList(breed: String) = viewModelScope.launch {
        getDogsSafeCall(breed)
    }

    private suspend fun getDogsSafeCall(breed: String) {
        _dogs.value = NetworkResult.Loading()
        try {
            val response = repository.remote.getRandomDogsByBreed(breed)
            _dogs.value = handleResponse(response)
        } catch (e: Exception) {
            _dogs.value = NetworkResult.Error("Dogs not found")
        }
    }

    private fun handleResponse(response: Response<Dogs>): NetworkResult<Dogs> {
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
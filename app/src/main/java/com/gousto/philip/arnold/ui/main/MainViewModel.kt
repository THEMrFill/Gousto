package com.gousto.philip.arnold.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gousto.philip.arnold.network.SingleLiveEvent
import com.gousto.philip.arnold.network.UseCaseResult
import com.gousto.philip.arnold.storage.StoredProducts
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val repository: MainRepository) : ViewModel(), CoroutineScope {

    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val showLoading = MutableLiveData<Boolean>()
    val displayProducts = MutableLiveData<StoredProducts>()
    val showError = SingleLiveEvent<String>()
    val navigateToDetail = SingleLiveEvent<String>()

    init {
        loadProducts()
    }

    fun loadProducts() {
        // Show progressBar during the operation on the MAIN (default) thread
        showLoading.value = true

        launch {
            repository.loadProducts(displayProducts){}
        }
        // launch the Coroutine
        launch {
            // Switching from MAIN to IO thread for API operation
            // Update our data list with the new one from API
            val result = withContext(Dispatchers.IO) { repository.getProducts() }
            // Hide progressBar once the operation is done on the MAIN (default) thread
            showLoading.value = false
            when (result) {
                is UseCaseResult.Success -> {
                    repository.storeProducts(result.data)
                    loadAllProducts() {}
                }
                is UseCaseResult.Error -> {
                    showError.value = result.exception.message
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }

    fun loadAllProducts(after: () -> Unit) {
        repository.loadProducts(displayProducts, after)
    }

    fun filterProducts(search: String, after: () -> Unit) {
        repository.filterProducts(displayProducts, search, after)
    }
}

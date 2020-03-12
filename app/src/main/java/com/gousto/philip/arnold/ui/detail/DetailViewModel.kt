package com.gousto.philip.arnold.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gousto.philip.arnold.storage.StoredData

class DetailViewModel(private val repository: DetailRepository) : ViewModel() {
    val id = MutableLiveData<String>()
    val data = MutableLiveData<StoredData>()

    fun loadData() {
        id.value?.let { item ->
            repository.loadProduct(item, data)
        }
    }
}
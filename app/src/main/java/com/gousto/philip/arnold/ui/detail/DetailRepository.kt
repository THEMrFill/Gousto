package com.gousto.philip.arnold.ui.detail

import androidx.lifecycle.MutableLiveData
import com.gousto.philip.arnold.storage.StoredData
import io.realm.Realm
import io.realm.kotlin.where

class DetailRepository {
    val realm: Realm
    init {
        realm = Realm.getDefaultInstance()
    }

    fun loadProduct(id: String, liveData: MutableLiveData<StoredData>) {
        val results = realm.where<StoredData>().equalTo("id", id).findAll()
        if (!results.isEmpty()) {
            liveData.value = results.first()
        }
    }
}
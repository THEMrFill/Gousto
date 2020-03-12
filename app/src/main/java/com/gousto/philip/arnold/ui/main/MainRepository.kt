package com.gousto.philip.arnold.ui.main

import androidx.lifecycle.MutableLiveData
import com.gousto.philip.arnold.network.Network
import com.gousto.philip.arnold.network.UseCaseResult
import com.gousto.philip.arnold.storage.StoredData
import com.gousto.philip.arnold.storage.StoredProducts
import io.realm.Case
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.where
import java.lang.Exception

class MainRepository(val api: Network) {
    val realm: Realm

    init {
        realm = Realm.getDefaultInstance()
    }

    suspend fun getProducts(): UseCaseResult<StoredProducts> {
        return try {
            val result = api.getProducts().await()
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }

    fun storeProducts(products: StoredProducts) {
        with(realm) {
            beginTransaction()
            // I had to delete everything, otherwise it leaves "orphaned" data
            deleteAll()
            copyToRealm(products)
            commitTransaction()
        }
    }

    fun loadProducts(liveData: MutableLiveData<StoredProducts>, after: () -> Unit) {
        filterProducts(liveData, null, after)
    }

    fun filterProducts(
        liveData: MutableLiveData<StoredProducts>,
        search: String? = null,
        after: () -> Unit
    ) {
        val query = realm.where<StoredData>()
            .equalTo("categories.hidden", false)
        search?.let {
            query.and()
                .contains("title", search, Case.INSENSITIVE)
        }
        val results = query.findAll()

        val newProducts = StoredProducts()
        newProducts.data.addAll(results)
        liveData.value = newProducts

        after()
    }
}
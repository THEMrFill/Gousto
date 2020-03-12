package com.gousto.philip.arnold.network

import com.gousto.philip.arnold.storage.StoredProducts
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface Network {
    @GET("products")
    fun getProducts(
        @Query("includes[]") includes1: String = "categories",
        @Query("includes[]") includes2: String = "attributes",
        @Query("image_sizes[]") sizes: Int = 200
    ) : Deferred<StoredProducts>
}
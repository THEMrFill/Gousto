package com.gousto.philip.arnold.model

data class Data(
    val age_restricted: Boolean,
    val always_on_menu: Boolean,
    val attributes: List<Attribute>,
    val box_limit: Int,
    val categories: List<Category>,
    val created_at: String,
    val description: String,
    val id: String,
    val images: Images,
    val is_for_sale: Boolean,
    val is_vatable: Boolean,
    val list_price: String,
    val sku: String,
    val tags: List<Any>,
    val title: String,
    val volume: Int,
    val zone: String
)
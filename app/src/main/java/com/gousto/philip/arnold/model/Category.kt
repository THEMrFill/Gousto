package com.gousto.philip.arnold.model

data class Category(
    val box_limit: Int,
    val hidden: Boolean,
    val id: String,
    val is_default: Boolean,
    val pivot: Pivot,
    val recently_added: Boolean,
    val title: String
)
package com.gousto.philip.arnold.storage

import io.realm.RealmList
import io.realm.RealmObject

open class StoredData(
    var age_restricted: Boolean? = false,
    var always_on_menu: Boolean? = false,
    var attributes: RealmList<StoredAttribute> = RealmList(),
    var box_limit: Int? = 0,
    var categories: RealmList<StoredCategory> = RealmList(),
    var created_at: String? = "",
    var description: String? = "",
    var id: String? = "",
    var images: StoredImages? = null,
    var is_for_sale: Boolean? = false,
    var is_vatable: Boolean? = false,
    var list_price: String? = "",
    var sku: String? = "",
    var tags: RealmList<String> = RealmList(),
    var title: String? = "",
    var volume: Int? = 0,
    var zone: String? = ""
): RealmObject()
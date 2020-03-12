package com.gousto.philip.arnold.storage

import io.realm.RealmObject

open class StoredCategory(
    var box_limit: Int? = 0,
    var hidden: Boolean? = false,
    var id: String? = "",
    var is_default: Boolean? = false,
    var pivot: StoredPivot? = null,
    var recently_added: Boolean? = false,
    var title: String? = ""
): RealmObject()
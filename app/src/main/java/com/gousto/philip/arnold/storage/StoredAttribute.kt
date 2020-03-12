package com.gousto.philip.arnold.storage

import io.realm.RealmObject

open class StoredAttribute(
    var id: String? = "",
    var title: String? = "",
    var unit: String? = "",
    var value: String? = ""
): RealmObject()
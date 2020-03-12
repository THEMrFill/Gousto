package com.gousto.philip.arnold.storage

import io.realm.RealmObject

open class StoredX200(
    var src: String? = "",
    var url: String? = "",
    var width: Int? = 0
) : RealmObject()
package com.gousto.philip.arnold.storage

import io.realm.RealmObject

open class StoredPivot(
    var created_at: String? = ""
): RealmObject()
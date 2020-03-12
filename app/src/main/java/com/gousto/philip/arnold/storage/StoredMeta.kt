package com.gousto.philip.arnold.storage

import io.realm.RealmObject

open class StoredMeta(
    var count: Int = 0,
    var limit: Int = 0,
    var offset: Int = 0,
    var total: Int = 0
) : RealmObject()
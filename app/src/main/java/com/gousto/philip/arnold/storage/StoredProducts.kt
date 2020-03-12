package com.gousto.philip.arnold.storage

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class StoredProducts(
    @PrimaryKey
    var id: Int = 0,
    var data: RealmList<StoredData> = RealmList(),
    var meta: StoredMeta? = null,
    var status: String = ""
): RealmObject()
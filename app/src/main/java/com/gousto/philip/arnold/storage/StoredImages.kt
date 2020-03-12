package com.gousto.philip.arnold.storage

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class StoredImages(
    @SerializedName("200")
    var a200: StoredX200? = null
): RealmObject()
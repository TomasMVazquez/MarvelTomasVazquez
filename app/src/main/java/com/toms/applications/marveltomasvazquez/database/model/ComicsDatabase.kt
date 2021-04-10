package com.toms.applications.marveltomasvazquez.database.model

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.domain.Comics
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicsDatabase(
    val available: Int,
    val items: List<ComicItemDatabase>
): Parcelable

fun ComicsDatabase.asDomainModel(): Comics {
    return Comics(
        available = available,
        items = items.asDomainModel()
    )
}
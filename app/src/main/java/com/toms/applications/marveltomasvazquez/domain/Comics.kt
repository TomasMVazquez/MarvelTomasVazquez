package com.toms.applications.marveltomasvazquez.domain

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.database.model.ComicsDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comics(
    val available: Int,
    val items: List<ComicItem>
): Parcelable

fun Comics.asDatabaseModel(): ComicsDatabase {
    return ComicsDatabase(
        available = available,
        items = items.asDatabaseModel()
    )
}
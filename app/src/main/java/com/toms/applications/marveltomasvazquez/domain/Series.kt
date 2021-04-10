package com.toms.applications.marveltomasvazquez.domain

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.database.model.SeriesDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
data class Series(
    val available: Int,
    val items: List<SerieItem>
): Parcelable

fun Series.asDatabaseModel(): SeriesDatabase {
    return SeriesDatabase(
        available = available,
        items = items.asDatabaseModel()
    )
}
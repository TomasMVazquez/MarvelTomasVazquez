package com.toms.applications.marveltomasvazquez.database.model

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.domain.Series
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeriesDatabase(
    val available: Int,
    val items: List<SerieItemDatabase>
): Parcelable

fun SeriesDatabase.asDomainModel(): Series {
    return Series(
        available = available,
        items = items.asDomainModel()
    )
}
package com.toms.applications.marveltomasvazquez.network.model

import com.toms.applications.marveltomasvazquez.database.model.SeriesDatabase
import com.toms.applications.marveltomasvazquez.domain.Series as SeriesDomain

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXX>,
    val returned: Int
)

fun Series.asDatabaseModel(): SeriesDatabase{
    return SeriesDatabase(
        available = available,
        items = items.asDatabaseModel()
    )
}

fun Series.asDomainModel(): SeriesDomain{
    return SeriesDomain(
        available = available,
        items = items.asDomainModel()
    )
}
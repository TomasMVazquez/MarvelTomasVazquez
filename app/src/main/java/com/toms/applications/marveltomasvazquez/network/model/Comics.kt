package com.toms.applications.marveltomasvazquez.network.model

import com.toms.applications.marveltomasvazquez.database.model.ComicsDatabase
import com.toms.applications.marveltomasvazquez.domain.Comics as ComicsDomain

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)

fun Comics.asDatabaseModel(): ComicsDatabase{
    return ComicsDatabase(
        available = available,
        items = items.asDatabaseModel()
    )
}

fun Comics.asDomainModel(): ComicsDomain{
    return ComicsDomain(
        available = available,
        items = items.asDomainModel()
    )
}
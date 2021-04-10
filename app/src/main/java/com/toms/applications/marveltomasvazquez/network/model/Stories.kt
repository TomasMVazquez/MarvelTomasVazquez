package com.toms.applications.marveltomasvazquez.network.model

import com.toms.applications.marveltomasvazquez.database.model.StoriesDatabase
import com.toms.applications.marveltomasvazquez.domain.Stories as StoriesDomain

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: Int
)

fun Stories.asDatabaseModel(): StoriesDatabase{
    return StoriesDatabase(
        available = available,
        items = items.asDatabaseModel()
    )
}

fun Stories.asDomainModel(): StoriesDomain{
    return StoriesDomain(
        available = available,
        items = items.asDomainModel()
    )
}
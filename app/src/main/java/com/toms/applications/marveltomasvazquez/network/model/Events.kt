package com.toms.applications.marveltomasvazquez.network.model

import com.toms.applications.marveltomasvazquez.database.model.EventsDatabase
import com.toms.applications.marveltomasvazquez.domain.Events as EventsDomain

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)

fun Events.asDatabaseModel(): EventsDatabase{
    return EventsDatabase(
        available = available,
        items = items.asDatabaseModel()
    )
}

fun Events.asDomainModel(): EventsDomain{
    return EventsDomain(
        available = available,
        items = items.asDomainModel()
    )
}
package com.toms.applications.marveltomasvazquez.network.model

import com.toms.applications.marveltomasvazquez.database.model.EventItemDatabase
import com.toms.applications.marveltomasvazquez.domain.EventItem

data class ItemX(
    val name: String,
    val resourceURI: String
)

fun List<ItemX>.asDatabaseModel(): List<EventItemDatabase>{
    return map{
        EventItemDatabase(
                name = it.name,
                resourceURI = it.resourceURI
        )
    }
}

fun List<ItemX>.asDomainModel(): List<EventItem>{
    return map{
        EventItem(
            name = it.name,
            resourceURI = it.resourceURI
        )
    }
}
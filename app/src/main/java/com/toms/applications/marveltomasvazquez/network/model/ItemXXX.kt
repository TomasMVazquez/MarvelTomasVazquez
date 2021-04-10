package com.toms.applications.marveltomasvazquez.network.model

import com.toms.applications.marveltomasvazquez.database.model.StoryItemDatabase
import com.toms.applications.marveltomasvazquez.domain.StoryItem

data class ItemXXX(
    val name: String,
    val resourceURI: String,
    val type: String
)

fun List<ItemXXX>.asDatabaseModel(): List<StoryItemDatabase>{
    return map{
        StoryItemDatabase(
                name = it.name,
                resourceURI = it.resourceURI,
                type = it.type
        )
    }
}

fun List<ItemXXX>.asDomainModel(): List<StoryItem>{
    return map{
        StoryItem(
            name = it.name,
            resourceURI = it.resourceURI,
            type = it.type
        )
    }
}
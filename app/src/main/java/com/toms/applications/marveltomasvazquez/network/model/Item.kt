package com.toms.applications.marveltomasvazquez.network.model

import com.toms.applications.marveltomasvazquez.database.model.ComicItemDatabase
import com.toms.applications.marveltomasvazquez.domain.ComicItem

data class Item(
    val name: String,
    val resourceURI: String
)

fun List<Item>.asDatabaseModel(): List<ComicItemDatabase>{
    return map{
        ComicItemDatabase(
            name = it.name,
            resourceURI = it.resourceURI
        )
    }
}

fun List<Item>.asDomainModel(): List<ComicItem>{
    return map{
        ComicItem(
            name = it.name,
            resourceURI = it.resourceURI
        )
    }
}
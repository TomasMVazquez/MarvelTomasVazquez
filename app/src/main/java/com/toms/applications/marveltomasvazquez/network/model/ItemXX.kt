package com.toms.applications.marveltomasvazquez.network.model

import com.toms.applications.marveltomasvazquez.database.model.SerieItemDatabase
import com.toms.applications.marveltomasvazquez.domain.SerieItem

data class ItemXX(
    val name: String,
    val resourceURI: String
)

fun List<ItemXX>.asDatabaseModel(): List<SerieItemDatabase>{
    return map{
        SerieItemDatabase(
            name = it.name,
            resourceURI = it.resourceURI
        )
    }
}

fun List<ItemXX>.asDomainModel(): List<SerieItem>{
    return map{
        SerieItem(
            name = it.name,
            resourceURI = it.resourceURI
        )
    }
}
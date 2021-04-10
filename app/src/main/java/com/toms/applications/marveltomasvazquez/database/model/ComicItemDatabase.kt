package com.toms.applications.marveltomasvazquez.database.model

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.domain.ComicItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicItemDatabase(
    val name: String,
    val resourceURI: String
): Parcelable

fun ComicItemDatabase.asDomainModel(): ComicItem {
    return ComicItem(
        name = name,
        resourceURI = resourceURI
    )
}

fun List<ComicItemDatabase>.asDomainModel(): List<ComicItem>{
    return map{
        ComicItem(
            name = it.name,
            resourceURI = it.resourceURI
        )
    }
}
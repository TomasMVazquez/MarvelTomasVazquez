package com.toms.applications.marveltomasvazquez.domain

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.database.model.ComicItemDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicItem(
    val name: String,
    val resourceURI: String
): Parcelable

fun List<ComicItem>.asDatabaseModel(): List<ComicItemDatabase>{
    return map{
        ComicItemDatabase(
            name = it.name,
            resourceURI = it.resourceURI
        )
    }
}
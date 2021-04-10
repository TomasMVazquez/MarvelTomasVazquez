package com.toms.applications.marveltomasvazquez.domain

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.database.model.SerieItemDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
data class SerieItem(
    val name: String,
    val resourceURI: String
): Parcelable

fun List<SerieItem>.asDatabaseModel(): List<SerieItemDatabase>{
    return map{
        SerieItemDatabase(
            name = it.name,
            resourceURI = it.resourceURI
        )
    }
}
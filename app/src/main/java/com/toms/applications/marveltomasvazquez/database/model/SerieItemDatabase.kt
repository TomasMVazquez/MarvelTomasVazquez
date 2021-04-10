package com.toms.applications.marveltomasvazquez.database.model

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.domain.SerieItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class SerieItemDatabase(
    val name: String,
    val resourceURI: String
): Parcelable

fun SerieItemDatabase.asDomainModel(): SerieItem {
    return SerieItem(
        name = name,
        resourceURI = resourceURI
    )
}

fun List<SerieItemDatabase>.asDomainModel(): List<SerieItem>{
    return map{
        SerieItem(
            name = it.name,
            resourceURI = it.resourceURI
        )
    }
}
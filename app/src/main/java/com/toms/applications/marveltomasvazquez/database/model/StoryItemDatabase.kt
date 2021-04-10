package com.toms.applications.marveltomasvazquez.database.model

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.domain.StoryItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryItemDatabase(
    val name: String,
    val resourceURI: String,
    val type: String
): Parcelable

fun StoryItemDatabase.asDomainModel(): StoryItem {
    return StoryItem(
        name = name,
        resourceURI = resourceURI,
        type = type
    )
}

fun List<StoryItemDatabase>.asDomainModel(): List<StoryItem>{
    return map{
        StoryItem(
            name = it.name,
            resourceURI = it.resourceURI,
            type = it.type
        )
    }
}
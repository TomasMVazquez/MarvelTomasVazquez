package com.toms.applications.marveltomasvazquez.domain

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.database.model.StoryItemDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryItem(
    val name: String,
    val resourceURI: String,
    val type: String
): Parcelable

fun List<StoryItem>.asDatabaseModel(): List<StoryItemDatabase>{
    return map{
        StoryItemDatabase(
            name = it.name,
            resourceURI = it.resourceURI,
            type = it.type
        )
    }
}
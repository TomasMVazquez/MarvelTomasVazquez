package com.toms.applications.marveltomasvazquez.domain

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.database.model.StoriesDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stories(
    val available: Int,
    val items: List<StoryItem>
): Parcelable

fun Stories.asDatabaseModel(): StoriesDatabase {
    return StoriesDatabase(
        available = available,
        items = items.asDatabaseModel()
    )
}
package com.toms.applications.marveltomasvazquez.database.model

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.domain.Stories
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoriesDatabase(
    val available: Int,
    val items: List<StoryItemDatabase>
): Parcelable


fun StoriesDatabase.asDomainModel(): Stories {
    return Stories(
        available = available,
        items = items.asDomainModel()
    )
}
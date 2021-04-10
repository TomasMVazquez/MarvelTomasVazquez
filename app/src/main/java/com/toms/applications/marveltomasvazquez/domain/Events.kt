package com.toms.applications.marveltomasvazquez.domain

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.database.model.EventsDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
data class Events(
    val available: Int,
    val items: List<EventItem>
): Parcelable

fun Events.asDatabaseModel(): EventsDatabase {
    return EventsDatabase(
        available = available,
        items = items.asDatabaseModel()
    )
}
package com.toms.applications.marveltomasvazquez.database.model

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.domain.Events
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventsDatabase(
    val available: Int,
    val items: List<EventItemDatabase>
): Parcelable

fun EventsDatabase.asDomainModel(): Events {
    return Events(
        available = available,
        items = items.asDomainModel()
    )
}
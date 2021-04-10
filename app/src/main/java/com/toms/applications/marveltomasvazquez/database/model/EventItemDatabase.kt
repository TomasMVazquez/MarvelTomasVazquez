package com.toms.applications.marveltomasvazquez.database.model

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.domain.EventItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventItemDatabase(
    val name: String,
    val resourceURI: String
): Parcelable

fun EventItemDatabase.asDomainModel(): EventItem {
    return EventItem(
        name = name,
        resourceURI = resourceURI
    )
}

fun List<EventItemDatabase>.asDomainModel(): List<EventItem>{
    return map{
        EventItem(
            name = it.name,
            resourceURI = it.resourceURI
        )
    }
}
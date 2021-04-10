package com.toms.applications.marveltomasvazquez.domain

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.database.model.EventItemDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventItem(
    val name: String,
    val resourceURI: String
): Parcelable

fun List<EventItem>.asDatabaseModel(): List<EventItemDatabase>{
    return map{
        EventItemDatabase(
            name = it.name,
            resourceURI = it.resourceURI
        )
    }
}